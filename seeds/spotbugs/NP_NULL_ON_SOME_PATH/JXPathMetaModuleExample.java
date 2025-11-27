import npe.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
public class JXPathMetaModuleExample {
    public Object[] getCollectionAsArray(Collection c) {
        List values = null;
        Iterator i = c.iterator();
        if (i.hasNext()) { values = new LinkedList(); }
        while (i.hasNext()) { values.add(i.next()); }
        Object[] obj = values.toArray(); 
        return obj;
    }
    public Object[] addCollectionToListDoNotReport(Collection c, List values) {
        if (values == null)
            System.out.println("Values shouldn't be null");
        Iterator i = c.iterator();
        if (i.hasNext()) { values = new LinkedList(); }
        while (i.hasNext()) { values.add(i.next()); }
        Object[] obj = values.toArray(); 
        return obj;
    }
    public int variation(Object x, int y) {
        int result = 2;
        if (y >= 0) {
            if (x == null)
                result = 1;
            if (y > 0)
                result *= y;
        } else
            x = new Object();
        result += x.hashCode();
        return result;
    }
}