import singletons.*;
class IndirectlyCloneableSingleton extends CloneableClass {
    private static IndirectlyCloneableSingleton instance;
    private IndirectlyCloneableSingleton() { }
    public static synchronized IndirectlyCloneableSingleton getInstance() {
      if (instance == null) { instance = new IndirectlyCloneableSingleton(); }
      return instance;
    }
}