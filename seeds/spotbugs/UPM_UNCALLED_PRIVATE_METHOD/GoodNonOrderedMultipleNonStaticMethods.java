import findhiddenmethodtest.*;
class SuperGoodNonOrderedMultipleStaticMethods {
    public static void display3() { System.out.println("display3 (SuperBadInEqualMultipleMethod)"); }
    void display(String s) { System.out.println("first method (Super) " + s); }
    private void display2(String s) { System.out.println("Second method (Super) " + s); }
}
class GoodNonOrderedMultipleNonStaticMethods extends SuperGoodNonOrderedMultipleStaticMethods {
    void display(String s) { System.out.println("first method (sub) " + s); }
    void display2(String s) { System.out.println("Second method (sub) " + s); }
}