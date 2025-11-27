use prusti_contracts::*;
#[derive(Copy, Clone)]
struct X {
    a: i32
}
impl X {
    #[pure]
    fn get_a(&self) -> i32{
        self.a
    }
}
#[pure]
fn baz(x: X) -> X {
    X {a: x.a + 1}
}
#[requires(y.a == 5)] 
fn test(x: i32, y: X) {
    let z = y.get_a();
    assert!(z == baz(y).a)
}
fn main(){
    let x = i32::MAX;
    let y = X {a: x};
    test(x, y);
}