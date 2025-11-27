use prusti_contracts::*;
struct Number<A, B, C> {
    a: A,
    b: B,
    c: C,
}
#[ensures(arg.b == old(arg.b) - 1000)]
fn decr1<D, E>(arg: &mut Number<D, i32, E>) {
    arg.b -= 1000;
}
#[requires(arg.a.b == 3000)]
#[requires(arg.b.b == 5000)]
#[requires(arg.c.b == 7000)]
fn test1<X>(arg: &mut Number<Number<i8, i32, u8>, Number<i16, i32, i64>, Number<X, i32, usize>>) {
    decr1(&mut arg.a);
    decr1(&mut arg.c);
    assert!(arg.a.b == 2000);
    assert!(arg.b.b == 5000);
    assert!(arg.c.b == 6000);
    assert!(arg.b.b == 4000);
}
#[requires(arg.a.b == 3000)]
#[requires(arg.b.b == 5000)]
#[requires(arg.c.b == 7000)]
fn test2<X, Y>(arg: &mut Number<Number<Y, i32, u8>, Number<i16, i32, Y>, Number<X, i32, usize>>) {
    decr1(&mut arg.a);
    decr1(&mut arg.c);
    assert!(arg.a.b == 2000);
    assert!(arg.b.b == 5000);
    assert!(arg.c.b == 6000);
    assert!(arg.a.b == 2000); 
    assert!(arg.b.b == 4000);
}
#[requires(arg.a.b == 3000)]
#[requires(arg.b.b == 5000)]
#[requires(arg.c.b == 7000)]
fn test3<X, Y, Z>(arg: &mut Number<Number<X, i32, Z>, Number<i16, i32, Z>, Number<Y, i32, Y>>) {
    decr1(&mut arg.a);
    decr1(&mut arg.c);
    assert!(arg.a.b == 2000);
    assert!(arg.b.b == 5000);
    assert!(arg.c.b == 6000);
    assert!(arg.b.b == 4000);
    assert!(arg.c.b == 6000); 
}
fn main() {
    let mut d1 = Number {
        a: Number {
            a: 0_i8,
            b: 3000_i32,
            c: 0_u8,
        },
        b: Number {
            a: 0_i16,
            b: 5000_i32,
            c: 0_i64,
        },
        c: Number {
            a: (),
            b: i32::MIN,
            c: 0_usize,
        },
    };
    test1::<()>(&mut d1); 
}