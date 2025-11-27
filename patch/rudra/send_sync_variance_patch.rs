unsafe impl<T: ?Sized + Send, U: ?Sized Send> Send for MappedMutexGuard<'_, T, U> {}
unsafe impl<T: ?Sized + Sync, U: ?Sized Sync> Sync for MappedMutexGuard<'_, T, U> {}