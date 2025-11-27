import npe.*;
import java.util.Set;
class NullDeref6 {
    Object foo(Object o) {
        if (o == null)
            return o;
        if (o instanceof String)
            return o;
        if (o != null && o instanceof Set)
            return ((Set) o).iterator();
        return o.getClass();
    }
    Object bar(Object o) {
        if (o != null)
            return o;
        if (o == null)
            System.out.println("Got null");
        System.out.println(o.hashCode());
        if (o == null)
            return o;
        return o.getClass();
    }
}