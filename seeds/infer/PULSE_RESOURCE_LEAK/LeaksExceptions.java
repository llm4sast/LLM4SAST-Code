import codetoanalyze.java.checkers.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
class LeaksExceptions {
  void tryWithResourcesOk() throws IOException, FileNotFoundException { try (FileInputStream stream = new FileInputStream("file.txt")) { } }
  void closeInFinallyOk() throws IOException, FileNotFoundException {
    FileInputStream stream = null;
    try {
      stream = new FileInputStream("file.txt");
    } finally { if (stream != null) { stream.close(); } }
  }
  void twoResourcesBad() throws IOException, FileNotFoundException {
    FileInputStream stream1 = null;
    FileInputStream stream2 = null;
    try {
      stream1 = new FileInputStream("file1.txt");
      stream2 = new FileInputStream("file2.txt");
    } finally {
      if (stream1 != null) { stream1.close(); }
      if (stream2 != null) { stream2.close(); }
    }
  }
  void leakInCatchBad() throws IOException, FileNotFoundException {
    FileInputStream stream = null;
    try {
      stream = new FileInputStream("file_in.txt");
    } catch (Exception e) {
      FileOutputStream fis = new FileOutputStream("file_out.txt");
    } finally { if (stream != null) { stream.close(); } }
  }
}