import com.google.errorprone.bugpatterns.*;
public class EmptyStatementNegativeCases {
  public static void negativeCase1() {
    int i = 10;
    if (i == 10) { System.out.println("foo"); }
    i++;
  }
  public static void negativeCase2() {
    int i = 0;
    for (;;) {
      if (i > 10) { break; }
      i++;
    }
  }
}