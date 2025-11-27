import npe.*;
public class GuaranteedFieldDereference3 {
    public int report() {
        if (x != null)
            x = new Object();
        return x.hashCode();
    }
    public int doNotReport() {
        if (x == null)
            x = new Object();
        return x.hashCode();
    }
    public GuaranteedFieldDereference3(Object x) { this.x = x; }
    Object x;
}