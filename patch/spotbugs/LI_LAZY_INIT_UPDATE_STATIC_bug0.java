public class LI_LAZY_INIT_UPDATE_STATIC_bug0 {
    static String[] weekends;
    public static String[] getWeekends() {
        if (weekends == null) {
            weekends = new String[2];
            weekends[0] = "Sunday";
            weekends[1] = "Saturday";
        }
        return weekends;
    }
}