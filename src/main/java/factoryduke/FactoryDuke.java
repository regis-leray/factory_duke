/**
 * The MIT License
 * Copyright (c) 2016 Regis Leray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
