import constructorthrow.*;
public class ConstructorThrowTest16 {
    public ConstructorThrowTest16() {
        testMethod();
        throw new RuntimeException();
    }
    public void testMethod() { throw new RuntimeException(); }
}