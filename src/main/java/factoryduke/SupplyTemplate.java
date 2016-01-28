package factoryduke;

import java.util.function.Supplier;

public class SupplyTemplate<T> extends Template {

	private Supplier<T> supplier;

	public SupplyTemplate(Class<T> clazz, String identifier, Supplier<T> supplier) {
		super(clazz, identifier);
		this.supplier = supplier;
	}

	public T create() {
		return supplier.get();
	}
}
