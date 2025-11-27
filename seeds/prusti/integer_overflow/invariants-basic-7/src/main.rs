use prusti_contracts::*;
#[invariant(self.value <= 100)]
struct Percentage {
    value: u8,
}
#[requires(percentage.value >= 10)]
fn adjust_percentage(percentage: &mut Percentage, flag: bool) { 
   let mut f0 = flag;
   if flag {
       percentage.value += 10; 
       f0 = false;
   } else {
       percentage.value = 120; 
       f0 = true
   }
   if f0 {
       percentage.value -= 10; 
   } else {
       percentage.value -= 20; 
   }
}
fn main() {
    let mut p = Percentage { value: u8::MAX };
    adjust_percentage(&mut p, true);
}