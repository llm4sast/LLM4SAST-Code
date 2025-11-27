use prusti_contracts::*;
fn foo2(x: i64, y: bool) -> i64 {
    let mut return_value = x * x;
    if y && x == 4 {
        return_value = -999; 
    }
    assert!(return_value >= 0);  
    return_value
}
fn main() {
    let x = i64::MAX;
    let y = true;
    foo2(x, y);
}