import bugIdeas.*;
public class Ideas_2009_01_05 {
    Object x;
    void pleaseGiveMeNonnull(Object x) {
        if (x == null)
            throw new NullPointerException();
        this.x = x;
    }
    void pleaseGiveMeNonnull2(Object x) {
        if (x == null)
            throw new NullPointerException("x isn't allowed to be null");
        this.x = x;
    }
    int getHash() { return x.hashCode(); }
    void test() { pleaseGiveMeNonnull(null); }
    void test2() { pleaseGiveMeNonnull2(null); }
}