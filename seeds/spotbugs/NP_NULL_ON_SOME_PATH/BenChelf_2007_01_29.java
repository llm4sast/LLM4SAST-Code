import foundOnTheWeb.*;
public class BenChelf_2007_01_29 {
    public static String listing1(String body) {
        if (body != null) { body = body.trim(); }
        if (body.length() < 1) { body = null; }
        return body;
    }
    public static String listing1FixedAsSuggestedByChen(String body) {
        if (body == null) { body = body.trim(); }
        if (body.length() < 1) { body = null; }
        return body;
    }
    public static String listing1FixedAsSuggestedByPugh(String body) {
        if (body != null) {
            body = body.trim();
            if (body.length() < 1) { body = null; }
        }
        return body;
    }
    public static String listing3(String body) {
        int body_tracker = 5;
        if (body != null) {
            body = body.trim();
        } else { body_tracker = 12; }
        if (body_tracker != 12 && body.length() < 1) { body = null; }
        return body;
    }
    public static String listing4(String body) {
        int body_tracker = 5;
        if (body != null) {
            body = body.trim();
            body_tracker = 12;
        }
        if (body_tracker != 12 && body.length() < 1) { body = null; }
        return body;
    }
}