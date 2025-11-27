import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
public class RV_RETURN_VALUE_OF_PUTIFABSENT_IGNORED_bug0 {
    private final ConcurrentMap<String, Calendar> calendars = new ConcurrentHashMap<>();
    public Calendar update(String key, Calendar newValue) {
        calendars.putIfAbsent(key, newValue);
        newValue.add(Calendar.DAY_OF_MONTH, 2);
        return newValue;
    }
}
