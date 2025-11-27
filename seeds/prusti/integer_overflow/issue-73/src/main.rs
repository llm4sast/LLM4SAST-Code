#![allow(unused_comparisons)]
use prusti_contracts::*;
pub struct VecWrapperusize{
    v: Vec<usize>
}
impl VecWrapperusize {
    #[trusted]
    #[pure]
    pub fn len(&self) -> usize {
        self.v.len()
    }
    #[trusted]
    #[ensures(result.len() == length)]
    #[ensures(forall(|i: usize| (0 <= i && i < length) ==> result.lookup(i) == 0))]
    pub fn new(length: usize) -> Self {
        VecWrapperusize{ v: vec![0; length] }
    }
    #[trusted]
    #[pure]
    #[requires(0 <= index && index < self.len())]
    pub fn lookup(&self, index: usize) -> usize {
        self.v[index]
    }
    #[trusted]
    #[requires(0 <= index && index < self.len())]
    #[ensures(self.lookup(old(index)) == old(value))]
    pub fn store(&mut self, index: usize, value: usize) {
        self.v[index] = value;
    }
}
pub fn binary_search_rec(arr: VecWrapperusize, target: usize) -> Option<usize> {
    let len = arr.len();
    return binary_search_help(arr, 0, len - 1, target); 
}
fn binary_search_help(arr: VecWrapperusize, left: usize, right: usize, target: usize) -> Option<usize> {
    if left <= right {
        let mid = (left + right) / 2;
        if arr.lookup(mid) < target { 
            return binary_search_help(arr, mid + 1, right, target);
        } else if arr.lookup(mid) > target {
            return binary_search_help(arr, left, mid - 1, target);
        } else {
            return Some(mid);
        }
    }
    return None;
}
fn main() {
    let arr = VecWrapperusize::new(20);
    let _ = binary_search_help(arr, usize::MAX, usize::MAX, usize::MAX);
}