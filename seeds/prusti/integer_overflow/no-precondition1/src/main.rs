use prusti_contracts::*;
#[ensures(match result { 4 => false, _ => true})] 
fn foo(x: i64, y: bool) -> i64 {
    let mut return_value = x * x;
    if y && x == 4 {
        return_value = -999; 
    }
    return_value
}
fn main() {
    let x = i64::MAX;
    let y = true;
    foo(x, y);
}