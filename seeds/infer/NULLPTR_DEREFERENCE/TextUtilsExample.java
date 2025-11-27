import android.text.TextUtils;
class TextUtilsExample {
  public void testTextUtilsIsEmptyBad(String s) {
    if (TextUtils.isEmpty(s)) {
      Object o = null;
      o.toString();
    }
  }
  public void testTextUtilsIsEmptyBad() {
    String s = "#@%^&%";
    if (!TextUtils.isEmpty(s)) {
      Object o = null;
      o.toString();
    }
  }
  public void testTextUtilsIsEmptyEmptyStrBad() {
    if (TextUtils.isEmpty("")) {
      Object o = null;
      o.toString();
    }
  }
  public void testTextUtilsIsEmptyNullBad() {
    String s = null;
    if (TextUtils.isEmpty(s)) {
      Object o = null;
      o.toString();
    }
  }
}