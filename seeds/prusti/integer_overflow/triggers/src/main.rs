use prusti_contracts::*;
#[pure]
fn fib(n: isize) -> isize {
    if n <= 1 {
        1
    } else {
        fib(n-1) + fib(n-2)
    }
}
pub fn test1() {
    assert!(fib(0) == 1);
    assert!(fib(1) == 1);
    assert!(fib(2) == 2);
    assert!(fib(3) == 3);
    assert!(fib(4) == 5);
}
pub fn test2() {
    assert!(fib(4) == 5);   
}
#[pure]
fn dummy(_n: isize, _res: isize) -> bool { true }
#[requires(
    forall(|n: isize, res: isize| fib(n) == res, triggers=[(dummy(n, res),)])
)]
pub fn test3() {
    dummy(4, 5);
    assert!(fib(4) == 5);
}
fn main() {
    test1();
    test2();
    test3();
    fib(isize::MAX);
}