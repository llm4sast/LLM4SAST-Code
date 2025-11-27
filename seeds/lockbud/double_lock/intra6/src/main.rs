use std::sync;
use parking_lot;
fn std_mutex() {
    let mu1 = sync::Mutex::new(1);
    match *mu1.lock().ok().unwrap() {
        1 => {},
        _ => { *mu1.lock().unwrap() += 1; },
    };
}
fn main() {
    std_mutex();
}