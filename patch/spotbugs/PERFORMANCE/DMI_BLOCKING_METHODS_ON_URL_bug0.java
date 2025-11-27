import java.net.URL;
public class DMI_BLOCKING_METHODS_ON_URL_bug0 {
    static int f(URL u) {
        return u.hashCode();
    }
    static boolean g(URL u1, URL u2) {
        return u1.equals(u2);
    }
}
