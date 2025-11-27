use prusti_contracts::*;
#[pure]
fn test_minus_i32(x: i32) -> i32 {
    -x 
}
fn main() {
    test_minus_i32(i32::MIN);
}