import sfBugs.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
import junit.framework.Assert;
public class Bug3474679 {
    @ExpectWarning(value = "EC_UNRELATED_TYPES")
    @Test
    public void testEquals1() { Assert.assertEquals(new Double(0), new BigDecimal("0")); }
    @ExpectWarning(value = "EC_UNRELATED_TYPES")
    @Test
    public void testEquals2() { Assert.assertEquals(new Double(0), new CustomObject()); }
    @NoWarning(value = "EC_UNRELATED_TYPES")
    @Test
    public void testEquals2a() { Assert.assertFalse(new Double(0).equals(new CustomObject())); }
    @ExpectWarning(value = "EC_UNRELATED_TYPES")
    @Test
    public void testEquals3() {
        if (new Double(0).equals(new BigDecimal("0"))) 
            System.out.println("huh");
    }
    @ExpectWarning(value = "EC_UNRELATED_TYPES")
    @Test
    public void testEquals4() {
       if (new Double(0).equals(new CustomObject())) 
            System.out.println("huh");;
    }
    @ExpectWarning(value = "EC_UNRELATED_TYPES", num=2)
    @Test
    public void testEqualsFalseNegative() {
       Assert.assertEquals(new Double(0), new CustomObject()); 
       if (new Double(0).equals(new CustomObject())) 
            ;
    }
    @ExpectWarning(value = "EC_UNRELATED_TYPES", num=4)
    public void testEquals5() {
        Assert.assertEquals(new Double(0), new BigDecimal("0")); 
        Assert.assertEquals(new Double(0), new CustomObject()); 
        if (new Double(0).equals(new BigDecimal("0"))) 
            ;
        if (new Double(0).equals(new CustomObject())) 
            ;
    }
    private static class CustomObject { }
}