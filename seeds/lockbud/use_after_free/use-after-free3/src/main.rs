fn escape_to_global() {
    use std::os::raw::{c_char, c_int};
    use std::ptr;
    #[repr(C)]
    pub struct hostent {
        h_name: *mut c_char,
        h_aliases: *mut *mut c_char,
        h_addrtype: c_int,
        h_length: c_int,
        h_addr_list: *mut *mut c_char,
    }
    static mut HOST_ENTRY: hostent = hostent {
        h_name: ptr::null_mut(),
        h_aliases: ptr::null_mut(),
        h_addrtype: 0,
        h_length: 0,
        h_addr_list: ptr::null_mut(),
    };
    static mut HOST_NAME: Option<Vec<u8>> = None;
    static mut HOST_ALIASES: Option<Vec<Vec<u8>>> = None;
    pub unsafe extern "C" fn gethostent() -> *const hostent {
        HOST_ALIASES = Some(vec![vec![0, 1, 2], vec![3, 4, 5]]);
        let mut host_aliases: Vec<*mut i8> = HOST_ALIASES
            .as_mut()
            .unwrap()
            .iter_mut()
            .map(|x| x.as_mut_ptr() as *mut i8)
            .collect();
        host_aliases.push(ptr::null_mut());
        host_aliases.push(ptr::null_mut());
        HOST_NAME = Some(vec![0, 1, 2]);
        HOST_ENTRY = hostent {
            h_name: HOST_NAME.as_mut().unwrap().as_mut_ptr() as *mut c_char,
            h_aliases: host_aliases.as_mut_slice().as_mut_ptr() as *mut *mut i8,
            h_addrtype: 0,
            h_length: 4,
            h_addr_list: ptr::null_mut(),
        };
        &HOST_ENTRY as *const hostent
    }
    unsafe {
        let h = gethostent();
        println!("{:?}", *(&*h).h_aliases);
    }
}
fn main() {
    escape_to_global();
}