import deref.*;
public class UnconditionalDeref {
    private void f(Object obj) { System.out.println(obj.hashCode()); }
    static void g(Object obj) { System.out.println(obj.hashCode()); }
    void h(Object obj) { System.out.println(obj.hashCode()); }
    void report() { f(null); }
    private void report2() { g(null); }
    private void report3() { h(null); }
    Object returnsNull() { return null; }
    @Override
    public int hashCode() { return 0; }
    @Override
    public boolean equals(Object o) { return o.hashCode() == this.hashCode() && o == this; }
    void report4() {
        Object o = returnsNull();
        System.out.println(o.hashCode());
    }
}