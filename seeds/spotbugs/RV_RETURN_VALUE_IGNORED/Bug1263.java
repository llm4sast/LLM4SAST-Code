import sfBugsNew.*;
public class Bug1263 {
    public void crash(String msg) {
        new java.math.BigDecimal(Double.POSITIVE_INFINITY);
        if ("Infinite or NaN".equals(msg)) {}
    }
}