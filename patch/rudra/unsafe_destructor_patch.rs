use std::os::raw::c_char;

pub struct StrcCtx {
    pub ptr: *mut c_char,
}

impl Drop for StrcCtx {
    fn drop(&mut self) {

    }
}