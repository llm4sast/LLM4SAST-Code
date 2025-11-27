import multithreaded.compoundoperation.*;
public class CompoundIANDOperationOnSharedVariable {
    private int num = 1;
    private volatile int volatileField; 
    public void toggle() { num &= 3; }
    public double getNum() { return num; }
}