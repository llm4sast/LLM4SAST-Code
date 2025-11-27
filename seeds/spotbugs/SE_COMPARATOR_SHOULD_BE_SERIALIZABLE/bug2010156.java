import sfBugs.b.*;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Comparator;
public class bug2010156 extends sfBugs.a.bug2010156 {
    private int i, j, k;
    protected int mask = 0;
    public bug2010156(int a, int b, int c) {
        i = a;
        j = b;
        k = c;
    }
    public int a() { return i + j + k; }
    public class subclass extends bug2010156 {
        protected int mask = 1;
        subclass() { super(0, 0, 0); }
        public int getMask() { return mask; }
    }
    public static class extern implements Externalizable {
        protected int var;
        extern(int i) { var = i; }
        public int getVar() { return var; }
        @Override
        public void readExternal(ObjectInput in) { var = 0; }
        @Override
        public void writeExternal(ObjectOutput out) { var = 1; }
    }
    static class serial extends sfBugs.b.bug2010156 implements Serializable {
        private static final long serialVersionUID = 1L;
        serial() { super(1, 2, 3); }
    }
    static class compare implements Comparator<Object> {
        @Override
        public int compare(Object arg0, Object arg1) { return arg0.hashCode() - arg1.hashCode(); }
    }
    static class clone implements Cloneable { }
}