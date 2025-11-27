use std::mem;
use std::ptr::addr_of_mut;
fn assume() {
    let uninit = std::mem::MaybeUninit::<Vec<i32>>::uninit();
    unsafe {
        uninit.assume_init();   
    }    
}
fn main() {
    assume();
}