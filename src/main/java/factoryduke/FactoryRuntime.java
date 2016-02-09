package factoryduke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import factoryduke.exceptions.TemplateDuplicateException;
import factoryduke.exceptions.TemplateNotFoundException;
import factoryduke.utils.Assert;

class FactoryRuntime implements FactoryContext {

	private Map<String, Template> templates = new ConcurrentHashMap<>();

	private List<Consumer> globalCallbacks = new ArrayList<>();

	void register(Template template) {
		if (templates.containsKey(template.getIdentifier())) {
			throw new TemplateDuplicateException(String.format("Cannot define duplicate template with the same identifier %s for the class %s", template.getIdentifier(), template.getClazz()));
		}

		templates.put(template.getIdentifier(), template);
	}

	<T> InstanceBuilder<T> build(String identifier, Consumer<T> override) {
		final Template template = findTemplate(identifier);
		return new InstanceBuilderImpl<>(template, override, globalCallbacks);
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
		templates.clear();
		this.globalCallbacks = new ArrayList<>();
	}

	@Override
	public void registerGlobalCallbacks(Consumer... callbacks){
		Assert.that().notNull(callbacks);
		this.globalCallbacks = Arrays.asList(callbacks);
	}

	Map getTemplates() {
		return Collections.unmodifiableMap(this.templates);
	}
}
