import exposemutable.*;
import java.nio.CharBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class ExposeMutableFromMutilevelInner {
    private Date date;
    private Date[] dateArray;
    private Map<Integer, String> hm = new HashMap<>();
    private static Date sDate = new Date();
    private static Date[] sDateArray = new Date[20];
    static {
        for (int i = 0; i < sDateArray.length; i++) { sDateArray[i] = new Date(); }
    }
    private static Map<Integer, String> shm = new HashMap<>();
    static { shm.put(1, "123-45-6789"); }
    private CharBuffer charBuf;
    private char[] charArray;
    private static CharBuffer sCharBuf;
    private static char[] sCharArray;
    public ExposeMutableFromMutilevelInner() {
        date = new Date();
        dateArray = new Date[20];
        hm.put(1, "123-45-6789");
        for (int i = 0; i < dateArray.length; i++) { dateArray[i] = new Date(); }
    }
    public static class MiddleStatic {
        public static class Nested {
            public static Date getStaticDate() { return sDate; }
            public static Date getStaticDate2() {
                Date d = sDate;
                return d;
            }
            public static Date[] getStaticDateArray() { return sDateArray; }
            public static Date[] getStaticDateArray2() { return sDateArray.clone(); }
            public static Map<Integer, String> getStaticValues() { return shm; }
            public static void setStaticDate(Date d) { sDate = d; }
            public static void setStaticDate2(Date d) {
                Date d2 = d;
                sDate = d2;
            }
            public static void setStaticDateArray(Date[] da) { sDateArray = da; }
            public static void setStaticDateArray2(Date[] da) { sDateArray = da.clone(); }
            public static void setStaticValues(Map<Integer, String> values) { shm = values; }
            public static CharBuffer getStaticBuffer() { return sCharBuf; }
            public static CharBuffer getStaticBufferDuplicate() { return sCharBuf.duplicate(); }
            public static CharBuffer getStaticBufferWrap() { return CharBuffer.wrap(sCharArray); }
            public static void setStaticBuffer(CharBuffer cb) { sCharBuf = cb; }
            public static void setStaticBufferDuplicate(CharBuffer cb) { sCharBuf = cb.duplicate(); }
            public static void setStaticBufferWrap(char[] cArr) { sCharBuf = CharBuffer.wrap(cArr); }
        }
    }
    public class Middle {
        public class Inner {
            public Date getDate() { return date; }
            public Date getDate2() {
                Date d = date;
                return d;
            }
            public Date[] getDateArray() { return dateArray; }
            public Date[] getDateArray2() { return dateArray.clone(); }
            public Map<Integer, String> getValues() { return hm; }
            public void setDate(Date d) { date = d; }
            public void setDate2(Date d) {
                Date d2 = d;
                date = d2;
            }
            public void setDateArray(Date[] da) { dateArray = da; }
            public void setDateArray2(Date[] da) { dateArray = da.clone(); }
            public void setValues(Map<Integer, String> values) { hm = values; }
            public CharBuffer getBuffer() { return charBuf; }
            public CharBuffer getBufferDuplicate() { return charBuf.duplicate(); }
            public CharBuffer getBufferWrap() { return CharBuffer.wrap(charArray); }
            public void setBuffer(CharBuffer cb) { charBuf = cb; }
            public void setBufferDuplicate(CharBuffer cb) { charBuf = cb.duplicate(); }
            public void setBufferWrap(char[] cArr) { charBuf = CharBuffer.wrap(cArr); }
        }
    }
}