import com.google.errorprone.bugpatterns.*;
import com.google.common.base.Objects;
public class SelfEqualsPositiveCase1 {
  private String field = "";
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    SelfEqualsPositiveCase1 other = (SelfEqualsPositiveCase1) o;
    boolean retVal;
    retVal = Objects.equal(field, field);
    retVal &= Objects.equal(field, this.field);
    retVal &= Objects.equal(this.field, field);
    retVal &= Objects.equal(this.field, this.field);
    return retVal;
  }
  @Override
  public int hashCode() { return Objects.hashCode(field); }
  public static void test() {
    ForTesting tester = new ForTesting();
    Objects.equal(tester.testing.testing, tester.testing.testing);
  }
  private static class ForTesting {
    public ForTesting testing;
    public String string;
  }
}