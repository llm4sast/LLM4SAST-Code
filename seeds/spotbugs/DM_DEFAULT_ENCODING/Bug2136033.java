import sfBugs.*;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.jupiter.api.Test;
public class Bug2136033 {
    @Test
    public void testCloseStream() throws Exception {
        FileOutputStream fos = new FileOutputStream(File.createTempFile("prefix", "temp"));
        try {
            fos.write("abc".getBytes());
        } finally { safeCloseStream(fos); }
    }
    private void safeCloseStream(OutputStream object) {
        try {
            if (object != null && object != System.out && object != System.err) { object.close(); }
        } catch (IOException e) { System.out.println("failed to close stream: " + e.getMessage()); }
    }
    @Test
    public void testCloseCloseableFalsePositive() throws Exception {
        FileOutputStream fos = new FileOutputStream(File.createTempFile("prefix", "temp"));
        try {
            fos.write("abc".getBytes());
        } finally { safeCloseCloseable(fos); }
    }
    private void safeCloseCloseable(Closeable object) {
        try {
            if (object != null && object != System.out && object != System.err) { object.close(); }
        } catch (IOException e) { System.out.println("failed to close stream: " + e.getMessage()); }
    }
}