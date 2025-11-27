use prusti_contracts::*;
#[pure]
fn test_minus_i64(x: i64) -> i64 {
    -x 
}
fn main() {
    test_minus_i64(i64::MIN);
}