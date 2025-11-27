import sfBugs.*;
import javax.annotation.Nonnull;
public class Bug2800825 {
    public @Nonnull
    Object getNonNullValue() { return "dummy"; }
    public void falsePositive() {
        if (null == getNonNullValue()) { throw new IllegalStateException(); }
        System.out.println("bar");
    }
}