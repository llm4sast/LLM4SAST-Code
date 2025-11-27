import sfBugs.*;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug1816258a {
    @NoWarning("BC_IMPOSSIBLE_CAST")
    public void castTest() {
        Object postObject = new String[] { null };
        String postValue = postObject instanceof String[] ? ((String[]) postObject)[0] : (String) postObject 
        ;
        System.out.println(postValue);
    }
    public static void main(String[] args) {
        Bug1816258a gt = new Bug1816258a();
        gt.castTest();
    }
}