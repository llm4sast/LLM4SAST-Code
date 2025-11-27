import com.google.errorprone.bugpatterns.*;
public class UnneededConditionalOperatorNegativeCases {
  public static void negativeCase1() { boolean t = (4 > 5) ? true : isFoo(); }
  public static void negativeCase2() { boolean t = (4 > 5) ? isFoo() : true; }
  public static void negativeCase3() { String t = isFoo() ? "true" : "false"; }
  private static boolean isFoo() { return true; }
}