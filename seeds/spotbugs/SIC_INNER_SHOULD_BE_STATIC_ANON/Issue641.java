import suppression.*;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import edu.umd.cs.findbugs.annotations.SuppressMatchType;
import java.io.BufferedReader;
import java.io.IOException;
@SuppressFBWarnings(value = "UUF_UNUSED_FIELD") 
public class Issue641 {
    @SuppressFBWarnings(value = "UUF_UNUSED_FIELD") 
    private String field;
    @SuppressFBWarnings(value = "UUF_UNUSED_FIELD") 
    private String field2;
    @SuppressFBWarnings 
    private String field3;
    @NonNull
    @SuppressFBWarnings(value = {"UUF_UNUSED_FIELD", "NP_NONNULL_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR"}) 
    private String field4;
    public String getField() { return field; }
    public void setField( @SuppressFBWarnings(value = "UUF_UNUSED_FIELD") String val) { field = val; }
    @SuppressFBWarnings(value = "NP_IMMEDIATE_DEREFERENCE_OF_READLINE") 
    public String readLine(BufferedReader reader) throws IOException { return reader.readLine().trim(); }
    public int increment(int x) {
        @SuppressFBWarnings(value = {"DLS_DEAD_LOCAL_STORE", "SIC_INNER_SHOULD_BE_STATIC_ANON"}) 
        Object lock = new Object() {
            @SuppressFBWarnings(value = {"UC_USELESS_VOID_METHOD", "UPM_UNCALLED_PRIVATE_METHOD"}) 
            private void lockObject() {
                @SuppressFBWarnings(value = "DLS_DEAD_LOCAL_STORE") 
                int a = 0;
                a++; 
            }
        };
        return x + 1;
    }
    @SuppressFBWarnings(matchType = SuppressMatchType.REGEX, value = "XYZ") 
    public int decrement(int x) { return x-1; }
}