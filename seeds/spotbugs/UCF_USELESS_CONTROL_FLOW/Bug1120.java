import sfBugsNew.*;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug1120 {
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