use prusti_contracts::*;
#[pure]
fn test_minus_isize(x: isize) -> isize {
    -x 
}
fn main() {
    test_minus_isize(isize::MIN);
}