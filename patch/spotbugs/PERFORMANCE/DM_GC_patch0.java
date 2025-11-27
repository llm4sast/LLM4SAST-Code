public class DM_GC_patch0 {
    public void ok() {
        try {
            System.out.println("ok()");
        } catch (OutOfMemoryError e) {
            System.gc();
        }
    }
}
