import sfBugs.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug1356076 {
    PrintWriter[] mWriters = new PrintWriter[1];
    PrintWriter mWriter = null;
    @NoWarning("OS")
    public void writeString1(final String s) throws IOException {
        if (mWriters[0] == null) {
            final File ff = File.createTempFile("sfq", ".lst");
            ff.deleteOnExit();
            mWriter = new PrintWriter(new BufferedWriter(new FileWriter(ff)));
        }
        mWriter.println(s);
    }
    @NoWarning("OS")
    public void writeString2(final String s) throws IOException {
        if (mWriters[0] == null) {
            final File ff = File.createTempFile("sfq", ".lst");
            ff.deleteOnExit();
            mWriters[0] = new PrintWriter(new BufferedWriter(new FileWriter(ff)));
        }
        mWriters[0].println(s);
    }
}