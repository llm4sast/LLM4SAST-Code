#[allow(arithmetic_overflow)]
pub fn evil() {
    let _ : usize = 1 - 2;
    panic!();
}

fn main() {
    evil();
}
