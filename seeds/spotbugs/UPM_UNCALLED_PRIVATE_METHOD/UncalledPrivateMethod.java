import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class UncalledPrivateMethod {
    private static final Object myObject = new Object();
    @ExpectWarning("UrF")
    String s;
    private void foo(String s) { this.s = s; }
    private void debug(String s) { System.out.println(s); }
    @ExpectWarning("UPM")
    private void foobar(int i) { }
    private void foobar(double d) { }
    public void f(double d) { foobar(d); }
}