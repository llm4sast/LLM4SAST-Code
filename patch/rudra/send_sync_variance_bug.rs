unsafe impl<T: ?Sized + Send, U: ?Sized> Send for MappedMutexGuard<'_, T, U> {}
unsafe impl<T: ?Sized + Sync, U: ?Sized> Sync for MappedMutexGuard<'_, T, U> {}