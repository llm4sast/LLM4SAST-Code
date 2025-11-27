import com.google.errorprone.bugpatterns.*;
public class BadShiftAmountPositiveCases {
    public long testEquality(int x) {
        long result = 0;
        result += x >> 32;
        result += x << 32;
        result += x >>> 32;
          return result;
      }
}