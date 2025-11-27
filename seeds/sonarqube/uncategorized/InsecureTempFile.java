import java.io.File;
import java.io.IOException;

class InsecureTempFile {
  protected void Example() throws IOException {
    File tempDir;
    tempDir = File.createTempFile("", ".");
    tempDir.delete();
    tempDir.mkdir(); // Noncompliant
  }
}
