package factoryduke;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Repeat<T> implements InstanceBuilder<T>, InstanceBuilder.CollectionBuilder<T> {

	private final Template template;

	private final Consumer<T> override;

	private final List<Consumer> callbacks;

	private int times;

	Repeat(Template template, Consumer<T> override, List<Consumer> callbacks) {
		this.template = template;
		this.override = override;
		this.callbacks = callbacks;
	}

	public T toOne() {
		return times(1).toList().get(0);
	}

	public CollectionBuilder<T> times(int times) {
		this.times = times;
		return this;
	}

	public List<T> toList() {
		return callTemplate();
	}

	public Set<T> toSet() {
		final HashSet<T> set = new HashSet<>();
		set.addAll(callTemplate());
		return set;
	}

	private List<T> callTemplate() {
		List<T> list = new ArrayList<>(times);
		AtomicInteger counter = new AtomicInteger(times);

		while (counter.getAndDecrement() > 0) {
			list.add(createInstance());
		}
		return list;
	}

	private T createInstance() {
		final T intance = template.create();
		override.accept(intance);
		callbacks.stream().forEach(c -> c.accept(intance));
		return intance;
	}

}
