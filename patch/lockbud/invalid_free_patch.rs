fn assume() {
    let mut uninit = std::mem::MaybeUninit::<Vec<i32>>::uninit();
    unsafe {
        uninit.as_mut_ptr().write(Vec::new());
        uninit.assume_init();
    }
}