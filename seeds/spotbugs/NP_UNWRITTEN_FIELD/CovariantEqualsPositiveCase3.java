import com.google.errorprone.bugpatterns.*;
public class CovariantEqualsPositiveCase3 {
  boolean isInVersion;
  String whitelist;
  public boolean equals(CovariantEqualsPositiveCase3 that) {
    return ((this.isInVersion == that.isInVersion) &&
            this.whitelist.equals(that.whitelist));
  }
}