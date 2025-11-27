#[inline]
fn div_rem(x: usize, d: usize) -> (usize, usize)
{
    (
        x / d,  
        x % d
    )
}
fn main(){
    div_rem(1, 0);
}