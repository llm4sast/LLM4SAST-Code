use prusti_contracts::*;
fn main() {
    let s = [0;0];
    let _ = pure_oob(&s);
    let _ = third(&s);
}
#[pure]
fn pure_oob(s: &[i32]) -> i32 {
    s[0]  
}
fn third(s: &[i32]) -> i32 {
    s[2]  
}