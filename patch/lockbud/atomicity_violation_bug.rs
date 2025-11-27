pub fn get_and_reset_text_shaping_performance_counter() -> usize {
    let value = TEXT_SHAPING_PERFORMANCE_COUNTER.load(Ordering::SeqCst);
    TEXT_SHAPING_PERFORMANCE_COUNTER.store(0, Ordering::SeqCst);
    value
}