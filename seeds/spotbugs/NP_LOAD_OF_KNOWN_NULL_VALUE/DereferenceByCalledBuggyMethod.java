import deref.*;
import java.io.IOException;
import java.io.OutputStream;
public class DereferenceByCalledBuggyMethod {
    public void closeit(OutputStream out) throws IOException {
        if (out == null)
            out.close();
    }
    public void falsePositive() throws IOException { closeit(null); }
}