import type.annotation.*;
import java.util.List;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
public class CheckerFrameworkTypeAnnotations {
    @NonNull
    public String returningNullOnNonNullMethod() { return null; }
    public <V extends @NonNull Object> List<@NonNull V> returningNullWithNonNullOnGenerics() { return null; }
    @NonNull
    public <V extends @NonNull Object> List<@NonNull V> returningNullOnNonNullMethodWithNonNullOnGenerics() { return null; }
    public void usingNullForNonNullParameter() { nonNullParameter(null); }
    public void nonNullParameter(@NonNull Object o) { }
    public void usingNullParameterWithNonNullOnGenerics() { parameterWithNonNullOnGenerics(null); }
    public <V extends @NonNull Object> void parameterWithNonNullOnGenerics(List<@NonNull V> list) { }
    public void usingNullOnNonNullParameterWithNonNullOnGenerics() { nonNullParameterWithNonNullOnGenerics(null); }
    public <V extends @NonNull Object> void nonNullParameterWithNonNullOnGenerics(@NonNull List<@NonNull V> list) { }
    public int usingNonNullArrayOfNullable() { return returningNonNullArrayOfNullable().hashCode(); }
    public @Nullable String @NonNull [] returningNonNullArrayOfNullable() { return new String[5]; }
}