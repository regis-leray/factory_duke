package factoryduke.generators;

import factoryduke.utils.Assert;

public final class Generators {
	private Generators() {
	}

	public static SequenceGenerator sequence() {
		return new SequenceGenerator();
	}

	public static StringSequenceGenerator stringSequence(String prefix) {
		Assert.that().notNull(prefix, "prefix may not be null");
		return new StringSequenceGenerator(prefix);
	}

	public static DateGenerator dateSequence() {
		return new DateGenerator();
	}

	public static <T> Generator<T> constant(final T constant) {
		return values(constant);
	}

	public static <T> SequenceValuesGenerator<T> values(T...values){
		return new SequenceValuesGenerator().withValues(values);
	}

	public static <T> RandomValuesGenerator<T> random(T...values){
		return new RandomValuesGenerator<>().withValues(values);
	}
}
