import sfBugs.*;
import java.util.List;
public class Bug1844673 {
    public void falsePositive(List actual, List expected) {
        if (actual == expected)
            return;
        if ((actual == null && expected != null) || (actual != null && expected == null)) { return; }
        if (actual.size() == expected.size()) 
            return;
        throw new AssertionError("Not the same");
    }
}