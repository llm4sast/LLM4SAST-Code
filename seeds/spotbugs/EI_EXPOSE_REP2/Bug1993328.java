import sfBugs.*;
import edu.umd.cs.findbugs.annotations.DesireWarning;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class Bug1993328 {
    private static Object[] NO_ARGS = new Object[] {};
    private Object[] args1;
    @DesireWarning("EI2")
    public void assign_conditional(Object[] args) { this.args1 = args == null ? NO_ARGS : args; };
    @ExpectWarning("EI2")
    public void assign_explicit(Object[] args) { this.args1 = args; };
    public boolean hasargs() {
        if (args1 != null) { return true; }
        return false;
    }
}