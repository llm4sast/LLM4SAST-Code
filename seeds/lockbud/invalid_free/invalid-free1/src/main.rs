use std::mem;
use std::ptr::addr_of_mut;
fn uninit() {
    unsafe {
        #[allow(invalid_value, deprecated)]
        let _obj: Vec<i32> = mem::uninitialized();
    }
}
fn main() {
    uninit();
}