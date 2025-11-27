use std::sync;
use parking_lot;
fn std_rwlock() -> i32 {
    let rw1 = sync::RwLock::new(1);
    let mut a = 0;
    match *rw1.read().unwrap() {
        1 => { *rw1.write().unwrap() += 1; },
        _ => {},
    };
    a
}
fn main() {
    std_rwlock();
}