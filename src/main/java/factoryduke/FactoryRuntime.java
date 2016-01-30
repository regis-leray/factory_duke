package factoryduke;


import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import factoryduke.exceptions.TemplateDuplicateException;
import factoryduke.exceptions.TemplateNotFoundException;

class FactoryRuntime {

	private Map<String, Template> templates = new ConcurrentHashMap<>();

	void register(Template template) {

		if (templates.containsKey(template.getIdentifier())) {
			throw new TemplateDuplicateException(String.format("Cannot define duplicate template with the same identifier %s for the class %s", template.getIdentifier(), template.getClazz()));
		}
		templates.put(template.getIdentifier(), template);
	}

	<T> Repeat<T> build(String identifier, Consumer<T> override) {
		final Template template = findTemplate(identifier);
		return new Repeat<>(template, override);
	}

	private Template findTemplate(String identifier) {
		return templates.computeIfAbsent(identifier, o -> {
			throw new TemplateNotFoundException("No builder register with identifier : " + identifier + " with bloc initialization");
		});
	}

	void load(String... packages) {
		reset();
		new FactoriesLoader(packages).load();
	}

	void reset() {
		templates.clear();
	}

	Map getTemplates() {
		return Collections.unmodifiableMap(this.templates);
	}
}
