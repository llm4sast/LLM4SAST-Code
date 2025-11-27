import multithreaded.compoundoperation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class CompoundXOROperationOnSharedVariable {
    private final List<Integer> nums = Collections.synchronizedList(new ArrayList<>()); 
    private Boolean flag = true;
    public void toggle(Boolean tmp) { flag ^= tmp; }
    public boolean getFlag() { return flag; }
}