import constructorthrow.*;
import java.io.IOException;
public class ConstructorThrowTest22 {
    public ConstructorThrowTest22() throws IOException { ConstructorThrowTest22.test(); }
    private static String test() throws IOException { throw new IOException(); }
}