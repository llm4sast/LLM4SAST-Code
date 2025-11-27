import com.google.errorprone.bugpatterns.*;
import java.util.Comparator;
import com.google.common.collect.Ordering;
public class OrderingFromNegativeCases {
  public static void negativeCase1() {
    Comparator<String> comparator = new Comparator<String>() {
      @Override
      public int compare(String first, String second) {
        int compare = first.length() - second.length();
        return (compare != 0) ? compare : first.compareTo(second);
      }
    };
    Ordering<String> ord = Ordering.from(comparator);
  }
}