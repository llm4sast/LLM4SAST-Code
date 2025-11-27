import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
class Feature330 {
    private volatile List<String> list;
    private volatile Point pt;
    
    public List<String> getList() {
        if(list == null) {
            synchronized(Feature330.class) {
                if(list == null) {
                    list = new ArrayList<>();
                    list.add("test string");
                }
            }
        }
        return list;
    }
    public Point getPoint() {
        if(pt == null) {
            synchronized(Feature330.class) {
                if(pt == null) {
                    pt = new Point();
                    pt.x = 1;
                    pt.y = 2;
                }
            }
        }
        return pt;
    }
    public List<String> getListCorrect() {
        if(list == null) {
            synchronized(Feature330.class) {
                if(list == null) {
                    List<String> list = new ArrayList<>();
                    list.add("test string");
                    this.list = list;
                }
            }
        }
        return list;
    }
}