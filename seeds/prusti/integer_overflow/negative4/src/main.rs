use prusti_contracts::*;
struct U {
    f: u32,
}
fn test3(a: u32) {
    let b = a - 5;
}
fn test4(a: u32) {
    let b = a - 5;
    test3(b); 
}
fn main() {
    let a = 1;
    test4(a);
}