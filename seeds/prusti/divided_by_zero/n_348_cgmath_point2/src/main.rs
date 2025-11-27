use prusti_contracts::*;
use std::ops::Div;
pub struct Vector1<S> {
    pub x: S,
}
impl<S> Vector1<S> {
    #[trusted]
    fn new(x: S) -> Vector1<S> {
        Vector1 {
            x,
        }
    }
}
impl Div<Vector1<u64>> for u64 {
    type Output = Vector1<u64>;
    fn div(self, other: Vector1<u64>) -> Vector1<u64> {
        let (scalar, point) = (self, other);
        Vector1::new(
            scalar / point.x, 
        )
    }
}
fn main() {
    let scalar = 1;
    let point = Vector1::new(0);
    let _ = scalar / point;
}