import ghIssues.issue543.*;
import java.io.File;
import org.example.Generated;
@SuppressWarnings("all")
public class GeneratedOnClassMembers {
  @Generated
  public int a = 1;
  @Generated
  public String toString() { return null; }
  public boolean test() {
    File file = new File("/home/test/file");
    return file.canExecute();
  }
  @Generated
  public boolean test(String s) { return s == "test"; }
}