import javax.annotation.concurrent.ThreadSafe;
@ThreadSafe
class Parent {}
@ThreadSafe
class Child extends Parent {}
@ThreadSafe
class Arrays {
  Child[] childArr = new Child[5];
  Parent[] parentArr = childArr; 
  final String[] strArr1 = new String[5];
  final String[] strArr2 = new String[5];
  void arrayParameterWriteBad(int[] name1) { name1[2] = 4; }
  int FN_arrayParameterReadBad(int[] name2) { return name2[2]; }
  int arrayParameterLiteralReadOk() { return (new int[] {2, 3})[1]; }
  public void writeWriteRaceBad(String s) { strArr1[2] = s; }
  public String readWriteRaceBad(String s) {
    synchronized (this) { strArr1[2] = s; }
    return strArr1[2];
  }
  public String notReadWriteRace1Ok(String s) {
    synchronized (this) { strArr1[0] = s; }
    return strArr2[0];
  }
  public Child FN_readWriteAliasRaceBad() {
    synchronized (this) { parentArr[3] = null; }
    return childArr[3];
  }
  String[] type1Arr[];
  Parent[] type2Arr;
  public Parent noRaceOk() {
    synchronized (this) { type1Arr[3] = null; }
    return type2Arr[3];
  }
}