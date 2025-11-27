import constructorthrow.*;
public class ConstructorThrowTest3 {
    public ConstructorThrowTest3() { testMethod(); }
    public void testMethod() { throw new RuntimeException(); }
}