import sfBugs.*;
import java.lang.reflect.Method;
public class Bug1912994 {
    public String foo() throws Exception {
        String s = null;
        try {
            Method m = this.getClass().getMethod("foo");
            m.invoke(this);
            char[] t = new char[0];
            t[1] = 'a';
            s = "foo";
            s += "bar";
            return s;
        } catch (Exception e) { throw new Exception(e); }
    }
    public String foo2() throws Exception {
        String s = null;
        try {
            Method m = this.getClass().getMethod("foo2");
            m.invoke(this);
            char[] t = new char[0];
            t[1] = 'a';
            s = "foo";
            s += "bar";
            return s;
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) { throw new Exception(e); }
    }
}