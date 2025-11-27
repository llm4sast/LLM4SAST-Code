fn main() {
    let mut t: i32 = 0;
    while t < 100 {
        t += 1;
    }
    let mut a: u32 = 1;
    a = a.saturating_sub(t as u32);
}