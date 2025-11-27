import constructorthrow.*;
import java.io.IOException;
public class ConstructorThrowTest11 {
    public ConstructorThrowTest11() {
        try {
            throw new IOException();
        } catch (IOException e) {
            System.err.println("IOException caught");
            throw new RuntimeException(e);
        }
    }
}