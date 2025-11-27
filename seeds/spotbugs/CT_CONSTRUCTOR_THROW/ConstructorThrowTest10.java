import constructorthrow.*;
public class ConstructorThrowTest10 {
    public ConstructorThrowTest10() { wrapperMethod(); }
    public void wrapperMethod() { testMethod(); }
    public void testMethod() { throw new RuntimeException(); }
}