import sfBugs.*;
public class RFE3052560 {
    public int foo(byte b) {
        int i = (int) b; 
        return i;
    }
    public int foo(byte b1, byte b2) {
        int i =  b1<< 8 | b2;
        return i;
    }
    public int fooFP(byte b) {
        int i = ((int) b) & 0xFF;
        return i;
    }
}