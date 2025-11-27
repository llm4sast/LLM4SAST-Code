use prusti_contracts::*;
#[ensures(result == old(x))] 
fn identity(x: i32) -> i32 {
    x + 1
}
fn main() {
    let x = i32::MAX;
    identity(x);
}