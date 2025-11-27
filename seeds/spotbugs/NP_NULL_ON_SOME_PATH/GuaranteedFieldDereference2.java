import npe.*;
public class GuaranteedFieldDereference2 {
    Object x;
    public GuaranteedFieldDereference2(Object x) { this.x = x; }
    public int report(int b) {
        int result;
        if (x != null)
            x = new Object();
        if (b > 0)
            result = b;
        else
            result = -b;
        result += x.hashCode();
        return result;
    }
}