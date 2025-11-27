use prusti_contracts::*;
#[requires(end <= slice.len())]
fn foo(slice: &[i32], start: usize, end: usize) {
    let subslice = &slice[start..end]; 
}
fn main() {
    let slice = [1, 2, 3, 4, 5];
    foo(&slice, 0, 6);
}