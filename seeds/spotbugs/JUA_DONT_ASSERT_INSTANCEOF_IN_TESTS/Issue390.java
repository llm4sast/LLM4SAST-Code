import ghIssues.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;
public class Issue390 {
    public void not_test() {
        Integer intVal = 1;
        assertTrue(intVal instanceof Integer);
    }
    @Test
    public void test() {
        Integer intVal = 1;
        assertTrue(intVal instanceof Integer);
    }
}