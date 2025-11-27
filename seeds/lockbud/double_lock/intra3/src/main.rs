use std::sync;
use parking_lot;
fn parking_lot_mutex() {
    let mu1 = parking_lot::Mutex::new(1);
    match *mu1.lock() {
        1 => {},
        _ => { *mu1.lock() += 1; },
    };
}
fn main() {
    parking_lot_mutex();
}