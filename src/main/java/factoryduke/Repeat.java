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

	public T toOne(){
		return times(1).toList().get(0);
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
			list.add(createInstance());
		}
		return list;
	}

	private T createInstance() {
		final T intance = template.create();
		override.accept(intance);
		return intance;
	}

}
