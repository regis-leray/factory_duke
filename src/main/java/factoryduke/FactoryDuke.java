package factoryduke;


import java.util.function.Consumer;
import java.util.function.Supplier;

public class FactoryDuke {
	private FactoryDuke() {
	}

	public static <T> void define(Class<T> clazz, Supplier<T> builder) {
		define(clazz, clazz.getCanonicalName(), builder);
	}

	public static <T> void define(Class<T> clazz, String identifier,  Supplier<T> builder) {
		FactoryRuntime.getRuntime().register(clazz, identifier, builder);
	}

	public static <T> void define(Class<T> clazz, Consumer<T> builder) {
		define(clazz, clazz.getCanonicalName(), builder);
	}

	public static <T> void define(Class<T> clazz, String identifier, Consumer<T> builder) {
		FactoryRuntime.getRuntime().register(clazz, identifier, builder);
	}

	public static <T> T build(Class<T> clazz) {
		return build(clazz, clazz.getCanonicalName());
	}

	public static <T> T build(Class<T> clazz, Consumer<T> override) {
		return build(clazz, clazz.getCanonicalName(), override);
	}

	public static <T> T build(Class<T> clazz, String identifier) {
		return build(clazz, identifier, o -> {});
	}

	public static <T> T build(Class<T> clazz, String identifier, Consumer<T> override) {
		return FactoryRuntime.getRuntime().build(clazz, identifier, override);
	}

	public static void load() {
		FactoryRuntime.getRuntime().load();
	}

	public static void reset() {
		FactoryRuntime.getRuntime().reset();
	}
}
