import sfBugs.*;
public class Bug2930744 {
    boolean f(Object x, int y) {
        if (x != null || (x == null && y > 0))
            return true;
        return false;
    }
    boolean f2(Object x, int y) {
        if (x != null || (x == null && y > 0))
            return true;
        return false;
    }
}