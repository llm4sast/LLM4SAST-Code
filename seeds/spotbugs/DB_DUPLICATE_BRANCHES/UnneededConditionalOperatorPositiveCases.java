import com.google.errorprone.bugpatterns.*;
public class UnneededConditionalOperatorPositiveCases {
  public static void positiveCaseTrueFalseSimple() { boolean t = isFoo() ? true : false; }
  public static void positiveCaseTrueFalseBinary() { boolean t = (4 > 5) ? true : false; }
  public static void positiveCaseFalseTrueSimple() { boolean t = isFoo() ? false : true; }
  public static void positiveCaseFalseTrueUnary() { boolean t = !isFoo() ? false : true; }
  public static void positiveCaseFalseTrueBinary() { boolean t = (4 > 5) ? false : true; }
  public static void positiveCaseFalseTrueBinary2() { boolean t = (4 == 5) ? false : true; }
  public static void positiveCaseFalseTrueBinaryUnparenthesised() { boolean t = 4 <= 5 ? false : true; }
  public static void positiveCaseFalseTrueBinaryUnparenthesised2() { boolean t = 4 != 5 ? false : true; }
  public static void positiveCaseFalseTrueDeMorgan() { boolean t = (4 != 5 || 2 == 3) ? false : true; }
  public static void positiveCaseTrueTrue() { boolean t = isFoo() ? true : true; }
  public static void positiveCaseFalseFalse() { boolean t = isFoo() ? false : false; }
  private static boolean isFoo() { return true; }
}