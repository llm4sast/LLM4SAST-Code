import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
public class RV_RETURN_VALUE_OF_PUTIFABSENT_IGNORED_patch0 {
    private final ConcurrentMap<String, Calendar> calendars = new ConcurrentHashMap<>();
    public void update(String key, Calendar newValue) {
        calendars.putIfAbsent(key, newValue);
    }
}
