import nullnessAnnotations.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
public class NullableFuture {
    public void getNow() {
        CompletableFuture<Object> future = new CompletableFuture<>();
        future.getNow(null);
    }
    public void completeOnTimeout() {
        CompletableFuture<Object> future = new CompletableFuture<>();
        future.completeOnTimeout(null, 1L, TimeUnit.MINUTES);
    }
    public void obtrudeValue() {
        CompletableFuture<Object> future = new CompletableFuture<>();
        future.obtrudeValue(null);
    }
}