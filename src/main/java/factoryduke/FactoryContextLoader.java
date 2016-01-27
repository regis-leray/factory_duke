package factoryduke;

import factoryduke.utils.ObjectUtils;

public class FactoryContextLoader {

	private final FactoryScanner scanner;

	public FactoryContextLoader() {
		this(new FactoryScanner());
	}

	public FactoryContextLoader(FactoryScanner scanner) {
		this.scanner = scanner.withInterfaces(FactoryAware.class);
	}

	public FactoryContext load() {
			return ObjectUtils.instanciate(scanner.scanOne(), (FactoryAware) () -> new FactoryContext()).preInit();
	}
}
