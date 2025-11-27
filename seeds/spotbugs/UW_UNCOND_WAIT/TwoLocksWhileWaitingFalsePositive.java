class TwoLocksWhileWaitingFalsePositive {
    synchronized void clueless() throws Exception {
        synchronized (this) { this.wait(); }
    }
}