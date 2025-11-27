import java.util.ArrayList;
import java.util.Map;
class HoistExpensive {
  int incr(int x) { return x + 1; }
  void cheap_dont_hoist(int size) {
    int x = 10;
    for (int i = 0; i < size; i++) { incr(x); }
  }
  void symbolic_expensive_hoist(int size) { for (int i = 0; i < size; i++) { cheap_dont_hoist(size); } }
  void instantiated_cheap_dont_hoist(int size) { for (int i = 0; i < size; i++) { cheap_dont_hoist(1); } }
  void cheap_iterator_dont_hoist(ArrayList<Integer> list) {
    int x = 0;
    for (Integer elem : list) { incr(x); }
  }
  void symbolic_expensive_iterator_hoist(int size, ArrayList<Integer> list) { for (int i = 0; i < size; i++) { cheap_iterator_dont_hoist(list); } }
  private Map<String, Foo> mLeakObjectResults;
  class Foo { String className; }
  public String getLeakSummary() {
    StringBuilder leakedObjectSB = new StringBuilder();
    for (String key : mLeakObjectResults.keySet()) {
      leakedObjectSB
          .append(key)
          .append(",")
          .append(mLeakObjectResults.get(key).className)
          .append("\n");
    }
    return leakedObjectSB.toString();
  }
}