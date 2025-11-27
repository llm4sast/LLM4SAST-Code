use prusti_contracts::*;
struct Number<A, B, C> {
    a: A,
    b: B,
    c: C,
}
#[ensures(arg.b.b == old(arg.b.b) - 1000)]
fn test2<A, B, C, D>(arg: &mut Number<A, Number<B, i32, C>, D>) {
    arg.b.b -= 1000;
}
#[requires(arg.a.b == 3000)]
#[requires(arg.b.b == 5000)]
#[requires(arg.c.b == 7000)]
fn test3<X>(arg: &mut Number<Number<i8, i32, u8>, Number<i16, i32, i64>, Number<isize, i32, usize>>) {
    test2(arg);
    assert!(arg.a.b == 2000); 
    assert!(arg.b.b == 4000);
}
fn main() {
    let mut a = Number { a: 1, b: 3000, c: 5 };
    let mut b = Number { a: 2, b: i32::MIN, c: 7 };
    let mut c = Number { a: 3, b: i32::MIN, c: 9 };
    let mut d = Number { a: a, b: b, c: c };
    test3::<()>(&mut d);
}