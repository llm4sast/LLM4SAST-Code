use prusti_contracts::*;
#[pure]
#[ensures(result != 42)]
fn test2(x: i32) -> i32 {
    x + 21
}
fn main() {
    test2(i32::MAX);
}