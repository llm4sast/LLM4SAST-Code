import ghIssues.issue543.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.immutables.value.Generated;
@Generated(from = "FoobarValue", generator = "Immutables")
@SuppressWarnings({"all"})
final class ImmutableFoobarValue extends FoobarValue {
  private final int foo;
  private final String bar;
  private final List<Integer> buz;
  private final Set<Long> crux;
  private ImmutableFoobarValue(
      int foo,
      String bar,
      List<Integer> buz,
      Set<Long> crux) {
    this.foo = foo;
    this.bar = bar;
    this.buz = buz;
    this.crux = crux;
  }
  @Override
  public int foo() { return foo; }
  @Override
  public String bar() { return bar; }
  @Override
  public List<Integer> buz() { return buz; }
  @Override
  public Set<Long> crux() { return crux; }
  public final ImmutableFoobarValue withFoo(int value) {
    if (this.foo == value) return this;
    return new ImmutableFoobarValue(value, this.bar, this.buz, this.crux);
  }
  public final ImmutableFoobarValue withBar(String value) {
    String newValue = Objects.requireNonNull(value, "bar");
    if (this.bar.equals(newValue)) return this;
    return new ImmutableFoobarValue(this.foo, newValue, this.buz, this.crux);
  }
  public final ImmutableFoobarValue withBuz(int... elements) {
    ArrayList<Integer> wrappedList = new ArrayList<>(elements.length);
    for (int element : elements) { wrappedList.add(element); }
    List<Integer> newValue = createUnmodifiableList(false, wrappedList);
    return new ImmutableFoobarValue(this.foo, this.bar, newValue, this.crux);
  }
  public final ImmutableFoobarValue withBuz(Iterable<Integer> elements) {
    if (this.buz.equals(elements)) return this;
    List<Integer> newValue = createUnmodifiableList(false, createSafeList(elements, true, false));
    return new ImmutableFoobarValue(this.foo, this.bar, newValue, this.crux);
  }
  public final ImmutableFoobarValue withCrux(long... elements) {
    ArrayList<Long> wrappedList = new ArrayList<>(elements.length);
    for (long element : elements) { wrappedList.add(element); }
    Set<Long> newValue = createUnmodifiableSet(wrappedList);
    return new ImmutableFoobarValue(this.foo, this.bar, this.buz, newValue);
  }
  public final ImmutableFoobarValue withCrux(Iterable<Long> elements) {
    if (this.crux.equals(elements)) return this;
    Set<Long> newValue = createUnmodifiableSet(createSafeList(elements, true, false));
    return new ImmutableFoobarValue(this.foo, this.bar, this.buz, newValue);
  }
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableFoobarValue
        && equalTo(0, (ImmutableFoobarValue) another);
  }
  private boolean equalTo(int synthetic, ImmutableFoobarValue another) {
    return foo == another.foo
        && bar.equals(another.bar)
        && buz.equals(another.buz)
        && crux.equals(another.crux);
  }
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + foo;
    h += (h << 5) + bar.hashCode();
    h += (h << 5) + buz.hashCode();
    h += (h << 5) + crux.hashCode();
    return h;
  }
  @Override
  public String toString() {
    return "FoobarValue{"
        + "foo=" + foo
        + ", bar=" + bar
        + ", buz=" + buz
        + ", crux=" + crux
        + "}";
  }
  public static ImmutableFoobarValue copyOf(FoobarValue instance) {
    if (instance instanceof ImmutableFoobarValue) { return (ImmutableFoobarValue) instance; }
    return ImmutableFoobarValue.builder()
        .from(instance)
        .build();
  }
  public static ImmutableFoobarValue.Builder builder() { return new ImmutableFoobarValue.Builder(); }
  @Generated(from = "FoobarValue", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_FOO = 0x1L;
    private static final long INIT_BIT_BAR = 0x2L;
    private long initBits = 0x3L;
    private int foo;
    private String bar;
    private List<Integer> buz = new ArrayList<Integer>();
    private List<Long> crux = new ArrayList<Long>();
    private Builder() { }
    public final Builder from(FoobarValue instance) {
      Objects.requireNonNull(instance, "instance");
      foo(instance.foo());
      bar(instance.bar());
      addAllBuz(instance.buz());
      addAllCrux(instance.crux());
      return this;
    }
    public final Builder foo(int foo) {
      this.foo = foo;
      initBits &= ~INIT_BIT_FOO;
      return this;
    }
    public final Builder bar(String bar) {
      this.bar = Objects.requireNonNull(bar, "bar");
      initBits &= ~INIT_BIT_BAR;
      return this;
    }
    public final Builder addBuz(int element) {
      this.buz.add(element);
      return this;
    }
    public final Builder addBuz(int... elements) {
      for (int element : elements) { this.buz.add(element); }
      return this;
    }
    public final Builder buz(Iterable<Integer> elements) {
      this.buz.clear();
      return addAllBuz(elements);
    }
    public final Builder addAllBuz(Iterable<Integer> elements) {
      for (Integer element : elements) { this.buz.add(Objects.requireNonNull(element, "buz element")); }
      return this;
    }
    public final Builder addCrux(long element) {
      this.crux.add(element);
      return this;
    }
    public final Builder addCrux(long... elements) {
      for (long element : elements) { this.crux.add(element); }
      return this;
    }
    public final Builder crux(Iterable<Long> elements) {
      this.crux.clear();
      return addAllCrux(elements);
    }
    public final Builder addAllCrux(Iterable<Long> elements) {
      for (Long element : elements) { this.crux.add(Objects.requireNonNull(element, "crux element")); }
      return this;
    }
    public ImmutableFoobarValue build() {
      if (initBits != 0) { throw new IllegalStateException(formatRequiredAttributesMessage()); }
      return new ImmutableFoobarValue(foo, bar, createUnmodifiableList(true, buz), createUnmodifiableSet(crux));
    }
    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_FOO) != 0) attributes.add("foo");
      if ((initBits & INIT_BIT_BAR) != 0) attributes.add("bar");
      return "Cannot build FoobarValue, some of required attributes are not set " + attributes;
    }
  }
  private static <T> List<T> createSafeList(Iterable<? extends T> iterable, boolean checkNulls, boolean skipNulls) {
    ArrayList<T> list;
    if (iterable instanceof Collection<?>) {
      int size = ((Collection<?>) iterable).size();
      if (size == 0) return Collections.emptyList();
      list = new ArrayList<>();
    } else { list = new ArrayList<>(); }
    for (T element : iterable) {
      if (skipNulls && element == null) continue;
      if (checkNulls) Objects.requireNonNull(element, "element");
      list.add(element);
    }
    return list;
  }
  private static <T> List<T> createUnmodifiableList(boolean clone, List<T> list) {
    switch(list.size()) {
    case 0: return Collections.emptyList();
    case 1: return Collections.singletonList(list.get(0));
    default:
      if (clone) {
        return Collections.unmodifiableList(new ArrayList<>(list));
      } else {
        if (list instanceof ArrayList<?>) { ((ArrayList<?>) list).trimToSize(); }
        return Collections.unmodifiableList(list);
      }
    }
  }
  private static <T> Set<T> createUnmodifiableSet(List<T> list) {
    switch(list.size()) {
    case 0: return Collections.emptySet();
    case 1: return Collections.singleton(list.get(0));
    default:
      Set<T> set = new LinkedHashSet<>(list.size());
      set.addAll(list);
      return Collections.unmodifiableSet(set);
    }
  }
}