use prusti_contracts::*;
#[requires(start <= end)]
fn bar(slice: &[i32], start: usize, end: usize) {
    let subslice = &slice[start..end]; 
}
fn main() {
    let slice = [1, 2, 3, 4, 5];
    bar(&slice, 0, 6);
}