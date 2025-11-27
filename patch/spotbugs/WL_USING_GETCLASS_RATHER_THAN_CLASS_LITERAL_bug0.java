public class WL_USING_GETCLASS_RATHER_THAN_CLASS_LITERAL_bug0 {
    private static final String base = "label";
    private static int nameCounter = 0;
    String constructComponentName() {
        synchronized (getClass()) {
            return base + nameCounter++;
        }
    }
}
