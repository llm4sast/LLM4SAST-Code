fn main() {
    let mut a = vec![1, 2, 3, 4, 5];
    let mut i = 0;
    while i < 5 {
        a[i] = i;
        i = i + 1;
    }
    if i < a.len() {
        let result = a[i];
        println!("Result: {}", result);
    } else {
        println!("Index out of bounds");
    }
}