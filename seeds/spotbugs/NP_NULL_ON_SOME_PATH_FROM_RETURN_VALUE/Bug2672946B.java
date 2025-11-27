import sfBugs.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.CheckForNull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.meta.TypeQualifierDefault;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
import jsr305.FieldsNonNullByDefault;
@FieldsNonNullByDefault
@MethodsAreCheckNullByDefault
@ParametersAreNonnullByDefault
abstract class Bug2672946B extends Bug2672946A {
    public Bug2672946B(Bug2672946B field) { super(field); }
    @NoWarning("NP_METHOD_RETURN_RELAXING_ANNOTATION")
    @Override
    public Bug2672946B getField() {
        Bug2672946A field = super.getField();
        if (field.hashCode() == 42)
            System.out.println("Found it");
        return (Bug2672946B) field;
    }
    @ExpectWarning("NP_METHOD_RETURN_RELAXING_ANNOTATION")
    @CheckForNull
    @Override
    public Bug2672946B getField2() {
        Bug2672946A field = super.getField2();
        if (field.hashCode() == 42)
            System.out.println("Found it");
        return (Bug2672946B) field;
    }
    @ExpectWarning("NP")
    public Object foo() {
        if (bar().hashCode() == 17)
            return "x";
        return null;
    }
    @NoWarning("NP")
    public Object foo2() {
        if (getField().hashCode() == 17)
            return "x";
        return null;
    }
    @ExpectWarning("NP")
    public Object foo3() {
        if (bar2().hashCode() == 17)
            return "x";
        return null;
    }
    @ExpectWarning("NP")
    public Object foo4() {
        if (getField2().hashCode() == 17)
            return "x";
        return null;
    }
    abstract Object bar();
    abstract @CheckForNull Object bar2();
}
@CheckForNull
@TypeQualifierDefault(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface MethodsAreCheckNullByDefault { }