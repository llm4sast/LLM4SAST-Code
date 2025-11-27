use prusti_contracts::*;
struct Account {
    bal: u32,
}
impl Account {
    #[pure]
    fn balance(&self) -> u32 {
        self.bal
    }
    #[ensures(self.balance() == old(self.bal) + amount)]
    fn deposit(&mut self, amount: u32) {
        self.bal = self.bal + amount;
    }
    #[ensures(self.bal == old(self.bal) - amount)]
    #[ensures(self.bal + other.bal == old(self.bal + other.bal))]
    fn transfer(&mut self, other: &mut Account, amount: u32) {
        other.deposit(amount);
    }
}
fn main() {
    let mut a = Account { bal: 100 };
    let mut b = Account { bal: 50 };
    a.transfer(&mut b, u32::MAX);
}