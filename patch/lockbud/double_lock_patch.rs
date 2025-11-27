fn feed_chunk(&self, hash: H256, chunk: &[u8], is_state: bool) {
    let mut restoration = self.restoration.lock();
    match self.feed_chunk_with_restoration(&mut restoration, hash, chunk, is_state) {
        Ok(())
        | Err(Error(SnapshotErrorKind::Snapshot(SnapshotError::RestorationAborted), _)) => (),
        Err(e) => {
            warn!("Encountered error during snapshot restoration: {}", e);
            *restoration = None;
            *self.status.lock() = RestorationStatus::Failed;
            let _ = fs::remove_dir_all(self.restoration_dir());
        }
    }
}