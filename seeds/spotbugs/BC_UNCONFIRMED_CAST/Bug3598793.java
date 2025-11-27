import sfBugs.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import edu.umd.cs.findbugs.annotations.Confidence;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug3598793 implements ServletResponse {
    @NoWarning("BC_UNCONFIRMED_CAST")
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletResponse response;
        try {
            response = (HttpServletResponse) res;
        } catch (ClassCastException e) { throw new ServletException("non-HTTP request or response", e); }
        response.sendError(404);
    }
    @ExpectWarning(value="BC_UNCONFIRMED_CAST", confidence=Confidence.LOW)
    public void service2(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletResponse response;
        response = (HttpServletResponse) res;
        response.sendError(404);
    }
  @ExpectWarning("BC_UNCONFIRMED_CAST")
  public  Integer foo(Number x) { return (Integer) x; }
  @NoWarning("BC_UNCONFIRMED_CAST")
  public  Integer foo(Object x) { return (Integer) x; }
    @Override
    public void flushBuffer() throws IOException { }
    @Override
    public int getBufferSize() { return 0; }
    @Override
    public String getCharacterEncoding() { return null; }
    @Override
    public String getContentType() { return null; }
    @Override
    public Locale getLocale() { return null; }
    @Override
    public ServletOutputStream getOutputStream() throws IOException { return null; }
    @Override
    public PrintWriter getWriter() throws IOException { return null; }
    @Override
    public boolean isCommitted() { return false; }
    @Override
    public void reset() { }
    @Override
    public void resetBuffer() { }
    @Override
    public void setBufferSize(int arg0) { }
    @Override
    public void setCharacterEncoding(String arg0) { }
    @Override
    public void setContentLength(int arg0) { }
    @Override
    public void setContentLengthLong(long arg0) { }
    @Override
    public void setContentType(String arg0) { }
    @Override
    public void setLocale(Locale arg0) { }
}