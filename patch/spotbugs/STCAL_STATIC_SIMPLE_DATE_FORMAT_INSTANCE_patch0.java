import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
public class STCAL_STATIC_SIMPLE_DATE_FORMAT_INSTANCE_patch0 {
    private static final DateFormat format = new SimpleDateFormat("MM");
    static synchronized String formatDate(Date date) {
        return format.format(date);
    }
}
