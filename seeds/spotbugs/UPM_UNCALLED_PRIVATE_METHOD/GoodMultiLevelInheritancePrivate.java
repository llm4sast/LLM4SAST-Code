import findhiddenmethodtest.*;
class GoodGrandClassPrivate {
    private static void display(String s) { System.out.println("Grand Information: " + s); }
}
class GoodParentClassPrivate extends GoodGrandClassPrivate { }
class GoodMultiLevelInheritancePrivate extends GoodParentClassPrivate {
    private static void display(String s) { System.out.println("Child information" + s); }
}