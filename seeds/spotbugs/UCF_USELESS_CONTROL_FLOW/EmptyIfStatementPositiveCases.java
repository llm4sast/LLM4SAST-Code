import com.google.errorprone.bugpatterns.*;
public class EmptyIfStatementPositiveCases {
  public static void positiveCase1() {
    int i = 10;
    if (i == 10); { i++; }
  }
  public static void positiveCase2() {
    int i = 10;
    if (i == 10);
    i++;
    System.out.println("foo");
  }
  public static void positiveCase3() {
    int i = 10;
    if (i == 10)
      ;
    i++;
    System.out.println("foo");
  }
  public static void positiveCase4() {
    int i = 10;
    if (i == 10)            ;
  }
  public static void positiveCase5() {
    int i = 10;
    if (i == 10)
      ; { System.out.println("foo"); }
  }
}