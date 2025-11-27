import sfBugs.*;
import java.io.Serializable;
import java.util.List;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class RFE2958584 implements Serializable {
    private static final long serialVersionUID = 1L;
    @ExpectWarning("SE_BAD_FIELD")
    private List<NotSerializable> list; 
    @ExpectWarning("SE_BAD_FIELD")
    private NotSerializable ns;
    static class NotSerializable { }
}