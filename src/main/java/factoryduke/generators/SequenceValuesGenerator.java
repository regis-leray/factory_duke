package factoryduke.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import factoryduke.utils.Assert;

public class SequenceValuesGenerator<T> implements Generator<T> {

	protected List<T> actual;

	private AtomicInteger counter;

	SequenceValuesGenerator(){
		this(new ArrayList<>());
	}

	private SequenceValuesGenerator(List<T> values) {
		this.actual = values;
		counter = new AtomicInteger();
	}

	public SequenceValuesGenerator withValues(T... values){
		Assert.that().notNull(values).isTrue(values.length > 0);

		for(T v : values){
			this.actual.add(v);
		}

		return this;
	}

	@Override
	public T nextValue() {
		T value = actual.isEmpty() ? null : actual.get(counter.getAndIncrement());

		if(isloopFinished()){
			counter = new AtomicInteger();
		}

		return value;
	}

	protected boolean isloopFinished() {
		return actual.size() == counter.intValue();
	}
}
