package factoryduke;

import java.util.function.Consumer;

import factoryduke.exceptions.TemplateInstanciationException;

public class ConsumerTemplate extends Template {
	private Consumer consumer;

	public <T> ConsumerTemplate(Class<T> clazz, String identifier,  Consumer<T> consumer) {
		super(clazz, identifier);
		this.consumer = consumer;
	}

	@Override
	public <T> T create() {
		try {
			T instance = (T) getClazz().newInstance();
			consumer.accept(instance);
			return instance;
		} catch (ClassCastException | InstantiationException | IllegalAccessException e) {
			throw new TemplateInstanciationException(e);
		}
	}
}
