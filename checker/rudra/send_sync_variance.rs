//! Unsafe Send/Sync impl detector

mod behavior;
mod phantom;
// You need to fix the code to enable `relaxed` mode..
mod relaxed;
// Default mode is `strict`.
mod strict;
mod utils;

use rustc_data_structures::fx::{FxHashMap, FxHashSet};
use rustc_hir::def_id::{DefId, LocalDefId};
use rustc_hir::{
    GenericBound, GenericParam, GenericParamKind, HirId, Impl, ImplPolarity, ItemId, ItemKind,
    Node, WherePredicate,
};
use rustc_middle::mir::terminator::Mutability;
use rustc_middle::ty::{
    self,
    subst::{self, GenericArgKind},
    AssocKind, GenericParamDef, GenericParamDefKind, List, PredicateKind, Ty, TyCtxt, TyS,
};
use rustc_span::symbol::sym;

use snafu::{OptionExt, Snafu};

use crate::analysis::{AnalysisKind, IntoReportLevel};
use crate::prelude::*;
use crate::report::{Report, ReportLevel};

use behavior::*;
pub use phantom::*;
pub use relaxed::*;
pub use strict::*;
pub use utils::*;

pub struct SendSyncVarianceChecker<'tcx> {
    rcx: RudraCtxt<'tcx>,
    /// For each ADT, keep track of reports.
    report_map: FxHashMap<DefId, Vec<Report>>,
    /// For each ADT, keep track of `T`s that are only within `PhantomData<T>`.
    phantom_map: FxHashMap<DefId, Vec<u32>>,
    /// For each ADT, keep track of AdtBehavior per generic param.
    behavior_map: FxHashMap<DefId, FxHashMap<PostMapIdx, AdtBehavior>>,
}

impl<'tcx> SendSyncVarianceChecker<'tcx> {
    pub fn new(rcx: RudraCtxt<'tcx>) -> Self {
        SendSyncVarianceChecker {
            rcx,
            report_map: FxHashMap::default(),
            phantom_map: FxHashMap::default(),
            behavior_map: FxHashMap::default(),
        }
    }

    pub fn analyze(mut self) {
        let send_trait_did = unwrap_or!(send_trait_def_id(self.rcx.tcx()) => return);
        let sync_trait_did = unwrap_or!(sync_trait_def_id(self.rcx.tcx()) => return);
        let copy_trait_did = unwrap_or!(copy_trait_def_id(self.rcx.tcx()) => return);

        // Main analysis
        self.analyze_send(send_trait_did, sync_trait_did, copy_trait_did);
        self.analyze_sync(send_trait_did, sync_trait_did, copy_trait_did);

        // Report any suspicious `Send`/`Sync` impls on the given struct.
        for (_struct_def_id, reports) in self.report_map.into_iter() {
            for report in reports.into_iter() {
                rudra_report(report);
            }
        }
    }

    /// Detect cases where the wrapper of T implements `Send`, but T may not be `Send`
    fn analyze_send(
        &mut self,
        send_trait_did: DefId,
        sync_trait_did: DefId,
        copy_trait_did: DefId,
    ) {
        // Iterate over `impl`s that implement `Send`.
        let hir = self.rcx.tcx().hir();
        for &impl_id in hir.trait_impls(send_trait_did) {
            let item = hir.item(ItemId { def_id: impl_id });
            if_chain! {
                if let ItemKind::Impl(impl_item) = &item.kind;
                if impl_item.polarity == ImplPolarity::Positive;
                if let Some((adt_def_id, send_sync_analyses)) =
                    self.suspicious_send(impl_id, send_trait_did, sync_trait_did, copy_trait_did);
                if send_sync_analyses.report_level() >= self.rcx.report_level();
                then {
                    let tcx = self.rcx.tcx();
                    self.report_map
                        .entry(adt_def_id)
                        .or_insert_with(|| Vec::with_capacity(2))
                        .push(Report::with_hir_id(
                            tcx,
                            send_sync_analyses.report_level(),
                            AnalysisKind::SendSyncVariance(send_sync_analyses),
                            "Suspicious impl of `Send` found",
                            impl_id,
                        ));
                }
            }
        }
    }

    /// Detect cases where the wrapper of T implements `Sync`, but T may not be `Sync`
    fn analyze_sync(
        &mut self,
        // report_map: &mut FxHashMap<DefId, Vec<Report>>,
        send_trait_did: DefId,
        sync_trait_did: DefId,
        copy_trait_did: DefId,
    ) {
        // Iterate over `impl`s that implement `Sync`.
        let hir = self.rcx.tcx().hir();
        for &impl_id in hir.trait_impls(sync_trait_did) {
            let item = hir.item(ItemId { def_id: impl_id });
            if_chain! {
                if let ItemKind::Impl(impl_item) = &item.kind;
                if impl_item.polarity == ImplPolarity::Positive;
                if let Some((struct_def_id, send_sync_analyses)) =
                    self.suspicious_sync(impl_id, send_trait_did, sync_trait_did, copy_trait_did);
                if send_sync_analyses.report_level() >= self.rcx.report_level();
                then {
                    let tcx = self.rcx.tcx();
                    self.report_map
                        .entry(struct_def_id)
                        .or_insert_with(|| Vec::with_capacity(2))
                        .push(Report::with_hir_id(
                            tcx,
                            send_sync_analyses.report_level(),
                            AnalysisKind::SendSyncVariance(send_sync_analyses),
                            "Suspicious impl of `Sync` found",
                            impl_id,
                        ));
                }
            }
        }
    }
}

/// Check Send Trait
fn send_trait_def_id<'tcx>(tcx: TyCtxt<'tcx>) -> AnalysisResult<'tcx, DefId> {
    convert!(tcx
        .get_diagnostic_item(sym::Send)
        .context(SendTraitNotFound))
}

/// Check Sync Trait
fn sync_trait_def_id<'tcx>(tcx: TyCtxt<'tcx>) -> AnalysisResult<'tcx, DefId> {
    convert!(tcx.lang_items().sync_trait().context(SyncTraitNotFound))
}

/// Check Copy Trait
fn copy_trait_def_id<'tcx>(tcx: TyCtxt<'tcx>) -> AnalysisResult<'tcx, DefId> {
    convert!(tcx.lang_items().copy_trait().context(CopyTraitNotFound))
}

/// Check Clone Trait
fn _clone_trait_def_id<'tcx>(tcx: TyCtxt<'tcx>) -> AnalysisResult<'tcx, DefId> {
    convert!(tcx.lang_items().clone_trait().context(CloneTraitNotFound))
}

#[derive(Debug, Snafu)]
pub enum SendSyncVarianceError {
    CloneTraitNotFound,
    CopyTraitNotFound,
    SendTraitNotFound,
    SyncTraitNotFound,
    CatchAll,
}

impl AnalysisError for SendSyncVarianceError {
    fn kind(&self) -> AnalysisErrorKind {
        use SendSyncVarianceError::*;
        match self {
            CloneTraitNotFound => AnalysisErrorKind::Unreachable,
            CopyTraitNotFound => AnalysisErrorKind::Unreachable,
            SendTraitNotFound => AnalysisErrorKind::Unreachable,
            SyncTraitNotFound => AnalysisErrorKind::Unreachable,
            CatchAll => AnalysisErrorKind::Unreachable,
        }
    }
}

// Index of generic type parameter within an impl block.
// Since the same generic parameter can have different indices in
// different impl blocks, we need to map these indices back to its
// original indices (`PostMapIdx`) to reason about generic parameters globally.
#[derive(Copy, Clone, Eq, Hash, PartialEq)]
pub struct PreMapIdx(u32);
// Index of generic type parameter in the ADT definition.
#[derive(Copy, Clone, Eq, Hash, PartialEq)]
pub struct PostMapIdx(u32);

bitflags! {
    #[derive(Default)]
    pub struct BehaviorFlag: u8 {
        // T: Send for impl Sync (with api check & phantom check)
        const API_SEND_FOR_SYNC = 0b00000001;
        // T: Sync for impl Sync (with api check & phantom check)
        const API_SYNC_FOR_SYNC = 0b00000100;
        // T: Send for impl Send (with phantom check)
        const PHANTOM_SEND_FOR_SEND = 0b00000010;
        // T: Send for impl Send (no api check, no phantom check)
        const NAIVE_SEND_FOR_SEND = 0b00001000;
        // T: Sync for impl Sync (no api check, no phantom check)
        const NAIVE_SYNC_FOR_SYNC = 0b00010000;
        // Relaxed Send for impl Send (with phantom check)
        const RELAX_SEND = 0b00100000;
        // Relaxed Sync for impl Sync (with phantom check)
        const RELAX_SYNC = 0b01000000;
    }
}

impl IntoReportLevel for BehaviorFlag {
    fn report_level(&self) -> ReportLevel {
        let high = BehaviorFlag::API_SEND_FOR_SYNC | BehaviorFlag::RELAX_SEND;
        let med = BehaviorFlag::API_SYNC_FOR_SYNC
            | BehaviorFlag::PHANTOM_SEND_FOR_SEND
            | BehaviorFlag::RELAX_SYNC;

        if !(*self & high).is_empty() {
            ReportLevel::Error
        } else if !(*self & med).is_empty() {
            ReportLevel::Warning
        } else {
            ReportLevel::Info
        }
    }
}

// We may not use the relaxed versions at all,
// but keeping them alive just in case..
impl<'tcx> SendSyncVarianceChecker<'tcx> {
    /// Detect suspicious `Send` with relaxed rules.
    /// Report only if all generic parameters of `impl Send` aren't `Send`.
    fn suspicious_send_relaxed(
        &self,
        hir_id: HirId,
        send_trait_def_id: DefId,
        sync_trait_def_id: DefId,
    ) -> bool {
        let map = self.rcx.tcx().hir();
        if_chain! {
            if let Some(node) = map.find(hir_id);
            if let Node::Item(item) = node;
            if let ItemKind::Impl(Impl {
                ref generics,
                of_trait: Some(ref trait_ref),
                ..
            }) = item.kind;
            if Some(send_trait_def_id) == trait_ref.trait_def_id();
            then {
                // If `impl Send` doesn't involve generic parameters, don't catch it.
                if generics.params.len() == 0 {
                    return false;
                }

                // Inspect immediate trait bounds on generic parameters
                if self.trait_in_imm_relaxed(
                    &[send_trait_def_id, sync_trait_def_id],
                    generics.params
                ) {
                    return false;
                }

                // Inspect trait bounds in where clauses
                return !self.trait_in_where_relaxed(
                    &[send_trait_def_id, sync_trait_def_id],
                    generics.where_clause.predicates
                );
            }
        }
        return false;
    }

    /// Detect suspicious Sync with relaxed rules.
    /// Report only if all generic parameters of `impl Sync` aren't Sync.
    fn suspicious_sync_relaxed(
        &self,
        // HirId of the `Impl Sync` item
        hir_id: HirId,
        sync_trait_def_id: DefId,
    ) -> bool {
        let map = self.rcx.tcx().hir();
        if_chain! {
            if let Some(node) = map.find(hir_id);
            if let Node::Item(item) = node;
            if let ItemKind::Impl(Impl {
                ref generics,
                of_trait: Some(ref trait_ref),
                ..
            }) = item.kind;
            if Some(sync_trait_def_id) == trait_ref.trait_def_id();
            then {
                // If `impl Sync` doesn't involve generic parameters, don't catch it.
                if generics.params.len() == 0 {
                    return false;
                }

                // Inspect immediate trait bounds on generic parameters
                if self.trait_in_imm_relaxed(
                   &[sync_trait_def_id],
                   generics.params
                ) {
                   return false;
                }

                return !self.trait_in_where_relaxed(
                    &[sync_trait_def_id],
                    generics.where_clause.predicates
                );
            }
        }
        return false;
    }

    fn trait_in_imm_relaxed(
        &self,
        target_trait_def_ids: &[DefId],
        generic_params: &[GenericParam],
    ) -> bool {
        for generic_param in generic_params {
            if let GenericParamKind::Type { .. } = generic_param.kind {
                for bound in generic_param.bounds {
                    if let GenericBound::Trait(x, ..) = bound {
                        if let Some(def_id) = x.trait_ref.trait_def_id() {
                            if target_trait_def_ids.contains(&def_id) {
                                return true;
                            }

                            // Check super-traits
                            for p in self.rcx.tcx().super_predicates_of(def_id).predicates {
                                if let PredicateKind::Trait(x) = p.0.kind().skip_binder() {
                                    if target_trait_def_ids.contains(&x.trait_ref.def_id) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    fn trait_in_where_relaxed(
        &self,
        target_trait_def_ids: &[DefId],
        where_predicates: &[WherePredicate],
    ) -> bool {
        for where_predicate in where_predicates {
            if let WherePredicate::BoundPredicate(x) = where_predicate {
                for bound in x.bounds {
                    if let GenericBound::Trait(y, ..) = bound {
                        if let Some(def_id) = y.trait_ref.trait_def_id() {
                            if target_trait_def_ids.contains(&def_id) {
                                return true;
                            }

                            for p in self.rcx.tcx().super_predicates_of(def_id).predicates {
                                if let PredicateKind::Trait(z) = p.0.kind().skip_binder() {
                                    if target_trait_def_ids.contains(&z.trait_ref.def_id) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

impl<'tcx> SendSyncVarianceChecker<'tcx> {
    /// Returns Some(DefId of ADT) if `impl Sync` for the ADT looks suspicious
    /// (ADT: struct / enum / union)
    pub fn suspicious_sync(
        &mut self,
        impl_id: LocalDefId,
        send_trait_def_id: DefId,
        sync_trait_def_id: DefId,
        copy_trait_def_id: DefId,
    ) -> Option<(DefId, BehaviorFlag)> {
        let rcx = self.rcx;
        let tcx = rcx.tcx();
        if let Some(trait_ref) = tcx.impl_trait_ref(impl_id) {
            if let ty::TyKind::Adt(adt_def, impl_trait_substs) = trait_ref.self_ty().kind() {
                let adt_did = adt_def.did;
                let adt_ty = tcx.type_of(adt_did);

                let mut need_send_sync: FxHashMap<PostMapIdx, BehaviorFlag> = FxHashMap::default();

                // Generic params that only occur within `PhantomData<_>`
                let phantom_params = self
                    .phantom_map
                    .entry(adt_did)
                    .or_insert_with(|| phantom_indices(tcx, adt_ty));

                // Get `AdtBehavior` per generic parameter.
                let adt_behavior = self
                    .behavior_map
                    .entry(adt_did)
                    .or_insert_with(|| adt_behavior(rcx, adt_did));

                // Initialize sets `need_send` & `need_sync`.
                if adt_def.is_struct() {
                    for gen_param in tcx.generics_of(adt_did).params.iter() {
                        if let GenericParamDefKind::Type { .. } = gen_param.kind {
                            let post_map_idx = PostMapIdx(gen_param.index);
                            let mut analyses = BehaviorFlag::NAIVE_SYNC_FOR_SYNC;

                            // Skip generic parameters that are only within `PhantomData<T>`.
                            if phantom_params.contains(&gen_param.index) {
                                need_send_sync.insert(post_map_idx, analyses);
                                continue;
                            }

                            analyses.insert(BehaviorFlag::RELAX_SYNC);
                            if let Some(behavior) = adt_behavior.get(&post_map_idx) {
                                if behavior.is_concurrent_queue() {
                                    analyses.insert(BehaviorFlag::API_SEND_FOR_SYNC);
                                }
                                if behavior.is_deref() {
                                    analyses.insert(BehaviorFlag::API_SYNC_FOR_SYNC);
                                }
                            }

                            need_send_sync.insert(post_map_idx, analyses);
                        }
                    }
                } else {
                    // Fields of enums/unions can be accessed by pattern matching.
                    // In this case, we require all generic parameters to be `Sync`.
                    for gen_param in tcx.generics_of(adt_did).params.iter() {
                        if let GenericParamDefKind::Type { .. } = gen_param.kind {
                            let post_map_idx = PostMapIdx(gen_param.index);
                            let mut analyses = BehaviorFlag::NAIVE_SYNC_FOR_SYNC;

                            // Skip generic parameters that are only within `PhantomData<T>`.
                            if phantom_params.contains(&gen_param.index) {
                                need_send_sync.insert(post_map_idx, analyses);
                                continue;
                            }

                            analyses.insert(BehaviorFlag::RELAX_SYNC);
                            analyses.insert(BehaviorFlag::API_SYNC_FOR_SYNC);
                            if let Some(behavior) = adt_behavior.get(&post_map_idx) {
                                if behavior.is_concurrent_queue() {
                                    analyses.insert(BehaviorFlag::API_SEND_FOR_SYNC);
                                }
                            }
                            need_send_sync.insert(post_map_idx, analyses);
                        }
                    }
                }

                // If the below assertion fails, there must be an issue with librustc we're using.
                // assert_eq!(tcx.generics_of(adt_did).params.len(), substs.len());
                let generic_param_idx_map =
                    generic_param_idx_mapper(&tcx.generics_of(adt_did).params, impl_trait_substs);

                // Iterate over predicates to check trait bounds on generic params.
                for atom in tcx
                    .param_env(impl_id)
                    .caller_bounds()
                    .iter()
                    .map(|x| x.kind().skip_binder())
                {
                    if let PredicateKind::Trait(trait_predicate) = atom {
                        if let ty::TyKind::Param(param_ty) = trait_predicate.self_ty().kind() {
                            let pre_map_idx = PreMapIdx(param_ty.index);
                            if let Some(mapped_idx) = generic_param_idx_map.get(&pre_map_idx) {
                                let trait_did = trait_predicate.def_id();
                                if trait_did == sync_trait_def_id {
                                    if let Some(analyses) = need_send_sync.get_mut(&mapped_idx) {
                                        analyses.remove(BehaviorFlag::API_SYNC_FOR_SYNC);
                                        analyses.remove(BehaviorFlag::NAIVE_SYNC_FOR_SYNC);
                                    }
                                    for analyses in need_send_sync.values_mut() {
                                        analyses.remove(BehaviorFlag::RELAX_SYNC);
                                    }
                                } else if (trait_did == send_trait_def_id)
                                    || (trait_did == copy_trait_def_id)
                                {
                                    if let Some(analyses) = need_send_sync.get_mut(&mapped_idx) {
                                        analyses.remove(BehaviorFlag::API_SEND_FOR_SYNC);
                                    }
                                }
                            }
                        }
                    }
                }

                return if need_send_sync.is_empty() {
                    None
                } else {
                    let mut detected = BehaviorFlag::empty();
                    for &analyses in need_send_sync.values() {
                        detected.insert(analyses);
                    }
                    if detected.is_empty() {
                        None
                    } else {
                        Some((adt_did, detected))
                    }
                };
            }
        }
        return None;
    }

    /// Returns `Some(DefId of ADT)` if `impl Send` for the ADT looks suspicious
    /// (ADT: struct / enum / union)
    pub fn suspicious_send(
        &mut self,
        impl_id: LocalDefId,
        send_trait_def_id: DefId,
        sync_trait_def_id: DefId,
        copy_trait_def_id: DefId,
    ) -> Option<(DefId, BehaviorFlag)> {
        let tcx = self.rcx.tcx();
        if let Some(trait_ref) = tcx.impl_trait_ref(impl_id) {
            if let ty::TyKind::Adt(adt_def, impl_trait_substs) = trait_ref.self_ty().kind() {
                let adt_did = adt_def.did;
                let adt_ty = tcx.type_of(adt_did);

                // Keep track of generic params that need to be `Send`.
                // let mut need_send: FxHashSet<PostMapIdx> = FxHashSet::default();

                let mut need_send_sync: FxHashMap<PostMapIdx, BehaviorFlag> = FxHashMap::default();

                // Generic params that only occur within `PhantomData<_>`
                let phantom_params = self
                    .phantom_map
                    .entry(adt_did)
                    .or_insert_with(|| phantom_indices(tcx, adt_ty));

                // If the below assertion fails, there must be an issue with librustc we're using.
                // assert_eq!(tcx.generics_of(adt_did).params.len(), substs.len());
                let generic_param_idx_map =
                    generic_param_idx_mapper(&tcx.generics_of(adt_did).params, impl_trait_substs);

                // Initialize set `need_send`
                for gen_param in tcx.generics_of(adt_did).params.iter() {
                    if let GenericParamDefKind::Type { .. } = gen_param.kind {
                        let post_map_idx = PostMapIdx(gen_param.index);
                        let mut analyses = BehaviorFlag::NAIVE_SEND_FOR_SEND;

                        // Skip generic parameters that are only within `PhantomData<T>`.
                        if phantom_params.contains(&gen_param.index) {
                            need_send_sync.insert(post_map_idx, analyses);
                            continue;
                        }

                        analyses.insert(BehaviorFlag::PHANTOM_SEND_FOR_SEND);
                        analyses.insert(BehaviorFlag::RELAX_SEND);
                        need_send_sync.insert(post_map_idx, analyses);
                    }
                }

                /* Our current filtering policy for `impl Send`:
                    1. Allow `T: Send` for `impl Send`
                    2. Allow `T: Sync` for `impl Send`
                        There are rare counterexamples (`!Send + Sync`) like `MutexGuard<_>`,
                        but we assume that in most of the common cases this holds true.
                    3. Allow `T: Copy` for `impl Send`
                        We shouldn't unconditionally allow `T: Copy for impl Send`,
                        due to the following edge case:
                        ```
                            // Below example be problematic for cases where T: !Sync .
                            struct Atom1<'a, T>(&'a T);
                            unsafe impl<'a, T: Copy> Send for Atom1<'a, T> {}
                        ```
                        TODO: implement additional checking to catch above edge case.
                */

                // Iterate over predicates to check trait bounds on generic params.
                for atom in tcx
                    .param_env(impl_id)
                    .caller_bounds()
                    .iter()
                    .map(|x| x.kind().skip_binder())
                {
                    if let PredicateKind::Trait(trait_predicate) = atom {
                        if let ty::TyKind::Param(param_ty) = trait_predicate.self_ty().kind() {
                            let pre_map_idx = PreMapIdx(param_ty.index);
                            if let Some(mapped_idx) = generic_param_idx_map.get(&pre_map_idx) {
                                let trait_did = trait_predicate.def_id();
                                if trait_did == send_trait_def_id
                                    || trait_did == sync_trait_def_id
                                    || trait_did == copy_trait_def_id
                                {
                                    need_send_sync.remove(&mapped_idx);
                                    for analyses in need_send_sync.values_mut() {
                                        analyses.remove(BehaviorFlag::RELAX_SEND);
                                    }
                                }
                            }
                        }
                    }
                }

                return if need_send_sync.is_empty() {
                    None
                } else {
                    let mut detected = BehaviorFlag::empty();
                    for &analyses in need_send_sync.values() {
                        detected.insert(analyses);
                    }
                    if detected.is_empty() {
                        None
                    } else {
                        Some((adt_did, detected))
                    }
                };
            }
        }
        return None;
    }
}