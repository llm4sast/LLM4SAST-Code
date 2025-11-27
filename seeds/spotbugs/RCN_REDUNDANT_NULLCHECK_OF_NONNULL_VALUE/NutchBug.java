import npe.*;
public class NutchBug {
    Object x;
    static int test(NutchBug b, int y) {
        if (b.x == null)
            b.x = new Object();
        if (b.x.hashCode() == 42) { }
        if (b.x == null) { return 1; }
        return 2;
    }
}