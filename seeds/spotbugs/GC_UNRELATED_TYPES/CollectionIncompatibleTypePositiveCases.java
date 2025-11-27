import com.google.errorprone.bugpatterns.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CollectionIncompatibleTypePositiveCases {
  Collection<String> collection = new ArrayList<String>();
  public boolean bug() { return collection.contains(this); }
  public boolean bug2() { return new ArrayList<String>().remove(new Date()); }
  public boolean bug3() {
    List<String> list = new ArrayList<String>(collection);
    return list.contains(new Exception());
  }
  public String bug4() {
    Map<Integer, String> map = new HashMap<Integer, String>();
    return map.get("not an integer");
  }
}