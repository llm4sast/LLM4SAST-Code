extern crate prusti_contracts;
use prusti_contracts::*;
fn main() {
    let mut list = List::new();
    list.push(5);
    list.push(10);
    list.lookup(usize::MIN);

}
pub struct List<T> {
    head: Link<T>,
}
type Link<T> = Option<Box<Node<T>>>;
struct Node<T> {
    elem: T,
    next: Link<T>,
}
#[extern_spec(std::mem)]
#[ensures(snap(dest) === src)]
#[ensures(result === old(snap(dest)))]
fn replace<T>(dest: &mut T, src: T) -> T;
#[extern_spec]
impl<T> std::option::Option<T> {
    #[requires(self.is_some())]
    #[ensures(old(self) === Some(result))]
    pub fn unwrap(self) -> T;
    #[pure]
    #[ensures(result == matches!(self, None))]
    pub const fn is_none(&self) -> bool;
    #[pure]
    #[ensures(result == matches!(self, Some(_)))]
    pub const fn is_some(&self) -> bool;
    #[ensures(result === old(snap(self)))]
    #[ensures(self.is_none())]
    pub fn take(&mut self) -> Option<T>;
}
impl<T> List<T> {
    #[pure]
    pub fn len(&self) -> usize {
        link_len(&self.head)
    }
    #[pure]
    fn is_empty(&self) -> bool {
        matches!(self.head, None)
    }
    #[ensures(result.len() == 0)]
    pub fn new() -> Self {
        List { head: None }
    }
    #[pure]
    #[requires(index < self.len())]
    pub fn lookup(&self, index: usize) -> &T {
        link_lookup(&self.head, index)
    }
    #[ensures(self.len() == old(self.len()) + 1)]
    #[ensures(snap(self.lookup(0)) === elem)]
    #[ensures(forall(|i: usize| (i < old(self.len())) ==>
                 old(self.lookup(i)) === self.lookup(i + 1)))]
    pub fn push(&mut self, elem: T) {
        let new_node = Box::new(Node {
            elem,
            next: self.head.take(),
        });
        self.head = Some(new_node);
    }
    predicate! {
        fn head_removed(&self, prev: &Self) -> bool {
            self.len() == prev.len() - 1 
            && forall(|i: usize| 
                (1 <= i && i < prev.len())
                    ==> prev.lookup(i) === self.lookup(i - 1))
        }
    }
    #[ensures(old(self.is_empty()) ==>
        result.is_none() &&
        self.is_empty()
    )]
    #[ensures(!old(self.is_empty()) ==>
        self.head_removed(&old(snap(self))) &&
        result === Some(snap(old(snap(self)).lookup(0)))
    )]
    pub fn try_pop(&mut self) -> Option<T> {
        match self.head.take() {
            None => None,
            Some(node) => {
                self.head = node.next;
                Some(node.elem)
            }
        }
    }
    #[requires(!self.is_empty())]
    #[ensures(self.head_removed(&old(snap(self))))]
    #[ensures(result === old(snap(self)).lookup(0))]
    pub fn pop(&mut self) -> T {
        self.try_pop().unwrap()
    }
    #[pure]
    #[requires(!self.is_empty())]
    pub fn peek(&self) -> &T {
        self.lookup(0)
    }
    #[trusted] 
    #[requires(!self.is_empty())]
    #[ensures(snap(result) === old(snap(self.peek())))]
    pub fn peek_mut(&mut self) -> &mut T {
        if let Some(node) = &mut self.head {
            &mut node.elem
        } else {
            unreachable!()
        }
    }
}
#[pure]
#[requires(index < link_len(link))]
fn link_lookup<T>(link: &Link<T>, index: usize) -> &T {
    match link {
        Some(node) => {
            if index == 0 {
                &node.elem
            } else {
                link_lookup(&node.next, index - 1)
            }
        }
        None => unreachable!(),
    }
}
#[pure]
fn link_len<T>(link: &Link<T>) -> usize {
    match link {
        None => 0,
        Some(node) => 1 + link_len(&node.next),
    }
}
#[cfg(prusti)]
mod prusti_tests {
    use super::*;
    fn _test_list(){
        let mut list = List::new(); 
        prusti_assert!(list.is_empty() && list.len() == 0); 
        list.push(5);
        list.push(10);
        prusti_assert!(!list.is_empty() && list.len() == 2); 
        prusti_assert!(*list.lookup(0) == 10); 
        prusti_assert!(*list.lookup(1) == 5); 
        let x = list.pop();
        prusti_assert!(x == 10); 
        match list.try_pop() {
            Some(y) => assert!(y == 5),
            None => unreachable!()
        }
        let z = list.try_pop();
        prusti_assert!(list.is_empty() && list.len() == 0); 
        prusti_assert!(z.is_none()); 
    }
    fn _test_peek() {
        let mut list = List::new();
        list.push(16);
        prusti_assert!(list.peek() === list.lookup(0));
        prusti_assert!(*list.peek() == 16);
        list.push(5);
        prusti_assert!(*list.peek() == 5);
        list.pop();
        prusti_assert!(*list.peek() == 16);
    }
    fn _test_peek_mut() {
        let mut list = List::new();
        list.push(8);
        list.push(16);
        {
            let first = list.peek_mut();
            prusti_assert!(*first == 16);
            *first = 5; 
            prusti_assert!(*first == 5);
        }
        prusti_assert!(list.len() == 2); 
        prusti_assert!(*list.lookup(0) == 5); 
        prusti_assert!(*list.lookup(1) == 8); 
    }
}