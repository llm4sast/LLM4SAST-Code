import bugPatterns.*;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class DMI_INVOKING_HASHCODE_ON_ARRAY {
    @ExpectWarning("DMI_INVOKING_HASHCODE_ON_ARRAY")
    void bug(int[] any) { any.hashCode(); }
    @ExpectWarning("DMI_INVOKING_HASHCODE_ON_ARRAY")
    void bug(Object[] any) { any.hashCode(); }
    @ExpectWarning("DMI_INVOKING_HASHCODE_ON_ARRAY")
    void bug(long[] any) { any.hashCode(); }
    @NoWarning("DMI_INVOKING_HASHCODE_ON_ARRAY")
    void notBug(Object any) { any.hashCode(); }
}