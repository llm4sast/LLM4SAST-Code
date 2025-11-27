import gcUnrelatedTypes.*;
import java.util.ArrayList;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class ArrayListContains<T> {
    static class Dummy { }
    static class DummyChild extends Dummy { }
    private ArrayList<?> wildcardF;
    private ArrayList<Dummy> dummyF;
    private ArrayList<? extends Dummy> dummyEF;
    private ArrayList<? super Dummy> dummySF;
    private ArrayList<DummyChild> childF;
    private ArrayList<? extends DummyChild> childEF;
    private ArrayList<? super DummyChild> childSF;
    private ArrayList<T> genericF;
    private ArrayList<? extends T> genericEF;
    private ArrayList<? super T> genericSF;
    @ExpectWarning("GC")
    public ArrayListContains(ArrayList<?> wildcardF, ArrayList<Dummy> dummyF, ArrayList<? extends Dummy> dummyEF,
            ArrayList<? super Dummy> dummySF, ArrayList<DummyChild> childF, ArrayList<? extends DummyChild> childEF,
            ArrayList<? super DummyChild> childSF, ArrayList<T> genericF, ArrayList<? extends T> genericEF,
            ArrayList<? super T> genericSF) {
        this.wildcardF = wildcardF;
        this.dummyF = dummyF;
        this.dummyEF = dummyEF;
        this.dummySF = dummySF;
        this.childF = childF;
        this.childEF = childEF;
        this.childSF = childSF;
        this.genericF = genericF;
        this.genericEF = genericEF;
        this.genericSF = genericSF;
        Dummy dummy = new Dummy();
        DummyChild dummyChild = new DummyChild();
        String s = "Mismatched Type";
        wildcardF.contains(dummy); 
        wildcardF.contains(dummyChild); 
        wildcardF.contains(s); 
        dummyF.contains(dummy); 
        dummyF.contains(dummyChild); 
        dummyF.contains(s); 
        dummyEF.contains(dummy); 
        dummyEF.contains(dummyChild); 
        dummyEF.contains(s); 
        dummySF.contains(dummy); 
        dummySF.contains(dummyChild); 
        dummySF.contains(s); 
        childF.contains(dummy); 
        childF.contains(dummyChild); 
        childF.contains(s); 
        childEF.contains(dummy); 
        childEF.contains(dummyChild); 
        childEF.contains(s); 
        childSF.contains(dummy); 
        childSF.contains(dummyChild); 
        childSF.contains(s); 
        genericF.contains(dummy); 
        genericF.contains(dummyChild); 
        genericF.contains(s); 
        genericEF.contains(dummy); 
        genericEF.contains(dummyChild); 
        genericEF.contains(s); 
        genericSF.contains(dummy); 
        genericSF.contains(dummyChild); 
        genericSF.contains(s); 
    }
    @ExpectWarning("GC")
    public void testFields() {
        Dummy dummy = new Dummy();
        DummyChild dummyChild = new DummyChild();
        String s = "Mismatched Type";
        wildcardF.contains(dummy); 
        wildcardF.contains(dummyChild); 
        wildcardF.contains(s); 
        dummyF.contains(dummy); 
        dummyF.contains(dummyChild); 
        dummyF.contains(s); 
        dummyEF.contains(dummy); 
        dummyEF.contains(dummyChild); 
        dummyEF.contains(s); 
        dummySF.contains(dummy); 
        dummySF.contains(dummyChild); 
        dummySF.contains(s); 
        childF.contains(dummy); 
        childF.contains(dummyChild); 
        childF.contains(s); 
        childEF.contains(dummy); 
        childEF.contains(dummyChild); 
        childEF.contains(s); 
        childSF.contains(dummy); 
        childSF.contains(dummyChild); 
        childSF.contains(s); 
        genericF.contains(dummy); 
        genericF.contains(dummyChild); 
        genericF.contains(s); 
        genericEF.contains(dummy); 
        genericEF.contains(dummyChild); 
        genericEF.contains(s); 
        genericSF.contains(dummy); 
        genericSF.contains(dummyChild); 
        genericSF.contains(s); 
    }
}