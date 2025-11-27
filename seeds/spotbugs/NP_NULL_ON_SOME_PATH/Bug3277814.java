import sfBugs.*;
public class Bug3277814 {
    public void test() {
        String var = "";
        int index = 2;
        if (index == -1) {
            var = String.class.getName();
            if (var.length() == 0) { var = null; }
        } else {
            var = Integer.class.getName();
            if (var.length() == 0) { var = null; }
        }
        if (var == null) { throw new RuntimeException("NULL"); }
    }
    public void test2() {
        String var = "";
        int index = 2;
        if (index == -1) {
            var = String.class.getName();
            if (var.length() == 0) { var = null; }
        } else {
            var = Integer.class.getName();
            if (var.length() == 0) { var = null; }
        }
        if (var == null) { throw new NullPointerException("var is null"); }
    }
}