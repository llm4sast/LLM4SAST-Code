import java.util.HashSet;
import java.util.Set;
public class Ideas_2011_07_31 {
    Set<Integer> s = new HashSet<Integer>();
    public Set<Integer> getIntegerSetPublic() { return s; }
    private Set<Integer> getIntegerSetPrivate() { return s; }
    public boolean test1a(Set<String> s) { return getIntegerSetPublic().contains("x"); }
    public boolean test2a(Set<String> s) { return getIntegerSetPrivate().contains("x"); }
    public void test1(Set<String> s) {
        if (getIntegerSetPublic().contains("x"))
            s.removeAll(getIntegerSetPublic());
        if (s.equals(getIntegerSetPublic()))
            System.out.println("Equal");
    }
    public void test2(Set<String> s) {
        if (getIntegerSetPrivate().contains("x"))
            s.removeAll(getIntegerSetPrivate());
        if (s.equals(getIntegerSetPrivate()))
            System.out.println("Equal");
    }
    public void test1OK(Set<Integer> s) {
        if (getIntegerSetPublic().contains(1))
            s.removeAll(getIntegerSetPublic());
        if (s.equals(getIntegerSetPublic()))
            System.out.println("Equal");
    }
    public void test2OK(Set<Integer> s) {
        if (getIntegerSetPrivate().contains(1))
            s.removeAll(getIntegerSetPrivate());
        if (s.equals(getIntegerSetPrivate()))
            System.out.println("Equal");
    }
    class Foo {
        public void test2(Set<String> s) {
            boolean b = getIntegerSetPrivate().contains("x");
            if (b)
                s.removeAll(getIntegerSetPrivate());
            if (s.equals(getIntegerSetPrivate()))
                System.out.println("Equal");
        }
        public void test2OK(Set<Integer> s) {
            if (getIntegerSetPrivate().contains(1))
                s.removeAll(getIntegerSetPrivate());
            if (s.equals(getIntegerSetPrivate()))
                System.out.println("Equal");
        }
    }
}