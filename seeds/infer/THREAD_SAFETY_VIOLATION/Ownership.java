import codetoanalyze.java.checkers.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;
import javax.inject.Provider;
class Obj {
  Object f;
  Obj g;
}
interface CustomProvider<T> extends Provider<T> {
  @Override
  public T get();
}
@ThreadSafe
class Ownership {
  Obj field;
  public Ownership() {}
  public Ownership(Obj o) { field = o; }
  @Inject
  Ownership(Provider<Obj> objProvider) {
    Obj owned = objProvider.get();
    owned.f = new Object(); 
  }
  @Inject
  Ownership(CustomProvider<Obj> objProvider) {
    Obj owned = objProvider.get();
    owned.f = new Object(); 
  }
  Obj mInjectedField1;
  Obj mInjectedField2;
  @Inject
  Ownership(Obj injectedField1, Obj injectedField2) {
    mInjectedField1 = injectedField1;
    mInjectedField2 = injectedField2;
    mInjectedField1.f = new Object(); 
    mInjectedField2.f = new Object(); 
  }
  Ownership(Obj obj, Object o) { obj.f = o; }
  native void leakToAnotherThread(Object o);
  public void escapeViaConstructorBad() {
    Obj local = new Obj();
    Ownership constructed = new Ownership(local);
    local.f = new Object();
  }
  public void ownInOneBranchBad(Obj formal, boolean b) {
    if (b) { formal = new Obj(); }
    formal.f = new Object();
  }
  public void reassignToFormalBad(Obj formal) {
    Obj local = new Obj();
    formal.g = local; 
    formal.g.f = new Object(); 
  }
  public void ownedLocalOk() {
    Obj local = new Obj();
    local.f = new Object();
  }
  public Obj returnOwnedLocalOk() {
    Obj local = new Obj();
    local.f = new Object();
    return local;
  }
  public void writeOwnedLocalThenEscapeOk() {
    Obj local = new Obj();
    local.f = new Object();
    leakToAnotherThread(local);
  }
  public void ownInBranchesOk1(boolean b) {
    Obj local;
    if (b) {
      local = new Obj();
      local.f = new Object();
    } else {
      local = new Obj();
      local.f = new Integer(0);
    }
    local.f = new Boolean(false);
  }
  public void ownedAccessPathOk() {
    Obj local = new Obj();
    local.g = new Obj();
    local.g.f = new Object();
  }
  public void aliasOwnedLocalOk() {
    Obj local = new Obj();
    Obj alias = local;
    alias.f = new Object();
    local.f = new Object();
  }
  public void aliasOwnedLocalAccessPathOk() {
    Obj local = new Obj();
    local.g = new Obj();
    Obj alias = local.g;
    alias.f = new Object();
  }
  private void writeToFormal(Obj formal) { formal.f = new Object(); }
  private void callWriteToFormal(Obj formal) { writeToFormal(formal); }
  private void setField(Obj o) { this.field = o; }
  native Obj getMaybeUnownedObj();
  public void writeToNotOwnedInCalleeBad1(Obj o) { writeToFormal(o); }
  public void FN_writeToNotOwnedInCalleeBad2() {
    Obj o = getMaybeUnownedObj();
    writeToFormal(o);
  }
  public void writeToNotOwnedInCalleeBad3(Obj o) { callWriteToFormal(o); }
  public void cantOwnThisBad() { setField(new Obj()); }
  public void writeToOwnedInCalleeOk1() {
    Obj o = new Obj();
    writeToFormal(o);
  }
  public void writeToOwnedInCalleeIndirectOk1() {
    Obj o = new Obj();
    callWriteToFormal(o);
  }
  public void writeToOwnedInCalleeIndirectOk2() {
    Obj o = new Obj();
    o.g = new Obj();
    callWriteToFormal(o.g);
  }
  public Obj ownershipCanBeInterproceduralOk() {
    Obj local = returnOwnedLocalOk();
    local.f = new Object();
    return local;
  }
  public void mutateDoubleReturnOwnedOk() {
    Obj owned = ownershipCanBeInterproceduralOk();
    owned.g = new Obj();
  }
  Obj returnOwnedOrNull(boolean b) {
    if (b) { return null; }
    return new Obj();
  }
  public void mutateAfterNullCheckOK(boolean b) {
    Obj o = returnOwnedOrNull(b);
    if (o != null) { o.f = new Object(); }
  }
  private void mutateIfNotNull(Obj o) { if (o != null) { o.f = new Object(); } }
  public void ownInCalleeViaNullOk() { mutateIfNotNull(null); }
  public void notOwnedInCalleeBad(Obj o) { mutateIfNotNull(o); }
  Obj id(Obj param) { return param; }
  public void passOwnershipInIdFunctionOk() {
    Obj owned = new Obj();
    Obj shouldBeOwned = id(owned);
    shouldBeOwned.f = new Object();
  }
  Obj id2(Obj param) { return id(param); }
  public void passOwnershipInMultiLevelIdFunctionOk() {
    Obj owned = new Obj();
    Obj shouldBeOwned = id2(owned);
    shouldBeOwned.f = new Object();
  }
  native boolean nondet();
  public Obj returnConditionalOwnedInTwoBranches(Obj param) {
    if (nondet()) { return param; }
    return param;
  }
  public void returnConditionalOwnedInTwoBranchesOk() {
    Obj owned = new Obj();
    Obj shouldBeOwned = returnConditionalOwnedInTwoBranches(owned);
    shouldBeOwned.f = new Object();
  }
  public Obj returnOwnedOrConditionalOwned(Obj param) {
    if (nondet()) { return param; } else { return new Obj(); }
  }
  public void ownedAfterCastOk() {
    Object o = new Obj();
    Obj owned = (Obj) o;
    owned.f = new Object();
  }
  public void ownAndConditionalOwnOk() {
    Obj owned = new Obj();
    Obj shouldBeOwned = returnOwnedOrConditionalOwned(owned);
    shouldBeOwned.f = new Object();
  }
  public Obj twoDifferentConditionalOwns(Obj param1, Obj param2) {
    if (nondet()) { return param1; } else { return param2; }
  }
  public void threadLocalOk(ThreadLocal<Obj> threadLocal) { threadLocal.get().f = new Object(); }
  public void twoDifferentConditionalOwnsOk() {
    Obj owned1 = new Obj();
    Obj owned2 = new Obj();
    Obj shouldBeOwned = twoDifferentConditionalOwns(owned1, owned2);
    shouldBeOwned.f = new Object();
  }
  public void FN_escapeThenWriteLocalBad() {
    Obj local = new Obj();
    leakToAnotherThread(local);
    local.f = new Object();
  }
  private Obj leakThenReturn() {
    Obj local = new Obj();
    leakToAnotherThread(local);
    return local;
  }
  public void FN_mutateReturnedBad() {
    Obj notOwned = leakThenReturn();
    notOwned.f = new Object(); 
  }
  private void castThenCall(Obj o) {
    Subclass s = (Subclass) o;
    s.doWrite();
  }
  void castThenCallOk() {
    Obj o = new Obj();
    castThenCall(o);
  }
  void FN_castThenCallBad() {
    Obj o = getMaybeUnownedObj();
    castThenCall(o);
  }
  private Obj castThenReturn(Obj o) {
    Subclass s = (Subclass) o;
    return s;
  }
  void castThenReturnOk() {
    Obj o = new Obj();
    castThenReturn(o).f = new Object();
  }
  void FN_castThenReturnBad() {
    Obj o = getMaybeUnownedObj();
    castThenReturn(o).f = new Object();
  }
  void ownViaReflectionOk1() throws InstantiationException, IllegalAccessException {
    Class<Obj> oClass = Obj.class;
    Obj o = oClass.newInstance();
    o.f = new Object();
  }
  void ownViaReflectionOk2()
      throws IllegalAccessException, InstantiationException, InvocationTargetException,
          NoSuchMethodException {
    Class<Obj> oClass = Obj.class;
    Constructor<Obj> oConstructor = oClass.getConstructor();
    Obj o = oConstructor.newInstance();
    o.f = new Object();
  }
  // void ownInSkippedCodeOk() {
  //   SkippedClass c = SkippedClass.returnOwned();
  //   c.f = new Object();
  // }
  void cloningAquiresOwnershipOk() {
    Ownership ow;
    try {
      ow = (Ownership) this.clone();
      ow.field = null;
    } catch (CloneNotSupportedException e) { }
  }
  static MyObj global;
  void FN_storeInGlobalAndWriteBad() {
    MyObj x = new MyObj();
    synchronized (Ownership.class) { global = x; }
    x.data = 5;
  }
  int readGlobalBad() { return global.data; }
  public void writeOwnedWithExceptionOk() {
    Obj options = returnOwnedWithException();
    options.f = new Object();
  }
  private Obj returnOwnedWithException() {
    Obj options = new Obj();
    if (options.f == null) { throw new IllegalArgumentException(); }
    return options;
  }
  public void notPropagatingOwnershipToAccessPathRootedAtFormalBad(Obj m) { m.g = new Obj(); }
  public void notPropagatingOwnershipToUnownedLocalAccessPathBad() {
    Obj m;
    synchronized (this) { m = field; }
    m.g = new Obj();
  }
  public void propagatingOwnershipToOwnedAccessPathOk() {
    Obj m = new Obj();
    m.g = new Obj();
    m.g.g = new Obj();
    m.g.g.g = new Obj();
  }
  private void reassignParamToOwned(Obj o) {
    o = new Obj();
    o.f = null; 
  }
  Obj unownedField1;
  void reassignParamToOwnedOk() { reassignParamToOwned(this.unownedField1); }
  Obj unownedField2;
  private void reassignParamToUnowned(Obj o) {
    o = this.unownedField2;
    o.f = null; 
  }
  void FN_reassignParamToUnownedBad() { reassignParamToUnowned(new Obj()); }
  void ownedViaLocalAliasOk() {
    Obj owned = new Obj();
    Obj alias = owned;
    alias.f = null;
    owned.f = new Object();
  }
  private void ownedViaParamAlias(Obj o) {
    Obj alias = o;
    alias.f = null; 
    o.f = new Object(); 
  }
  public void ownedViaAliasOk() {
    Obj owned = new Obj();
    ownedViaParamAlias(owned);
  }
  Obj unownedField3;
  private void ownedViaThisAlias() {
    Ownership alias = this;
    alias.unownedField3 = null; 
    this.unownedField3 = new Obj(); 
  }
  public static void ownedViaThisAliasOk() {
    Ownership owned = new Ownership();
    owned.ownedViaThisAlias();
  }
  boolean nondet;
  private void conditionalAlias(Obj o1, Obj o2) {
    Obj alias;
    if (nondet) { alias = o1; } else { alias = o2; }
    alias.f = null; 
  }
  void conditionalAliasOk() { conditionalAlias(new Obj(), new Obj()); }
  void FN_conditionalAliasBad(Obj unowned) { conditionalAlias(new Obj(), unowned); }
}
class MyObj { int data; }
class Subclass extends Obj { public void doWrite() { f = new Object(); } }
@ThreadSafe
class OtherObj {
  private OtherObj(Obj o) { o.f = new Object(); }
  void mutateInConstructorOk() { new OtherObj(new Obj()); }
}
@ThreadSafe
class ContainerOwnership {
  ContainerOwnership() {
    ArrayList<MyObj> children = new ArrayList<MyObj>();
    setFirstOk(children);
  }
  private void setFirstOk(ArrayList<MyObj> children) {
    MyObj obj = children.get(0);
    if (obj == null) { obj = new MyObj(); }
    obj.data = 10;
  }
}