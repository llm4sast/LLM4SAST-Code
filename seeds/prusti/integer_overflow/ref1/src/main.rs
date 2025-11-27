use prusti_contracts::*;
#[requires(*x != 0)]
#[ensures(result != 0)]
fn test1(x: &mut i32) -> i32 {
    let mut y = *x;
    y = y * 3 - 12;
    *x = y;
    y
}
fn main() {
    let mut x = i32::MAX;
    test1(&mut x);  
}