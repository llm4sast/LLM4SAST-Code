import sfBugs.*;
import java.util.HashMap;
import java.util.Map;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class Bug2602466 {
    public Map<Long, String> error_lookup = new HashMap<Long, String>();
    public void setHighValueKey() { error_lookup.put(new Long(0x80000001), "Unknown error."); }
    @ExpectWarning("Bx")
    public void setLowValueKey() { error_lookup.put(new Long(0), "Success."); }
}