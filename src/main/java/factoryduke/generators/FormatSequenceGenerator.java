package factoryduke.generators;

import java.util.concurrent.atomic.AtomicInteger;

public final class FormatSequenceGenerator implements Generator<String> {

	private String message;

	private AtomicInteger counter = new AtomicInteger(1);

	private int incrementingBy = 1;

	FormatSequenceGenerator() {
		this("%s");
	}

	private FormatSequenceGenerator(String message) {
		this.message = message;
	}

	public FormatSequenceGenerator withMessage(String message) {
		this.message = message;
		return this;
	}

	public FormatSequenceGenerator startingAt(int startingAt) {
		counter.set(startingAt);
		return this;
	}

	public FormatSequenceGenerator incrementingBy(int incrementingBy) {
		this.incrementingBy = incrementingBy;
		return this;
	}

	@Override
	public String nextValue() {
		return String.format(message, counter.getAndAdd(incrementingBy));
	}
}
