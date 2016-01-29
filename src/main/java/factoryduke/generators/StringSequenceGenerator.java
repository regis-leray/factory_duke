package factoryduke.generators;

import factoryduke.utils.Assert;

public final class StringSequenceGenerator implements Generator<String> {
	private String prefix;
	private long next;
	private int increment;


	private int paddedNumberLength;

	StringSequenceGenerator(String prefix) {
		this(prefix, 1L, 1, 0);
	}

	private StringSequenceGenerator(String prefix, long next, int increment, int paddedNumberLength) {
		this.prefix = prefix;
		this.next = next;
		this.increment = increment;
		this.paddedNumberLength = paddedNumberLength;
	}


	public StringSequenceGenerator withLeftPadding(int paddedNumberLength) {
		Assert.that().isTrue(paddedNumberLength > 0, "paddedNumberLength must be > 0");
		this.paddedNumberLength = paddedNumberLength;
		return this;
	}


	public StringSequenceGenerator withoutLeftPadding() {
		this.paddedNumberLength = 0;
		return this;
	}


	public StringSequenceGenerator startingAt(long start) {
		this.next = start;
		return this;
	}


	public StringSequenceGenerator incrementingBy(int increment) {
		this.increment = increment;
		return this;
	}

	@Override
	public String nextValue() {
		long number = next;
		next += increment;
		return prefix + leftPadIfNecessary(number);
	}

	private String leftPadIfNecessary(long number) {
		String numberAsString = Long.toString(number);
		if (numberAsString.length() >= paddedNumberLength) {
			return numberAsString;
		}
		StringBuilder builder = new StringBuilder(paddedNumberLength);
		for (int i = 0; i < paddedNumberLength - numberAsString.length(); i++) {
			builder.append('0');
		}
		return builder.append(numberAsString).toString();
	}

	@Override
	public String toString() {
		return "StringSequenceGenerator["
				+ "prefix='" + prefix + '\''
				+ ", next=" + next
				+ ", increment=" + increment
				+ ", paddedNumberLength=" + paddedNumberLength
				+ "]";
	}
}
