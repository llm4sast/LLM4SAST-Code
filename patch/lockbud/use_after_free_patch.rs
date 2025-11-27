fn drop_in_match() {
    fn create_obj(i: i32) -> Option<Vec<i32>> {
        if i > 10 {
            Some(vec![i])
        } else {
            None
        }
    }
    let ptr;
    let mut v = None;
    match create_obj(11) {
        Some(vec) => {
            v = Some(vec);
            ptr = v.as_mut().unwrap().as_mut_ptr();
        },
        None => ptr = std::ptr::null_mut(),
    };
    unsafe {
        if !ptr.is_null() {
            println!("{}", *ptr);
        }
    }
}