use prusti_contracts::*;
#[requires(*x != 0)]
#[ensures(result != 14)]
fn test2(x: &i32) -> i32 {
    let y = *x + 1;
    match y {
        x => x * 2
    }
}
fn main() {
    let x = i32::MAX;
    test2(&x);
}