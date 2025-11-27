use prusti_contracts::*;
struct U {
    f: u32,
}
fn test2(a: &mut U) -> &mut u32 { 
    a.f -= 5;
    &mut a.f
}
fn main() {
    let mut a = U { f: 3 };
    test2(&mut a);
}