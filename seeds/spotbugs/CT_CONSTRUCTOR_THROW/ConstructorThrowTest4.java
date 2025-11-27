import constructorthrow.*;
public class ConstructorThrowTest4 {
    public static void main(String[] args) {
        ConstructorThrowTest4 t = new ConstructorThrowTest4();
        t.testMethod();
        System.out.println("IO");
    }
    public void testMethod() { throw new RuntimeException(); }
    public ConstructorThrowTest4() { testMethod(); }
}