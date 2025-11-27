import tomcat.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
public class ExpandWar {
    protected static void expand(InputStream input, File docBase, String name) throws IOException {
        File file = new File(docBase, name);
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
        byte buffer[] = new byte[2048];
        while (true) {
            int n = input.read(buffer);
            if (n <= 0)
                break;
            output.write(buffer, 0, n);
        }
        output.close();
    }
}