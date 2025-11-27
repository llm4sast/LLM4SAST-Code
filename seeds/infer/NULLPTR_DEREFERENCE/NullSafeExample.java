import com.facebook.infer.annotation.Nullsafe;
class OtherClass {
  OtherClass x = null;
  OtherClass canReturnNull() { return this.x; }
  String buggyMethodBad() {
    OtherClass o = new OtherClass();
    return o.canReturnNull().toString();
  }
}
@Nullsafe(Nullsafe.Mode.LOCAL)
class NullsafeExampleLocal {
  void testingNullsafeLocalMode() {
    OtherClass o = new OtherClass();
    o = o.canReturnNull();
    o.getClass();
  }
}
@Nullsafe(Nullsafe.Mode.STRICT)
class NullsafeExampleStrict {
  void testingNullsafeStrictMode() {
    OtherClass o = new OtherClass();
    o = o.canReturnNull();
    o.toString();
  }
}