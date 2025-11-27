import com.google.errorprone.bugpatterns.*;
public class ArrayEqualsPositiveCases {
  public void intArray() {
    int[] a = {1, 2, 3};
    int[] b = {1, 2, 3};
    if (a.equals(b)) {
      System.out.println("arrays are equal!");
    } else { System.out.println("arrays are not equal!"); }
  }
  public void objectArray() {
    Object[] a = new Object[3];
    Object[] b = new Object[3];
    if (a.equals(b)) {
      System.out.println("arrays are equal!");
    } else { System.out.println("arrays are not equal!"); }
  }
  public void firstMethodCall() {
    String s = "hello";
    char[] b = new char[3];
    if (s.toCharArray().equals(b)) {
      System.out.println("arrays are equal!");
    } else { System.out.println("arrays are not equal!"); }
  }
  public void secondMethodCall() {
    char[] a = new char[3];
    String s = "hello";
    if (a.equals(s.toCharArray())) {
      System.out.println("arrays are equal!");
    } else { System.out.println("arrays are not equal!"); }
  }
  public void bothMethodCalls() {
    String s1 = "hello";
    String s2 = "world";
    if (s1.toCharArray().equals(s2.toCharArray())) {
      System.out.println("arrays are equal!");
    } else { System.out.println("arrays are not equal!"); }
  }
}