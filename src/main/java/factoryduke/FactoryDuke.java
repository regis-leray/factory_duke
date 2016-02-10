package factoryduke;

import java.util.function.Consumer;
import java.util.function.Supplier;

import factoryduke.builder.HookBuilder;

public class FactoryDuke {
	private FactoryDuke() {
	}

	public static <T> void define(Class<T> clazz, Supplier<T> builder) {
		define(clazz, clazz.getCanonicalName(), builder);
	}

	public static <T> void define(Class<T> clazz, String identifier, Supplier<T> builder) {
		FactoryRuntimeHolder.getRuntime().register(new SupplyTemplate<>(clazz, identifier, builder));
	}

	public static <T> void define(Class<T> clazz, Consumer<T> builder) {
		define(clazz, clazz.getCanonicalName(), builder);
	}

	public static <T> void define(Class<T> clazz, String identifier, Consumer<T> builder) {
		FactoryRuntimeHolder.getRuntime().register(new ConsumerTemplate(clazz, identifier, builder));
	}

	public static <T> HookBuilder<T> build(Class<T> clazz) {
		return build(clazz, clazz.getCanonicalName());
	}

	public static <T> HookBuilder<T> build(Class<T> clazz, String identifier) {
		return build(clazz, identifier, o -> {
		});
	}

	public static <T> HookBuilder<T> build(Class<T> clazz, Consumer<T> override) {
		return build(clazz, clazz.getCanonicalName(), override);
	}

	public static <T> HookBuilder<T> build(Class<T> clazz, String identifier, Consumer<T> override) {
		return FactoryRuntimeHolder.getRuntime().build(identifier, override);
	}

	public static FactoryContext load() {
		return load(new String[0]);
	}

	public static FactoryContext load(String... packages) {
		return FactoryRuntimeHolder.getRuntime().load(packages);
	}

	public static void reset() {
		FactoryRuntimeHolder.getRuntime().reset();
	}
}
