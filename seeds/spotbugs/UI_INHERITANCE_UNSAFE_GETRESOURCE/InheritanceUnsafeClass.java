import unsafeInheritance.*;
import java.io.InputStream;
import java.net.URL;
public class InheritanceUnsafeClass {
    public InputStream getResourceOK(String r) { return InheritanceUnsafeClass.class.getResourceAsStream(r); }
    public URL getResource(String r) { return getClass().getResource(r); }
    public InputStream getResourceBAD(String r) { return getClass().getResourceAsStream(r); }
}