public class DynamicLoad {
  public void foo() throws Exception {
    String className = System.getProperty("messageClassName");
    java.lang.Class clazz = java.lang.Class.forName(className); // Noncompliant
    System.out.println(clazz);
  }
}
