import npe.*;
public class InstanceOfInteraction {
    int f(Object x, boolean b) {
        if (x == null)
            System.out.println("x is null");
        if (b)
            System.out.println("b is true");
        if (x instanceof String)
            return 0;
        return x.hashCode();
    }
}