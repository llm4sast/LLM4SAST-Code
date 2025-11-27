public class IncompatMaskTest {
    public static void foo(int i) {
        if ((i & 16) == 2)
            System.out.println("warn");
        if ((i & 16) != 2)
            System.out.println("warn");
        if ((i | 16) == 2)
            System.out.println("warn");
        if ((i | 16) != 2)
            System.out.println("warn");
        if ((i & 16) < 2)
            System.out.println("unsupp");
        if ((i | 16) < 2)
            System.out.println("unsupp");
        if ((i & 3) == 2)
            System.out.println("bogus");
        if ((i & 3) != 2)
            System.out.println("bogus");
        if ((i | 3) == 7)
            System.out.println("bogus");
        if ((i | 3) == 7)
            System.out.println("bogus");
    }
    public static void bar(int i) {
        if ((i & 16) == 2)
            return; 
        if ((i & 16) != 2)
            return; 
        if ((i | 16) == 2)
            return; 
        if ((i | 16) != 0)
            return; 
        if ((i & 0) == 1) 
            return; 
        if ((i & 0) != 1) 
            return; 
        if ((i & 0) == 0) 
            return; 
        if ((i & 0) != 0) 
            return; 
        if ((i | 1) == 0)
            return; 
        if ((i | 1) != 0)
            return; 
        if ((i & 16L) == 2)
            return; 
        if ((i & 16L) != 2)
            return; 
        if ((i | 16L) == 2)
            return; 
        if ((i | 16L) != 0)
            return; 
        if ((i & 0L) == 0) 
            return; 
        if ((i & 0L) != 0) 
            return; 
        if ((i | 1L) == 0)
            return; 
        if ((i | 1L) != 0)
            return; 
        System.out.println("foo");
    }
    public static void moreBars(short i) {
        if ((i | 0xff00) == 0xFFFF0000) { System.out.println(); }
        if ((i | 0xff00) == 0x00FF) { System.out.println(); }
    }
}