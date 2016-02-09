package factoryduke;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import factoryduke.exceptions.FactoryLoaderException;
import factoryduke.exceptions.FactoryNotFoundException;
import factoryduke.utils.Assert;

public class FactoriesLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(FactoriesLoader.class);

	public static final String DEFAULT_FACTORY = "Factories";

	public static final String DEFAULT_PACKAGE = "factories";

	private final FactoryScanner scanner;

	private final boolean skipDefaultBehavior;

	public FactoriesLoader(String... packages) {
		Assert.that().notNull(packages, "packages cannot be null");
		final boolean skipDefaultFactory = packages.length > 0;
		this.skipDefaultBehavior = packages.length > 0;
		this.scanner = new FactoryScanner()
				.withInterfaces(TFactory.class)
				.withPackages(skipDefaultFactory ? packages : new String[]{DEFAULT_PACKAGE});
	}

	public void load() {
		if(!skipDefaultBehavior) {
			doLoadSafe(DEFAULT_FACTORY);
		}

		final List<String> founded = scanner.scan();

		for (String factoryClass : founded) {
			doLoad(factoryClass);
		}
	}

	private void doLoadSafe(String className) {
		try {
			doLoad(className);
		} catch (FactoryNotFoundException e) {
			LOGGER.debug("Cannot found any default 'Factories' in the current classpath ");
		}
	}

	private void doLoad(String className) {
		try {
			((TFactory) Class.forName(className).newInstance()).define();
		} catch (ClassNotFoundException e) {
			throw new FactoryNotFoundException(e);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new FactoryLoaderException(e);
		}
	}
}
