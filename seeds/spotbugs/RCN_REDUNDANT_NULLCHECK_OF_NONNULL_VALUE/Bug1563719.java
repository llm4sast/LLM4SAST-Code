import sfBugs.*;
import java.util.ArrayList;
import java.util.Iterator;
public class Bug1563719 {
    public static int f(ArrayList list) {
        ArrayList workingList = (ArrayList) list.clone();
        Iterator iter = workingList != null ? workingList.iterator() : null;
        return workingList.size();
    }
}