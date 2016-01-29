package factoryduke.generators;

import java.util.Collections;

public class RandomValuesGenerator<T> extends SequenceValuesGenerator<T> {

	@Override
	public T nextValue() {
		final T value = super.nextValue();

		if(isloopFinished()){
			Collections.shuffle(actual);
		}

		return value;
	}

	@Override
	public RandomValuesGenerator withValues(T... values) {
		super.withValues(values);
		Collections.shuffle(this.actual);
		return this;
	}
}
