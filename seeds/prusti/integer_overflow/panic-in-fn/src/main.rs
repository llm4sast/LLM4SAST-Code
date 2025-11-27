#![feature(nll)]
#![feature(box_patterns)]
use prusti_contracts::*;
use std::borrow::BorrowMut;
struct List {
    value: u32,
    next: Option<Box<List>>,
}
#[allow(unconditional_recursion)]
fn diverging() -> ! {
    diverging()
}
#[pure]
fn lookup(head: &List, index: isize) -> u32 {
    if index == 0 {
        head.value
    } else {
        match head.next {
            Some(box ref tail) => lookup(tail, index - 1),
            None => diverging() 
        }
    }
}
#[pure]
fn lookup2(head: &List, index: isize) -> u32 {
    if index == 0 {
        head.value
    } else {
        match head.next {
            Some(box ref tail) => lookup(tail, index - 1),
            None => panic!() 
        }
    }
}
#[pure]
fn lookup3(head: &List, index: isize) -> u32 {
    if index == 0 {
        head.value
    } else {
        match head.next {
            Some(box ref tail) => lookup(tail, index - 1),
            None => unreachable!() 
        }
    }
}
#[pure]
fn lookup4(head: &List, index: isize) -> u32 {
    if index == 0 {
        head.value
    } else {
        match head.next {
            Some(box ref tail) => lookup(tail, index - 1),
            None => unimplemented!() 
        }
    }
}
#[pure]
fn lookup5(head: &List, index: isize) -> u32 {
    if index == 0 {
        head.value
    } else {
        match head.next {
            Some(box ref tail) => lookup(tail, index - 1),
            None => {
                assert!(false); 
                0
            }
        }
    }
}
fn main() {
    let l = List {
        value: 1,
        next: Some(Box::new(List {
            value: 2,
            next: None,
        })),
    };
    let _ = lookup(&l, isize::MIN);
    let _ = lookup2(&l,  isize::MIN);
    let _ = lookup3(&l,  isize::MIN);
    let _ = lookup4(&l,  isize::MIN);
    let _ = lookup5(&l,  isize::MIN);
}