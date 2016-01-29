package factoryduke;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class Repeat<T> {

	private int times;

	private Consumer<T> override;

	private final Template template;

	Repeat(Template template, Consumer<T> override) {
		this.template = template;
		this.override = override;
	}

	public Repeat<T> times(int times) {
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

		for (int i = 0; i < times; i++) {
			final T intance = template.create();
			override.accept(intance);
			list.add(intance);
		}
		return list;
	}

}
