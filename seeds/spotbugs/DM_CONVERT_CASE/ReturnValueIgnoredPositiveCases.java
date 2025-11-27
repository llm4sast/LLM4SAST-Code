import com.google.errorprone.bugpatterns.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;
public class ReturnValueIgnoredPositiveCases {
  String a = "thing"; {
    String.format("%d", 10);
    String.format("%d", 10).trim();
    java.lang.String.format("%d", 10).trim();
    a.intern();
    a.trim();
    a.trim().concat("b");
    a.concat("append this");
    a.replace('t', 'b');
    a.replace("thi", "fli");
    a.replaceAll("i", "b");
    a.replaceFirst("a", "b");
    a.toLowerCase();
    a.toLowerCase(Locale.ENGLISH);
    a.toUpperCase();
    a.toUpperCase(Locale.ENGLISH);
    a.substring(0);
    a.substring(0, 1);
  }
  StringBuffer sb = new StringBuffer("hello"); { sb.toString().trim(); }
  BigInteger b = new BigInteger("123456789"); {
    b.add(new BigInteger("3"));
    b.abs();
    b.shiftLeft(3);
    b.subtract(BigInteger.TEN);
  }
  BigDecimal c = new BigDecimal("1234.5678"); {
    c.add(new BigDecimal("1.3"));
    c.abs();
    c.divide(new BigDecimal("4.5"));
    new BigDecimal("10").add(c);
  }
}