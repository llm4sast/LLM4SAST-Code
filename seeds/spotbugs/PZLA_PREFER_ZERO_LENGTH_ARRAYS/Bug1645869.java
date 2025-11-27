import sfBugs.*;
public class Bug1645869 {
    boolean booleanMethod() { return true; }
    byte byteMethod() { return 1; }
    char charMethod() { return 1; }
    short shortMethod() { return 1; }
    int intMethod() { return 1; }
    long longMethod() { return 1; }
    double doubleMethod() { return 1; }
    float floatMethod() { return 1; }
    Object objectMethod() { return null; }
    Object[] arrayMethod() { return null; }
    static class Useless extends Bug1645869 {
        @Override
        boolean booleanMethod() { return super.booleanMethod(); }
        @Override
        byte byteMethod() { return super.byteMethod(); }
        @Override
        char charMethod() { return super.charMethod(); }
        @Override
        short shortMethod() { return super.shortMethod(); }
        @Override
        int intMethod() { return super.intMethod(); }
        @Override
        long longMethod() { return super.longMethod(); }
        @Override
        double doubleMethod() { return super.doubleMethod(); }
        @Override
        float floatMethod() { return super.floatMethod(); }
        @Override
        Object objectMethod() { return super.objectMethod(); }
        @Override
        Object[] arrayMethod() { return super.arrayMethod(); }
    }
    static class Uselessful extends Bug1645869 {
        @Override
        final boolean booleanMethod() { return super.booleanMethod(); }
        @Override
        final byte byteMethod() { return super.byteMethod(); }
        @Override
        final char charMethod() { return super.charMethod(); }
        @Override
        final short shortMethod() { return super.shortMethod(); }
        @Override
        final int intMethod() { return super.intMethod(); }
        @Override
        final long longMethod() { return super.longMethod(); }
        @Override
        final double doubleMethod() { return super.doubleMethod(); }
        @Override
        final float floatMethod() { return super.floatMethod(); }
        @Override
        final Object objectMethod() { return super.objectMethod(); }
        @Override
        final Object[] arrayMethod() { return super.arrayMethod(); }
    }
}