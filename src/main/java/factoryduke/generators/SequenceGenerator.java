package factoryduke.generators;


public final class SequenceGenerator implements Generator<Long> {

	private long next = 1L;
	private int increment = 1;

	SequenceGenerator() {
		this(1, 1);
	}

	private SequenceGenerator(long start, int increment) {
		this.next = start;
		this.increment = increment;
	}

	/**
	 * Restarts the sequence at the given value
	 * @param start the starting value of the created generator
	 * @return this instance, for chaining
	 */
	public SequenceGenerator startingAt(long start) {
		this.next = start;
		return this;
	}

	/**
	 * Increments the value by the given increment.
	 * @return this instance, for chaining
	 */
	public SequenceGenerator incrementingBy(int increment) {
		this.increment = increment;
		return this;
	}

	@Override
	public Long nextValue() {
		long result = next;
		next += increment;
		return result;
	}

	@Override
	public String toString() {
		return "SequenceGenerator["
				+ "next="
				+ next
				+ ", increment="
				+ increment
				+ "]";
	}
}
