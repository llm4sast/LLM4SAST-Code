import singletons.*;
class NotSynchronizedSingleton {
    private static NotSynchronizedSingleton instance;
    private NotSynchronizedSingleton() { }
    public static NotSynchronizedSingleton getInstance() { 
      if (instance == null) { instance = new NotSynchronizedSingleton(); }
      return instance;
    }
}