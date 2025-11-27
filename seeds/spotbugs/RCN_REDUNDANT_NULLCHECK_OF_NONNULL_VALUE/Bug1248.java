import sfBugsNew.*;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
public class Bug1248 {
    @Nonnull
    private final List<Integer> foo = Collections.emptyList();
    public void test0() {
        if (foo != null) { System.out.println("non null"); }
        if (foo == null) { System.out.println("null"); }
    }
}