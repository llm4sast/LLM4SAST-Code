import sfBugsNew.*;
import edu.umd.cs.findbugs.annotations.Confidence;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug1215 {
    static class A {
        A getA() { return this; }
    }
    static class AA extends A { }
    @ExpectWarning(value = "BC_UNCONFIRMED_CAST_OF_RETURN_VALUE", confidence = Confidence.LOW)
    AA test(A a) {
        if (a.getA() instanceof AA) { return (AA) a.getA(); }
        return null;
    }
    @NoWarning(value = "BC_UNCONFIRMED_CAST_OF_RETURN_VALUE")
    AA testOK(A a) {
        A a2 = a.getA();
        if (a2 instanceof AA) { return (AA) a2; }
        return null;
    }
    public static void main(String[] args) { System.out.println(new AA().getA()); }
}