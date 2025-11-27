use prusti_contracts::*;
struct U {
    f: u32,
}
fn test1(a: &mut U, value: u32) {   
    a.f -= value;
}
fn main() {
    let mut a = U { f: 3 };
    test1(&mut a, 5);
}