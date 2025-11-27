import npe.*;
public class LoadKnownNull {
    static void simple() {
        Object o = null;
        System.out.println(o);
    }
    static void lenbok(String s) {
        try {
            if (s == null)
                System.out.println("s is null");
        } finally { System.out.println(s); }
    }
    static void dmoellen() {
        Object foo = null;
        try {
            foo = new Object();
        } finally { System.out.println(foo); }
    }
}