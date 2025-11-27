use prusti_contracts::*;
trait Integer {
    fn lcm(&self, other: &Self) -> Self;
    fn gcd(&self, other: &Self) -> Self;
}
impl Integer for i8 {
    fn lcm(&self, other: &Self) -> Self {
        (*self * (*other / self.gcd(other))).abs() 
    }
    #[trusted]
    fn gcd(&self, other: &Self) -> Self {
        let mut m = *self;
        let mut n = *other;
        if m == 0 || n == 0 {
            return m | n;
        }
        let shift = (m | n).trailing_zeros();
        m >>= m.trailing_zeros();
        n >>= n.trailing_zeros();
        while m != n {
            if m > n {
                m -= n;
                m >>= m.trailing_zeros();
            } else {
                n -= m;
                n >>= n.trailing_zeros();
            }
        }
        m << shift
    }
}
fn main(){
    let x = 32;
    let y = 0;
    let z = x.lcm(&y);
}