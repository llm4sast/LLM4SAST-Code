import com.google.errorprone.bugpatterns.*;
public class SuppressWarningsDeprecatedPositiveCases {
  @SuppressWarnings("deprecated")
  public static void positiveCase1() { }
  @SuppressWarnings({"deprecated"})
  public static void positiveCase2() { }
  @SuppressWarnings({"deprecated", "foobarbaz"})
  public static void positiveCase3() { }
  public static void positiveCase4() {
    @SuppressWarnings({"deprecated", "foobarbaz"})
    int a = 3;
  }
  public static void positiveCase5() {
    @SuppressWarnings("deprecated")
    int a = 3;
  }
  public static void positiveCase6() {
    @SuppressWarnings("deprecated")
    class Foo { };
  }
  public static void positiveCase7() {
    @SuppressWarnings({"deprecated", "foobarbaz"})
    class Foo { };
  }
  @SuppressWarnings(value = {"deprecated"})
  public static void positiveCase8() { }
  @SuppressWarnings(value = "deprecated")
  public static void positiveCase9() { }
}