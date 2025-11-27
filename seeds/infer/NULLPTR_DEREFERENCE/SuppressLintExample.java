import codetoanalyze.java.infer.*;
import com.facebook.infer.annotation.SuppressLint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
class SuppressAllWarnigsInTheClass {
  void shouldNotReportNPE() {
    Object object = null;
    object.toString();
  }
  void shouldNotReportResourceLeak() {
    try { FileInputStream fis = new FileInputStream(new File("whatever.txt")); } catch (IOException e) { }
  }
}
class SuppressLintExample {
  @SuppressLint("null-dereference")
  SuppressLintExample() {
    Object object = null;
    object.toString();
  }
  void shouldReportNPE() {
    Object object = null;
    object.toString();
  }
  @SuppressLint("null-dereference")
  void shouldNotReportNPE() {
    Object object = null;
    object.toString();
  }
}