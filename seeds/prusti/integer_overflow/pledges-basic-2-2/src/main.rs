use prusti_contracts::*;
struct Nonsense {
    m2: u32, 
    m3: u32, 
    m5: u32, 
}
impl Nonsense {
    #[pure]
    fn valid(&self) -> bool {
        self.m2 % 2 == 0 && self.m3 % 3 == 0 && self.m5 % 5 == 0
    }
    #[requires(self.valid())]
    #[assert_on_expiry(
        *result % 3 == 0,
        self.valid()
    )]
    fn m3_mut(&mut self) -> &mut u32 {
        &mut self.m3
    }
}
#[requires(arg.valid())]
#[ensures(arg.valid())]
fn test(arg: &mut Nonsense) {
    let m3 = arg.m3_mut(); 
    *m3 += 3;
}
fn main() {
    let mut n = Nonsense { m2: 2, m3: u32::MAX, m5: 5 };
    test(&mut n);
}