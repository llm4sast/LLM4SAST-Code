import constructorthrow.*;
public class ConstructorThrowNegativeTest3 {
    public ConstructorThrowNegativeTest3() {
        try {
            testMethod();
        } catch (RuntimeException e) { System.err.println("Exception caught, no error"); }
    }
    public void testMethod() { throw new RuntimeException(); }
}