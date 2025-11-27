import constructorthrow.*;
public class ConstructorThrowTest5 {
    private int i = 0;
    public ConstructorThrowTest5() { this(5); }
    public ConstructorThrowTest5(int input) {
        i = input;
        testMethod();
    }
    public void testMethod() {
        System.out.println(i);
        throw new RuntimeException();
    }
}