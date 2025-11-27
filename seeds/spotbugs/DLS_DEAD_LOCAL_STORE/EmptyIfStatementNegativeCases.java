import com.google.errorprone.bugpatterns.*;
public class EmptyIfStatementNegativeCases {
  public static void negativeCase1() {
    int i = 10;
    if (i == 10) { System.out.println("foo"); }
    i++;
  }
  public static void negativeCase2() {
    int i = 0;
    if (i == 10)
      ;
    else
      System.out.println("not 10");
  }
  public static void negativeCase3() {
    int i = 0;
    if (i == 10)
      ;
    else if (i == 11)
      ;
    else if (i == 12)
      ;
    else
      System.out.println("not 10, 11, or 12");
  }
}