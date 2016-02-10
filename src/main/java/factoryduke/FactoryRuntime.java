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
