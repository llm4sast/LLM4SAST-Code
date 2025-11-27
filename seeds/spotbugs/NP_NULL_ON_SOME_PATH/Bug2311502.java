import sfBugs.*;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class Bug2311502 {
    @Documented
    @Nonnull
    @TypeQualifierDefault(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ReturnValuesAreNonnullByDefault { }
    static public class NonNullFalseNegative {
        @CheckForNull
        private Object junkField;
        public void setJunk(Object junk) { this.junkField = junk; }
        public final class BadInnerClass {
            @ExpectWarning("NP")
            public void badMethod() { System.out.println(junkField.hashCode()); }
        }
    }
    static public @ReturnValuesAreNonnullByDefault
    class NPNonNullReturnViolationBug {
        @CheckForNull
        private Object junkField;
        public void setJunk(Object junk) { this.junkField = junk; }
        public final class InnerClass {
            @ExpectWarning("IMA_INEFFICIENT_MEMBER_ACCESS")
            public void printJunk() {
                Object temp = junkField;
                if (temp != null) { System.out.println(temp.hashCode()); }
            }
        }
    }
}