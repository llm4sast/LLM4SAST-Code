import constructorthrow.*;
public class ConstructorThrowNegativeTest4 {
    public ConstructorThrowNegativeTest4() { testMethodWrapperNoException(); }
    public void testMethodWrapperNoException() {
        try {
            testMethod();
        } catch (RuntimeException e) { System.err.println("Exception caught, no error"); }
    }
    public void testMethod() { throw new RuntimeException(); }
}