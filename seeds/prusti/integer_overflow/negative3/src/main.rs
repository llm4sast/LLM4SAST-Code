use prusti_contracts::*;
struct U {
    f: u32,
}
fn test3(a: u32) {
    let b = a - 5;
}
fn main() {
    let a = 1;
    test3(a);
}