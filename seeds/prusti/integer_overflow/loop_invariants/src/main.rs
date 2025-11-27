extern crate prusti_contracts;
use prusti_contracts::*;
fn main() {
    let x = i32::MAX;
    let _ = summation(x);
}
#[requires(x >= 0)]
#[ensures(result == x * (x + 1) / 2)] 
fn summation(x: i32) -> i32 {
    let mut i = 1;
    let mut sum = 0;
    while i <= x {
        sum += i;
        i += 1;
    }
    sum
}