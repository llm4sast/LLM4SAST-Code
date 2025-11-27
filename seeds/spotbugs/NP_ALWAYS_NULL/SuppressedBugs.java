import suppress.*;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import edu.umd.cs.findbugs.annotations.SuppressMatchType;
public class SuppressedBugs {
	@SuppressFBWarnings(value = "NP")
	public int suppressedNpePrefix() {
		String x = null;
		return x.length();
	}
	@SuppressFBWarnings(value = "XYZ")
	public int nonSuppressedNpePrefix() {
		String x = null;
		return x.length();
	}
	@SuppressFBWarnings(value = {"NP_ALWAYS_NULL", "NP_LOAD_OF_KNOWN_NULL_VALUE"}, matchType = SuppressMatchType.EXACT)
	public int suppressedNpeExact() {
		String x = null;
		return x.length();
	}
	@SuppressFBWarnings(value = "XYZ", matchType = SuppressMatchType.EXACT)
	public int nonSuppressedNpeExact() {
		String x = null;
		return x.length();
	}
	@SuppressFBWarnings(value = {"np_always_null", "np_load_of_known_null_value"}, matchType = SuppressMatchType.EXACT)
	public int nonSuppressedNpeExactDifferentCase() {
		String x = null;
		return x.length();
	}
	@SuppressFBWarnings(value = "NP.*", matchType = SuppressMatchType.REGEX)
	public int suppressedNpeRegex() {
		String x = null;
		return x.length();
	}
	@SuppressFBWarnings(value = "XYZ.*", matchType = SuppressMatchType.REGEX)
	public int nonSuppressedNpeRegex() {
		String x = null;
		return x.length();
	}
}