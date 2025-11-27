import ghIssues.*;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.requireNonNull;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.Nullable;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.When;
public class Issue782 {
    public void func() {
        Object a = get(); 
        Object b = checkNotNull(get()); 
        Object c = checkNotNull(
                get()); 
        System.out.println(a.toString());
        System.out.println(b.toString());
        System.out.println(c.toString());
    }
    public void func2() {
        Object a = get2(); 
        Object b = checkNotNull(get2()); 
        Object c = checkNotNull(
                get2()); 
        System.out.println(a.toString());
        System.out.println(b.toString());
        System.out.println(c.toString());
    }
    public void func3() {
        Object a = get2(); 
        Object b = requireNonNull(get2()); 
        Object c = requireNonNull(
                get2()); 
        System.out.println(a.toString());
        System.out.println(b.toString());
        System.out.println(c.toString());
    }
    @MyNullable
    public Object get() { return null; }
    @Nullable
    public Object get2() { return null; }
    @TypeQualifierNickname
    @javax.annotation.Nonnull(when = When.MAYBE)
    @Retention(RetentionPolicy.CLASS)
    public @interface MyNullable { }
}