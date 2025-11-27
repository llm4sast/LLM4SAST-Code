import constructorthrow.*;
import java.io.IOException;
public class ConstructorThrowTest18 {
    public ConstructorThrowTest18() throws IOException {
        if (!verify()) { throw new IOException(); }
    }
    private boolean verify() { return false; }
}