import constructorthrow.*;
import java.util.function.Supplier;
public class ConstructorThrowTest23 {
    Supplier<String> s;
    public ConstructorThrowTest23() {
        s = ConstructorThrowTest23::supplier;
        String str = s.get();
    }
    private static String supplier() { throw new IllegalStateException(); }
}