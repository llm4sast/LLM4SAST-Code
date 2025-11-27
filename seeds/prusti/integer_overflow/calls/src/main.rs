use prusti_contracts::*;
#[requires(a + b <= std::u32::MAX)]
#[ensures(result == a + b)]
fn sum(a: u32, b: u32) -> u32 {
    a + b
}
fn sum_call_fail(a: u32, b: u32) -> Option<u32> {
    Some(sum(a, b)) 
}
fn sum_call_fail2(a: u32, b: u32) -> Option<u32> {
    if a + b <= std::u32::MAX {  
        Some(sum(a, b))
    } else {
        None
    }
}
fn main() {
    let a = std::u32::MAX;
    let b = 1;
    let result = sum_call_fail(a, b);
    let result2 = sum_call_fail2(a, b);
}