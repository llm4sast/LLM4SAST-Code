import sfBugs.*;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
public class Bug2003267 {
    private String type = "";
    public void correct() {
        Object param = new Object();
        Works target = null;
        if (type.equals("a")) {
            target = new Works();
        } else if (type.equals("b")) { target = new Works(); }
        target.bar(param); 
    }
    public void wrong() {
        Object param = new Object();
        DoesntWork target = null;
        if (type.equals("a")) {
            target = new DoesntWork();
        } else if (type.equals("b")) { target = new DoesntWork(); }
        target.foo(param); 
    }
    static class Works {
        public void bar(@Nullable Object o) { }
    }
    static class DoesntWork {
        public void foo(@NonNull Object o) { }
    }
}