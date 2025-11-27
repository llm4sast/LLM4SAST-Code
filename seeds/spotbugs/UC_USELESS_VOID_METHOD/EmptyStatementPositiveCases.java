import com.google.errorprone.bugpatterns.*;
public class EmptyStatementPositiveCases {
  public static void positiveCase1() {
    int i = 10;
    ;
    i++;
  }
  public static void positiveCase2() {
    int i = 10;
    if (i == 10);
      i++;
  }
}