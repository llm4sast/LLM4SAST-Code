use std::sync;
use parking_lot;
fn parking_lot_rwlock() -> i32 {
    let rw1 = parking_lot::RwLock::new(1);
    let mut a = 0;
    match *rw1.read() {
        1 => { *rw1.write() += 1; },
        _ => {},
    };
    a
}
fn main() {
    parking_lot_rwlock();
}