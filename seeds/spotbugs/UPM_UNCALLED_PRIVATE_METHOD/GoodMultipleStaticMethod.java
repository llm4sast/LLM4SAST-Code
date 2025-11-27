import findhiddenmethodtest.*;
class SuperGoodMultipleStaticMethod {
    private static void display(String s) { System.out.println("first method (Super)" + s); }
    private static void display2(String s) { System.out.println("Second method (Super)" +s); }
}
class GoodMultipleStaticMethod extends SuperGoodMultipleStaticMethod {
    private static void display(String s) { System.out.println("first method (sub) " + s); }
    private static void display2(String s) { System.out.println("Second method (sub) " + s); }
}