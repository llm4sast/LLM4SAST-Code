import java.io.IOException;
import java.io.InputStream;
public class Finalizer {
    public static void main(String[] args) { }
    class Parent {
        InputStream f;
        @Override
        protected void finalize() {
            try {
                f.close();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
    class Child extends Parent {
        @Override
        protected void finalize() { System.out.println("nooooooooo"); }
    }
    class Deviant extends Parent {
        @Override
        protected void finalize() { }
    }
}