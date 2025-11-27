pub const fn elts<T>(bits: usize) -> usize {
	let width = mem::size_of::<T>() * 8;
	if width == 0 {
		return 0;
	}
	bits / width + (bits % width != 0) as usize
}