public class LI_LAZY_INIT_UPDATE_STATIC_patch0 {
    static String[] weekends;
    public synchronized static String[] getWeekends() {
        if (weekends == null) {
            weekends = new String[2];
            weekends[0] = "Sunday";
            weekends[1] = "Saturday";
        }
        return weekends;
    }
}