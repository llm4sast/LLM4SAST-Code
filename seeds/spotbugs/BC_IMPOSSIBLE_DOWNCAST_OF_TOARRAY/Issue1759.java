import ghIssues.*;
import java.util.ArrayList;
class Issue1759 {
    ArrayList<Integer> lst = new ArrayList<Integer>();
    Integer[] res;
    public Integer[] asArray() {
        res = (Integer[]) ((Object[])(lst.toArray()));  
        return this.res;
    }
}