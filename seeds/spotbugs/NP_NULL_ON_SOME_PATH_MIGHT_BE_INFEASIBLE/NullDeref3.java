import npe.*;
class NullDeref3 {
    static boolean same1(int a[], int b[]) {
        if (a == null)
            if (b == null || b.length == 0)
                return true;
        if (b == null)
            if (a == null || a.length == 0)
                return true;
        if (a.length != b.length)
            return false;
        return true;
    }
    static boolean same2(Object a, Object b) {
        if ((null == a && null == b) || a.equals(b))
            return true;
        else
            return false;
    }
    static boolean same3(Object a, Object b) {
        if ((a == null && b == null) || a.equals(b))
            return true;
        else
            return false;
    }
    static boolean same4(Object a, Object b) {
        if ((a == null && b == null) || b.equals(a))
            return true;
        else
            return false;
    }
}