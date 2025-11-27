use std::sync::atomic::Ordering;
use std::sync::atomic::{AtomicBool, AtomicI32};
fn gen_rand_val_i32() -> i32 {
    rand::random::<i32>()
}
fn buggy_control_dep_i32() {
    let a = AtomicI32::new(gen_rand_val_i32());
    let v = a.load(Ordering::Relaxed);
    let v3 = v + 1;
    let v4 = if v3 > 10 { v3 + 2 } else { v3 - 1 };
    if v4 > 11 && gen_rand_val_i32() < 12 {
        a.store(10, Ordering::Relaxed);
    }
    println!("{:?}", a);
}
fn main() {
    buggy_control_dep_i32();
}