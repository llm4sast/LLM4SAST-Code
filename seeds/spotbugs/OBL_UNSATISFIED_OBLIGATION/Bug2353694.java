import sfBugs.*;
import java.io.FileInputStream;
import java.io.IOException;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class Bug2353694 {
    @ExpectWarning(value="OS", num=2)
    public void fp_warning_OS() throws IOException {
        FileInputStream in2 = new FileInputStream("test");
        in2.read();
        FileInputStream in = new FileInputStream("test");
        try {
            in.read();
        } finally { in.close(); }
        FileInputStream in3 = new FileInputStream("test");
        in3.read();
    }
    @ExpectWarning(value="OS", num=1)
    public void correct_warning_OS() throws IOException {
        FileInputStream in = new FileInputStream("test");
        try {
            in.read();
        } finally { in.close(); }
        FileInputStream in3 = new FileInputStream("test");
        in3.read();
    }
}