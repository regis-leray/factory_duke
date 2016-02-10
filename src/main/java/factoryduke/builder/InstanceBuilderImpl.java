package factoryduke.builder;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import factoryduke.Template;
import factoryduke.function.Callback;

public class InstanceBuilderImpl<T> implements InstanceBuilder<T>, InstanceBuilder.CollectionBuilder<T> {

	private final Template template;

	private final Consumer<T> override;

	private int times;

	public InstanceBuilderImpl(Template template, Consumer<T> override) {
		this.template = template;
		this.override = override;
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
		doBefore();

		final T instance = template.create();
		override.accept(instance);

		doAfter(instance);

		return instance;
	}

	public void doAfter(T instance) {
	}

	public void doBefore() {
	}
}
