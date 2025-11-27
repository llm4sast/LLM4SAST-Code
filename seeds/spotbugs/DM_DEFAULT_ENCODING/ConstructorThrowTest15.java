import constructorthrow.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class ConstructorThrowTest15 {
    public ConstructorThrowTest15(File f) {
        try (FileWriter fw = new FileWriter(f)) {
            fw.write("string");
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}