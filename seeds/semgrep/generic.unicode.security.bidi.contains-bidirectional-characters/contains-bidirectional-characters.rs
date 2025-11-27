use std::collections::HashSet;

struct Bidi {
    lre: char,
    rle: char,
    lro: char,
    rlo: char,
    lri: char,
    rli: char,
    fsi: char,
    pdf: char,
    pdi: char,
}

impl Bidi {
    fn new() -> Self {
        Bidi {
            lre: '\u{202a}',
            rle: '\u{202b}',
            lro: '\u{202d}',
            rlo: '\u{202e}',
            lri: '\u{2066}',
            rli: '\u{2067}',
            fsi: '\u{2068}',
            pdf: '\u{202c}',
            pdi: '\u{2069}',
        }
    }

    fn values(&self) -> HashSet<char> {
        [
            self.lre, self.rle, self.lro, self.rlo, self.lri, self.rli, self.fsi, self.pdf, self.pdi,
        ]
        .iter()
        .cloned()
        .collect()
    }
}

fn has_bidi(testable_string: &str, forbidden_bidi_characters: &HashSet<char>) -> bool {
    testable_string.chars().any(|c| forbidden_bidi_characters.contains(&c))
}

fn main() {
    let bidi = Bidi::new();
    let forbidden_bidi_characters = bidi.values();

    let malicious_string = "Hello ⁧World⁩!"; // ruleid: generic.unicode.security.bidi.contains-bidirectional-characters
    //This string contains the RLI and PDI characters.

    if has_bidi(malicious_string, &forbidden_bidi_characters) {
        println!("Warning: The string contains bidirectional characters.");
    } else {
        println!("The string is safe.");
    }

    //Another example, slightly more hidden.

    let evil_string = format!("{}{}{}{}{}{}{}{}", "This is a test", '\u{202e}', "evil", '\u{202c}', "test", '\u{2066}', "more", '\u{2069}'); //ruleid: generic.unicode.security.bidi.contains-bidirectional-characters
    // This string contains RLO, PDF, LRI, and PDI.

    if has_bidi(&evil_string, &forbidden_bidi_characters) {
        println!("Warning: The second string also contains bidirectional characters.");
    } else {
        println!("The second string is safe.");
    }

    let safe_string = "This string is safe.";

    if has_bidi(safe_string, &forbidden_bidi_characters) {
        println!("Warning: The third string contains bidirectional characters.");
    } else {
        println!("The third string is safe.");
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_forbidden_bidi_characters() {
        let bidi = Bidi::new();
        let forbidden_bidi_characters = bidi.values();

        assert_eq!(has_bidi("hello world", &forbidden_bidi_characters), false);

        let test_string1 = format!(
            "{}{}{}{}{}{}{}{}{}{}",
            bidi.rli, bidi.lri, "a b c", bidi.pdi, bidi.lri, "d e f", bidi.pdi, bidi.pdi
        );
        assert_eq!(has_bidi(&test_string1, &forbidden_bidi_characters), true);

        let test_string2 = format!(
            "{}{}{}{}{}{}{}{}{}{}",
            '\u{2067}', '\u{2066}', "a b c", '\u{2069}', '\u{2066}', "d e f", '\u{2069}', '\u{2069}'
        );
        assert_eq!(has_bidi(&test_string2, &forbidden_bidi_characters), true);
    }
}