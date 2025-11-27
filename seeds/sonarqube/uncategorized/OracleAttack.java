import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class ExampleController {
  private static String targetDirectory = "/path/to/target/directory/";

  @GetMapping(value = "/exists")
  public void exists(@RequestParam("filename") String filename) throws IOException {
    File file = new File(targetDirectory + filename);
    if (!file.exists()) { // Noncompliant
      throw new IOException("File does not exist in the target directory");
    }
  }
}
