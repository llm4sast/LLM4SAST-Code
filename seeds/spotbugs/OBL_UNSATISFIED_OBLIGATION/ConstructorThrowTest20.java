import constructorthrow.*;
import java.io.FileWriter;
import java.io.IOException;
public class ConstructorThrowTest20 {
    public ConstructorThrowTest20() throws IOException {
        if (!verify()) {
            FileWriter fw = new FileWriter("");
            fw.write("string");
        }
    }
    private boolean verify() { return false; }
}