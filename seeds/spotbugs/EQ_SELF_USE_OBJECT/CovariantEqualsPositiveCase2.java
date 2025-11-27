import com.google.errorprone.bugpatterns.*;
public class CovariantEqualsPositiveCase2 {
  int i, j, k;
  public boolean equals(CovariantEqualsPositiveCase2 other) {
    if (i == other.i && j == other.j && k == other.k) { return true; }
    return false;
  }
}