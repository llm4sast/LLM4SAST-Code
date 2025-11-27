import constructorthrow.*;
import java.io.IOException;
public class ConstructorThrowNegativeTest5 {
    public ConstructorThrowNegativeTest5() {
        try {
            testMethod();
        } catch (IOException e) { System.err.println("IOException caught, no error"); }
    }
    public void testMethod() throws IOException { throw new IOException(); }
}