import constructorthrow.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class ConstructorThrowNegativeTest10 {
    public ConstructorThrowNegativeTest10(File f) {
        try (FileWriter fw = new FileWriter(f)) {
            fw.write("string");
        } catch (IOException e) { System.err.println("Exception caught, no error"); }
    }
}