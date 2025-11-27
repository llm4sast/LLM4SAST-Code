import java.net.URI;
public class DMI_BLOCKING_METHODS_ON_URL_patch0 {
    static int f(URI u) {
        return u.hashCode();
    }
    static boolean g(URI u1, URI u2) {
        return u1.equals(u2);
    }
}
