use prusti_contracts::*;
fn borrow(_x: &i32) {}
#[ensures(*n == old(*n))]
pub fn test1(n: &mut i32) {
    let mut i = 0;
    let mut cond = i < *n;
    while cond {
        i += 1;
        cond = i < *n;
    }
    assert!(false); 
}
fn main() {
    let mut x = i32::MAX;
    test1(&mut x);
}