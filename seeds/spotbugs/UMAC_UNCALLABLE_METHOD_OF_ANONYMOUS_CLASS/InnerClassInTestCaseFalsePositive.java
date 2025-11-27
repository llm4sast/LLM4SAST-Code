import junit.framework.TestCase;
public class InnerClassInTestCaseFalsePositive extends TestCase {
    public void testFoo() {
        doSomething(new Object() {
            public void overrideSomeMethod() { }
        });
    }
    private void doSomething(Object object) { }
}