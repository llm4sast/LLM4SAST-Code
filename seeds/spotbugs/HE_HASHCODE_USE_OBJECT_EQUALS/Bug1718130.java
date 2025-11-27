import sfBugs.*;
import org.apache.tapestry.spring.SpringBean;
import com.google.inject.Inject;
public class Bug1718130 {
    @Inject
    Object x;
    @SpringBean
    Object y;
    Object z;
    Bug1718130() { z = y; }
    @Override
    public int hashCode() { return x.hashCode() + y.hashCode() + z.hashCode(); }
}