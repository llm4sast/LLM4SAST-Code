import java.io.IOException;
import java.io.OutputStream;
class IgnoredException {
    void foo(OutputStream o) {
        try {
            System.out.println("Foo");
            o.close();
        } catch (IOException e) { }
        try {
            System.out.println("Foo");
            o.close();
        } catch (IOException e) { }
        try {
            System.out.println("Foo");
            o.close();
        } catch (Throwable e) { }
    }
}