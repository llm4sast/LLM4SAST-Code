use prusti_contracts::*;
pub fn u64_u32(x: u64) -> u32 {
    x as u32  
}
pub fn u64_u16(x: u64) -> u16 {
    x as u16  
}
pub fn u64_u8(x: u64) -> u8 {
    x as u8  
}
pub fn u16_u8(x: u16) -> u8 {
    x as u8  
}
fn main() {
    u64_u32(std::u64::MAX);
    u64_u16(std::u64::MAX);
    u64_u8(std::u64::MAX);
    u16_u8(std::u16::MAX);
}