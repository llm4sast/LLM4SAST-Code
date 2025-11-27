use prusti_contracts::*;
fn test1() {
    let a = 1;
    let b = 2;
    let c = a + b;
    assert!(c == 3);
}
fn test2() {
    let a = 1;
    let b = 2;
    let c = a + b;
    assert!(c == 4);    
}
fn test3(a: i32, b: i32) -> i32 {
    a + b   
}
fn test4() {
    let x = 1 / 3;
    assert!(x == 0);
    let x = 4 / 3;
    assert!(x == 1);
}
fn test5() {
    let x = 1 / 3;
    assert!(x == 1);    
}
fn test6() {
    let x = 1 * 3;
    assert!(x == 3);
}
fn test7() {
    let x = 1 * 3;
    assert!(x == 1);    
}
fn main() {
    test1();
    test2();
    test3(i32::MAX, 2);
    test4();
    test5();
    test6();
    test7();
}