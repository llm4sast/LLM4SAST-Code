import rangeArray.*;
import java.io.File;
import java.io.FileReader;
public class IoOperationRangeArrayOffsetExpected {
    private static final int BUFFER_LENGTH = 10;
    public void ioOperation1() {
        File file = new File("temp.txt");
        try (FileReader fr = new FileReader(file)) {
            char[] buffer = new char[BUFFER_LENGTH];
            fr.read(buffer, BUFFER_LENGTH + 1, 1);
        } catch (Exception e) { }
    }
    public void ioOperation2() {
        File file = new File("temp.txt");
        try (FileReader fr = new FileReader(file)) {
            char[] buffer = new char[BUFFER_LENGTH];
            fr.read(buffer, BUFFER_LENGTH + 2, 1);
        } catch (Exception e) { }
    }
}