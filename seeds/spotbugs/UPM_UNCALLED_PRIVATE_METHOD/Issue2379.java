import ghIssues.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
public class Issue2379 {
	private static Stream<String> values() { return Stream.of("one", "two"); }
	@ParameterizedTest
	@MethodSource("values")
	public void test(String value) { }
	private static int[] defaultValues() { return new int[] {1, 2}; }
	@ParameterizedTest
	@MethodSource
	public void defaultValues(int value) { }
	private static Stream<String> valuesFromStream() { return Stream.of("one", "two"); }
	private static String[] valuesFromArray() { return new String[] { "three", "four" }; }
	@ParameterizedTest
	@MethodSource({"valuesFromStream", "valuesFromArray"})
	public void multipleSourcesTest(String value) { }
	private Stream<String> unusedValues(int x) { return Stream.of("one", "two"); }
}