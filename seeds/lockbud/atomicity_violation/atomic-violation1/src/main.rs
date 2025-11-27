use std::sync::atomic::Ordering;
use std::sync::atomic::{AtomicBool, AtomicI32};
fn gen_rand_val_bool() -> bool {
    rand::random::<bool>()
}
fn buggy_control_dep_bool() {
    let a = AtomicBool::new(gen_rand_val_bool());
    if a.load(Ordering::Relaxed) {
        a.store(false, Ordering::Relaxed);
    }
    println!("{}", a.load(Ordering::Relaxed));
}
fn main() {
    buggy_control_dep_bool();
}