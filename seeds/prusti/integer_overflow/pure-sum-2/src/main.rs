use prusti_contracts::*;
#[pure]
fn sum(x:i64) -> i64 {
    if x <= 0 {
        0
    } else {
        x + sum(x - 1)
    }
}
#[ensures(sum(5) == 0)] 
fn test1() {}
fn main() {
    test1();
    sum(i64::MAX);
}