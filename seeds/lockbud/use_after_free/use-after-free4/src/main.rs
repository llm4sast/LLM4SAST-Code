fn escape_to_param() {
    use std::ptr;
    use std::sync::atomic::{AtomicPtr, Ordering};
    struct Owned<T> {
        data: T,
    }
    impl<T> Owned<T> {
        fn as_raw(&self) -> *mut T {
            &self.data as *const _ as *mut _
        }
    }
    fn opt_owned_as_raw<T>(val: &Option<Owned<T>>) -> *mut T {
        val.as_ref().map(Owned::as_raw).unwrap_or(ptr::null_mut())
    }
    struct Obj<T> {
        ptr: AtomicPtr<T>,
    }
    impl<T> Obj<T> {
        fn null() -> Self {
            Obj {
                ptr: AtomicPtr::new(ptr::null_mut()),
            }
        }
        fn load(&self, ord: Ordering) -> *mut T {
            self.ptr.load(ord)
        }
        fn store(&self, owned: Option<Owned<T>>, ord: Ordering) {
            self.ptr.store(opt_owned_as_raw(&owned), ord);
        }
    }
    let o = Obj::<Vec<i32>>::null();
    let owned = Some(Owned { data: Vec::new() });
    o.store(owned, Ordering::Relaxed);
    let p = o.load(Ordering::Relaxed);
    unsafe {
        println!("{:?}", *p);
    }
}
fn main() {
    escape_to_param();
}