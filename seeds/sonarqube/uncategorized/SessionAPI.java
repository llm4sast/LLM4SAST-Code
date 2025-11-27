import javax.servlet.http.HttpServletRequest;

public class SessionAPI {
  public void foo(HttpServletRequest request) {
    if (isActiveSession(request.getRequestedSessionId())) { // Noncompliant
    }
  }
}
