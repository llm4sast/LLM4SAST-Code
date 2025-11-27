fn std_deadlock_wait_patch() {
    use std::sync::{Condvar, Mutex};
    struct CondPair {
        lock: Mutex<bool>,
        cvar: Condvar,
        other: Mutex<i32>,
    }
    
    impl CondPair {
        fn new() -> Self {
            Self {
                lock: Mutex::new(false),
                cvar: Condvar::new(),
                other: Mutex::new(1),
            }
        }
        fn wait(&self) {
            {
                let _i = self.other.lock().unwrap();
            }
            let mut started = self.lock.lock().unwrap();
            println!("start waiting!");
            self.cvar.wait_while(started, |started| !*started).unwrap();
            println!("end waiting");
        }
        fn notify(&self) {
            {
                let _i = self.other.lock().unwrap();
            }
            let mut started = self.lock.lock().unwrap();
            println!("start notifying!");
            *started = true;
            self.cvar.notify_one();
            println!("end notifying!");
        }
    }
    
    let condvar1 = Arc::new(CondPair::new());
    let condvar2 = condvar1.clone();
    
    let th1 = thread::spawn(move || {
        condvar1.wait();
    });

    condvar2.notify();
    th1.join().unwrap();
}