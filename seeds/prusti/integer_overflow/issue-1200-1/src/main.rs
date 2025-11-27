use prusti_contracts::*;
use std::cmp::min;
fn fast_fft_len(
    minimum_len: usize,
    once_factor: usize,
    multi_factor1: usize,
    multi_factor2: usize,
) -> usize {
    let mut product = once_factor;
    while product < minimum_len {
        product *= multi_factor2
    }
    let mut best = product;
    loop {
        match product.cmp(&minimum_len) {
            std::cmp::Ordering::Less => product *= multi_factor1,
            std::cmp::Ordering::Equal => return product,
            std::cmp::Ordering::Greater => {
                best = min(best, product);
                if product % multi_factor2 != 0 { 
                    return best;
                }
                product /= multi_factor2;
            }
        }
    }
}
fn main(){
    let minimum_len = usize::MAX;
    let once_factor = usize::MAX-1;
    let multi_factor1 = usize::MAX;
    let multi_factor2 = usize::MAX;
    let _ = fast_fft_len(minimum_len, once_factor, multi_factor1, multi_factor2);
}