import com.google.errorprone.bugpatterns.*;
import com.google.common.base.Objects;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class IncompatibleEqualsPositiveCases {
    @ExpectWarning(value="EC", num=7)
    public boolean testEquality(String s, Integer i, Double d, Object a[]) {
        if (i.equals(17L))
            return true;
        if (s.equals(a))
            return true;
        if (a.equals(s))
            return true;
        if (i.equals((byte)17))
            return true;
        if (s.equals(i))
            return true;
        if (i.equals(d))
            return true;
        if (d.equals(a))
            return true;
        return false;
    }
    @ExpectWarning(value="EC", num=7)
    public boolean testObjectsEquals(String s, Integer i, Double d, Object a[]) {
        if (java.util.Objects.equals(i, 17L))
            return true;
        if (java.util.Objects.equals(s, a))
            return true;
        if (java.util.Objects.equals(a, s))
            return true;
        if (java.util.Objects.equals(i, (byte)17))
            return true;
        if (java.util.Objects.equals(s, i))
            return true;
        if (java.util.Objects.equals(i, d))
            return true;
        if (java.util.Objects.equals(d, a))
            return true;
        return false;
    }
    @ExpectWarning(value="EC", num=7)
    public boolean testGuavaEquals(String s, Integer i, Double d, Object a[]) {
        if (Objects.equal(i, 17L))
            return true;
        if (Objects.equal(s, a))
            return true;
        if (Objects.equal(a, s))
            return true;
        if (Objects.equal(i, (byte)17))
            return true;
        if (Objects.equal(s, i))
            return true;
        if (Objects.equal(i, d))
            return true;
        if (Objects.equal(d, a))
            return true;
        return false;
    }
}