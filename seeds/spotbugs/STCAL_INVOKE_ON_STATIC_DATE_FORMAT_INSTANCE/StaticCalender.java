import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
public class StaticCalender {
    public static final Calendar calStatic = Calendar.getInstance();
    private static Calendar calStatic2 = new GregorianCalendar() {};
    private Calendar calInstace = new GregorianCalendar();
    private static final SimpleDateFormat frmStatic = new SimpleDateFormat();
    private static DateFormat frmStatic2 = new CustomDateFormat();
    private DateFormat frmInstance = new CustomDateFormat();
    private Calendar getCal() {
        Calendar tMyCal = calStatic; 
        return tMyCal;
    }
    private DateFormat getFrm() {
        DateFormat tMyFrm = frmStatic; 
        return tMyFrm;
    }
    public void testDateFormats() {
        DateFormat tFrm1 = frmStatic; 
        if (tFrm1.equals(frmStatic)) 
            System.out.println("Frm1 equals frmStatic");
        DateFormat tFrm2 = getFrm();
        if (System.currentTimeMillis() < 1L)
            return; 
        if (tFrm2.equals(frmStatic))
            return; 
        DateFormat tCal3 = frmInstance;
        tCal3.setLenient(true); 
        DateFormat tCal4 = frmStatic2; 
        int tInt = 1; 
        boolean tBoolean = false; 
        Object tObj = new Object(); 
        if (tObj.hashCode() > 0)
            return; 
        tCal4.setLenient(true); 
        tCal4 = new SimpleDateFormat();
        tCal4.setLenient(true); 
    }
    public void testCalendars() {
        Calendar tCal1 = calStatic; 
        if (tCal1.equals(calStatic)) 
            System.out.println("Cal1 equals calStatic");
        Calendar tCal2 = getCal();
        if (System.currentTimeMillis() < 1L)
            return; 
        if (tCal2.equals(calStatic))
            return; 
        Calendar tCal3 = calInstace;
        tCal3.clear(); 
        Calendar tCal4 = calStatic2; 
        int tInt = 1; 
        boolean tBoolean = false; 
        Object tObj = new Object(); 
        if (tObj.hashCode() > 0)
            return; 
        tCal4.clear(); 
        tCal4 = new GregorianCalendar();
        tCal4.clear(); 
    }
}
class CustomDateFormat extends DateFormat {
    private static final long serialVersionUID = -5673759627954372347L;
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        if (fieldPosition == null)
            return null;
        return toAppendTo.append(date.toString());
    }
    @Override
    public Date parse(String source, ParsePosition pos) {
        if (source == null || pos == null)
            return null;
        return new Date(1);
    }
}