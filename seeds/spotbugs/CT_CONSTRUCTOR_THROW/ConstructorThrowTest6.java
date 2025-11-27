import constructorthrow.*;
public class ConstructorThrowTest6 {
    public ConstructorThrowTest6() { throw new RuntimeException(); }
    final void finalize(Object object) { System.out.println("I am not overriding the void#finalize() method"); }
}