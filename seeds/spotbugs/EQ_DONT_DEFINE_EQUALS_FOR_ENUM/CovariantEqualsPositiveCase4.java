import com.google.errorprone.bugpatterns.*;
public enum CovariantEqualsPositiveCase4 {
  MERCURY,
  VENUS,
  EARTH,
  MARS,
  JUPITER,
  SATURN,
  URANUS,
  NEPTUNE,
  PLUTO;   
  public boolean equals(CovariantEqualsPositiveCase4 other) { return this == other; }
}