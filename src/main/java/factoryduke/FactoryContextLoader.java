package factoryduke;

import factoryduke.utils.ObjectUtils;

public class FactoryContextLoader {

	private final FactoryScanner scanner;

	public FactoryContextLoader() {
		this(new FactoryScanner().withInterfaces(FactoryAware.class));
	}

	public FactoryContextLoader(FactoryScanner scanner) {
		this.scanner = scanner;
	}

	public FactoryContext load() {
			return ObjectUtils.instanciate(scanner.scanOne(), (FactoryAware) () -> new FactoryContext()).preInit();
	}
}
