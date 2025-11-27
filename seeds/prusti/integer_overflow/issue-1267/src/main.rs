use prusti_contracts::*;
#[pure]
#[ensures(double(x) % 2 == 0)] 
fn double(x: i64) -> i64 { 
    2 * x
}
fn main() {
    double(i64::MAX);
}