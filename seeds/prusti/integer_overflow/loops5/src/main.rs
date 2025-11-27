use prusti_contracts::*;
fn borrow(_x: &i32) {}
#[ensures(*n == old(*n))]
pub fn test3_1(n: &i32) {
    let mut i = 0;
    let mut cond = i < *n;
    while cond {
        i += 1;
        borrow(n);
        cond = i < *n;
        assert!(false); 
    }
}
fn main() {
    let x = i32::MAX;
    test3_1(&x);
}