import singletons.*;
class DefaultConstructor {
    private static DefaultConstructor instance;
    public static synchronized DefaultConstructor getInstance() {
      if (instance == null) { instance = new DefaultConstructor(); }
      return instance;
    }
}