import sfBugs.*;
import edu.umd.cs.findbugs.annotations.DesireNoWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug3553542 {
    @DesireNoWarning( "RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE")
    public static void checkIfNullIsReturned(GoodBehavingClass goodBehavingClass) {
        String isNullReturned;
        isNullReturned = goodBehavingClass.isNullReturned();
        if (isNullReturned != null) {
            System.out.println("No it isn't. It has length " + isNullReturned.length());
        } else { System.out.println("Yes it is."); }
    }
    static class GoodBehavingClass {
        public String isNullReturned() { return "noop"; }
    }
    static class BadBehavingClass  {
        public String isNullReturned() { return null; }
    }
    public static void main(String[] args) { checkIfNullIsReturned(new GoodBehavingClass()); }
    Object globalError;
    Object getGlobalError() { return globalError; }
    void myMethod() {
      if (Math.random() > 0.5)
          globalError = "x";
    }
    @NoWarning("RCN")
    void myProg() {
      globalError = null;
      myMethod();
      if (globalError != null) { }
    }
}