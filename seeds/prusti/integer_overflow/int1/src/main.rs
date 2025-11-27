use prusti_contracts::*;
#[ensures(result != 86)]
fn test1(x: i32) -> i32 {
    let y = x + 1;
    y * 2
}
fn main() {
    test1(i32::MAX);
}