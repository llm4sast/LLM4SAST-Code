import ghIssues.*;
public class Issue2142 {
    int foo;
    void foo1() { foo = foo++; }
    class Inner {
        void foo2() { foo = foo++; }
    }    
}