import constructorthrow.*;
public class ConstructorThrowTest24 {
    String s;
    public ConstructorThrowTest24() { s = ConstructorThrowTest24.supplier(); }
    private static String supplier() { throw new IllegalStateException(); }
}