import sfBugs.*;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class Bug2003253 {
    @Nonnull
    private Object foo;
    @ExpectWarning("NP")
    public void report1(@Nullable Object bar) { this.foo = bar; }
    @ExpectWarning("NP")
    public void report2(@CheckForNull Object bar) { this.foo = bar; }
    @ExpectWarning("NP")
    public int report3(@CheckForNull Object bar) { return bar.hashCode(); }
    @ExpectWarning("NP")
    public int report4(@CheckForNull Object bar) { return nonnull(bar); }
    public int nonnull(@Nonnull Object bar) { return bar.hashCode(); }
}