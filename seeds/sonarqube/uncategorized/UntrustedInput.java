import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UntrustedInput {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    Runtime r = Runtime.getRuntime();
    String userInput = request.getParameter("example");

    if (userInput != null) {
      String[] envs = {userInput};
      r.exec("/path/to/example", envs);
    } else {
      r.exec("/path/to/example");
    }
  }
}
