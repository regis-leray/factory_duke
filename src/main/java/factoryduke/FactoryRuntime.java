package factoryduke;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import factoryduke.exceptions.TemplateDuplicateException;
import factoryduke.exceptions.TemplateNotFoundException;

public class FactoryRuntime {

	private static final FactoryRuntime instance = new FactoryRuntime();

	private final Map<String, Template> templates = new HashMap<>();

	private final FactoryContext config;

	private FactoryRuntime() {
		config = new FactoryContextLoader().load();
	}

	public static FactoryRuntime getRuntime() {
		return instance;
	}

	void register(Template template) {

		if (templates.containsKey(template.getIdentifier())) {
			throw new TemplateDuplicateException(String.format("Cannot define duplicate template with the same identifier %s for the class %s", template.getIdentifier(), template.getClazz()));
		}
		templates.put(template.getIdentifier(), template);
	}

	<T> T build(String identifier, Consumer<T> override) {
		T instance = templates.computeIfAbsent(identifier, o -> {
			throw new TemplateNotFoundException("No builder register with identifier : " + identifier + " with bloc initialization");
		}).create();

		override.accept(instance);
		return instance;
	}

	public void load() {
		reset();
		new FactoriesLoader(config.getPackages()).load();
	}

	public void reset() {
		templates.clear();
	}

	Map getTemplates() {
		return Collections.unmodifiableMap(this.templates);
	}
}
