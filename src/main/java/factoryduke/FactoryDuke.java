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
		FactoryRuntime.getRuntime().register(new SupplyTemplate<>(clazz, identifier, builder));
	}

	public static <T> void define(Class<T> clazz, Consumer<T> builder) {
		define(clazz, clazz.getCanonicalName(), builder);
	}

	public static <T> void define(Class<T> clazz, String identifier, Consumer<T> builder) {
		FactoryRuntime.getRuntime().register(new ConsumerTemplate(clazz, identifier, builder));
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
		return FactoryRuntime.getRuntime().build(identifier, override).toOne();
	}

	public static <T> Repeat<T> repeat(Class<T> clazz){
		return repeat(clazz, clazz.getCanonicalName());
	}

	public static <T> Repeat<T> repeat(Class<T> clazz, String identifier) {
		return repeat(clazz, identifier, o -> {});
	}

	public static <T> Repeat<T> repeat(Class<T> clazz, Consumer<T> override) {
		return repeat(clazz, clazz.getCanonicalName(), override);
	}

	public static <T> Repeat<T> repeat(Class<T> clazz, String identifier, Consumer<T> override) {
		return FactoryRuntime.getRuntime().build(identifier, override);
	}

	public static void load() {
		load(new String[0]);
	}

	public static void load(String... packages) {
		FactoryRuntime.getRuntime().load(packages);
	}

	public static void reset() {
		FactoryRuntime.getRuntime().reset();
	}
}
