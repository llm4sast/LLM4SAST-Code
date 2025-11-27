use std::sync;
struct Foo {
    mu1: sync::Arc<sync::Mutex<i32>>,
    rw1: sync::RwLock<i32>,
    mu2: parking_lot::Mutex<i32>,
    rw2: parking_lot::RwLock<i32>,
    mu3: spin::Mutex<i32>,
    rw3: spin::RwLock<i32>,
}
impl Foo {
    fn new() -> Self {
        Self {
            mu1: sync::Arc::new(sync::Mutex::new(1)),
            rw1: sync::RwLock::new(1),
            mu2: parking_lot::Mutex::new(1),
            rw2: parking_lot::RwLock::new(1),
            mu3: spin::Mutex::new(1),
            rw3: spin::RwLock::new(1),
        }
    }
    fn parking_lot_rwlock_write_1(&self) {
        match *self.rw2.write() {
            1 => { self.parking_lot_rwlock_write_2(); },
            _ => {},
        };
    }
    fn parking_lot_rwlock_write_2(&self) {
        *self.rw2.write() += 1;
    }
}
fn main() {
    let foo1 = Foo::new();
    foo1.parking_lot_rwlock_write_1();
    foo1.parking_lot_rwlock_write_2();
}