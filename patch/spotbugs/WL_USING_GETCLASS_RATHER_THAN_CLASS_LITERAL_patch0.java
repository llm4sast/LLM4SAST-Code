public class WL_USING_GETCLASS_RATHER_THAN_CLASS_LITERAL_patch0 {
    private static final String base = "label";
    private static int nameCounter = 0;
    String constructComponentName() {
        synchronized (WL_USING_GETCLASS_RATHER_THAN_CLASS_LITERAL_patch0.class) {
            return base + nameCounter++;
        }
    }
}
