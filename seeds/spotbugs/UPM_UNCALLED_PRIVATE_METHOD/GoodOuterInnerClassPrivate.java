import findhiddenmethodtest.*;
public class GoodOuterInnerClassPrivate {
    private static void method1(){ System.out.println("method1 (GoodOuterInnerClassPrivate)"); }
    public static class GoodInnerClassPrivate extends GoodOuterInnerClassPrivate {
        private static void method1(){ System.out.println("method1 (GoodInnerClassPrivate)"); }
    }
}