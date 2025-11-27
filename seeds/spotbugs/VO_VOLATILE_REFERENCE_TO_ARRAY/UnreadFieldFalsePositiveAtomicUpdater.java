import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
public class UnreadFieldFalsePositiveAtomicUpdater {
    volatile Object[] _newkvs;
    private final AtomicReferenceFieldUpdater<UnreadFieldFalsePositiveAtomicUpdater, Object[]> _newkvsUpdater = AtomicReferenceFieldUpdater
            .newUpdater(UnreadFieldFalsePositiveAtomicUpdater.class, Object[].class, "_newkvs");
    boolean CAS_newkvs(Object[] newkvs) {
        while (_newkvs == null)
            if (_newkvsUpdater.compareAndSet(this, (Object[]) null, newkvs))
                return true;
        return false;
    }
}