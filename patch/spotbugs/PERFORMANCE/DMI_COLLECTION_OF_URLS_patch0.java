import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
public class DMI_COLLECTION_OF_URLS_patch0 {
    static Set<URI> foo() {
        return new HashSet<URI>();
    }
    static Map<URI, String> foo2() {
        return new HashMap<URI, String>();
    }
    static Map<String, URI> falsePositive() {
        return new HashMap<String, URI>();
    }
    public static Map<URI, String> map;
    public static Map<String, URI> falsePositiveMap;
    public static Set<URI> set;
    static boolean contains(Set<?> m, URI u) {
        return m.contains(u);
    }
    static Object get(Map<?, ?> m, URI u) {
        return m.get(u);
    }
}
