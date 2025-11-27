use std::sync::atomic::Ordering;
use std::sync::atomic::{AtomicBool, AtomicI32};
fn gen_rand_val_i32() -> i32 {
    rand::random::<i32>()
}
fn buggy_data_dep_i32() {
    let a = AtomicI32::new(gen_rand_val_i32());
    let v = a.load(Ordering::Relaxed);
    let v3 = v + 1;
    let v4 = if v3 > 10 { v3 + 2 } else { v3 - 1 };
    a.store(v4, Ordering::Relaxed);
    println!("{:?}", a);
}
fn main() {
    buggy_data_dep_i32();
}