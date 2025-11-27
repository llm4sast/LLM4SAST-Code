import constructorthrow.*;
import java.io.File;
import java.io.FileNotFoundException;
public class ConstructorThrowTest12 {
    public ConstructorThrowTest12(File f) {
        try {
            if (!f.exists()) {
                throw new FileNotFoundException();
            } else { throw new RuntimeException(); }
        } catch (FileNotFoundException e) { System.err.println("IOException caught"); }
    }
}