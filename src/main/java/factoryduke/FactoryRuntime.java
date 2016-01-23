package factoryduke;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import factoryduke.utils.Assert;

public class FactoryRuntime {

	private static final FactoryRuntime instance = new FactoryRuntime();

	private final Map<String, Object> map = new HashMap<>();

	private FactoryContext config;

	private FactoryRuntime(){
		config = new FactoryContextLoader().load();
	}

	public static FactoryRuntime getRuntime() {
		return instance;
	}

	<T> void register(Class<T> clazz, String identifier, Object builder) {
		Assert.that().isTrue(builder instanceof Supplier || builder instanceof Consumer, "Please provide a Consumer or Supplier instance");

		if (map.containsKey(identifier)) {
			throw new IllegalStateException(String.format("Cannot define duplicate template with the same identifier %s for the class %s", identifier, clazz.getCanonicalName()));
		}

		map.put(identifier, builder);
	}

	<T> T build(Class<T> clazz, String identifier, Consumer<T> override) {
		final Object builder = map.computeIfAbsent(identifier, o -> {
			throw new IllegalStateException("No builder register with identifier : " + identifier + " with bloc initialization");
		});

		try {
			T instance = null;

			if (builder instanceof Consumer) {
				instance = clazz.newInstance();
				((Consumer<T>) builder).accept(instance);
			} else {
				instance = ((Supplier<T>) builder).get();
			}

			override.accept(instance);
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void load() {
		new FactoriesLoader(config.getPackages()).load();
	}

	public void reset() {
		map.clear();
	}
}
