import constructorthrow.*;
public class ConstructorThrowTest21 {
    public ConstructorThrowTest21() { ConstructorThrowTest21.test(); }
    private static String test() { throw new IllegalStateException(); }
}