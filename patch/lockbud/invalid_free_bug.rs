fn assume() {
    let mut uninit = std::mem::MaybeUninit::<Vec<i32>>::uninit();
    unsafe {
        uninit.assume_init();
    }
}