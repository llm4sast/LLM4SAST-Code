use libc::c_char;
use std::ffi::CStr;
unsafe fn fmt_time(date: &Date) -> *const c_char {
    let days = vec!["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
    let months = vec![
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
    ];
    let year = 1900 + date.tm_year;
    let time_str = format!(
        "{} {} {:2} {:02}:{:02}:{:02} {:4}\n\0\0\0\0\0\0\0\0\0\0\0\0\0",
        days[date.tm_wday as usize],
        months[date.tm_mon as usize],
        date.tm_mday,
        date.tm_hour,
        date.tm_min,
        date.tm_sec,
        year
    );
    time_str[0..26].as_ptr() as _
}
struct Date {
    tm_year: usize,
    tm_wday: usize,
    tm_mon: usize,
    tm_mday: usize,
    tm_hour: usize,
    tm_min: usize,
    tm_sec: usize,
}
fn escape_to_return() {
    let date = Date {
        tm_year: 1,
        tm_wday: 1,
        tm_mon: 1,
        tm_mday: 1,
        tm_hour: 1,
        tm_min: 1,
        tm_sec: 1,
    };
    unsafe {
        let ptr = fmt_time(&date);
        println!("{:?}", CStr::from_ptr(ptr));
    }
}
fn main() {
    escape_to_return();
}