use prusti_contracts::*;
#[trusted]
fn random(i: u32) -> bool {
    unimplemented!()
}
fn continue_before_invariant() {
    let mut i = 0;
    'myloop: while { 
        if random(i + 2) {
            continue 'myloop;
        }
        random(i + 3)
    } {
        body_invariant!(true);
        i += 1;
    }
}
fn break_before_invariant() {
    let mut i = 0;
    'outer: while random(i + 0) {
        'inner: while {
            if random(i + 2) {
                break 'inner; 
            }
            if random(i + 2) {
                break 'outer; 
            }
            random(i + 3)
        } {
            body_invariant!(true);
            i += 1;
        }
    }
}
fn continue_outer_loop_before_invariant() {
    let mut i = 0;
    'outer: while random(i + 0) {
        'inner: while {
            if random(i + 2) {
                continue 'outer; 
            }
            random(i + 3)
        } {
            body_invariant!(true);
            i += 1;
        }
    }
}
fn main() {
    continue_before_invariant();
    break_before_invariant();
    continue_outer_loop_before_invariant();
}