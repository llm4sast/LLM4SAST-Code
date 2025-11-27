import constructorthrow.*;
import java.util.function.Supplier;
public class ConstructorThrowNegativeTest19 {
    Supplier<String> s;
    public ConstructorThrowNegativeTest19() { s = ConstructorThrowNegativeTest19::supplier; }
    private static String supplier() { throw new IllegalStateException(); }
}