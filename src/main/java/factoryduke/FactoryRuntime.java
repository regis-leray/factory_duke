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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import factoryduke.builder.HookBuilder;
import factoryduke.builder.HookInstanceBuilder;
import factoryduke.builder.InstanceBuilderImpl;
import factoryduke.exceptions.TemplateDuplicateException;
import factoryduke.exceptions.TemplateNotFoundException;
import factoryduke.function.Callback;
import factoryduke.utils.Assert;

class FactoryRuntime implements FactoryContext {

	private Map<String, Template> templates = new ConcurrentHashMap<>();

	private final List<Consumer> afterHooks = new ArrayList<>();

	private final List<Callback> beforeHooks = new ArrayList<>();

	void register(Template template) {
		if (templates.containsKey(template.getIdentifier())) {
			throw new TemplateDuplicateException(String.format("Cannot define duplicate template with the same identifier %s for the class %s", template.getIdentifier(), template.getClazz()));
		}
		templates.put(template.getIdentifier(), template);
	}

	<T> HookBuilder<T> build(String identifier, Consumer<T> override) {
		final Template template = findTemplate(identifier);

		HookInstanceBuilder<T> callback = new HookInstanceBuilder<>(beforeHooks, afterHooks);

		return callback.builder(new InstanceBuilderImpl<T>(template, override){
			public void doAfter(T instance) {
				callback.callAfter(instance);
			}

			public void doBefore() {
				callback.callBefore();
			}
		});
	}

	private Template findTemplate(String identifier) {
		return templates.computeIfAbsent(identifier, o -> {
			throw new TemplateNotFoundException("No builder register with identifier : " + identifier + " with bloc initialization");
		});
	}

	FactoryContext load(String... packages) {
		reset();
		new FactoriesLoader(packages).load();
		return this;
	}

	void reset() {
		this.templates.clear();
		this.afterHooks.clear();
		this.beforeHooks.clear();
	}

	@Override
	public FactoryContext addBeforeHook(Callback hook) {
		Assert.that().notNull(hook);
		this.beforeHooks.add(hook);
		return this;
	}

	@Override
	public FactoryContext addAfterHook(Consumer hook) {
		Assert.that().notNull(hook);
		this.afterHooks.add(hook);
		return this;
	}

	Map getTemplates() {
		return Collections.unmodifiableMap(this.templates);
	}
}
