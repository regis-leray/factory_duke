package factoryduke;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import factoryduke.exceptions.FactoryLoaderException;
import factoryduke.exceptions.FactoryNotFoundException;
import factoryduke.utils.ReflectionUtils;

public class FactoriesLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(FactoriesLoader.class);

	public static final String DEFAULT_FACTORY = "Factories";

	public static final String DEFAULT_PACKAGE = "factories";

	private final FactoryScanner scanner;

	public FactoriesLoader() {
		this(new String[0]);
	}

	public FactoriesLoader(String... packages) {
		this(new FactoryScanner()
				.withPackages(DEFAULT_PACKAGE)
				.withInterfaces(IFactory.class)
				.withAnnotations(Factory.class)
				.addPackages(packages));
	}

	public FactoriesLoader(FactoryScanner factoryScanner) {
		this.scanner = factoryScanner;
	}

	public void load() {
		doLoadSafe(DEFAULT_FACTORY);

		for (String factoryClass : scanner.scan()) {
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
			final Object factory = Class.forName(className).newInstance();
			if (factory instanceof IFactory) {
				((IFactory) factory).define();
			} else {
				callDefine(factory);
			}
		} catch (ClassNotFoundException e) {
			throw new FactoryNotFoundException(e);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new FactoryLoaderException(e);
		}
	}

	private void callDefine(Object factory) throws InvocationTargetException, IllegalAccessException {
		final List<Method> methods = ReflectionUtils.getMethodsAnnotatedWith(factory.getClass(), Factory.Define.class);

		if (methods.size() == 0) {
			throw new IllegalStateException(String.format("The factory %s is missing the %s annotation ", factory.getClass().getCanonicalName(), Factory.Define.class.getCanonicalName()));
		}
		if (methods.size() > 1) {
			throw new IllegalStateException(String.format("The factory %s have multiple %s annotation. Please provide only one method.", factory.getClass().getCanonicalName(), Factory.Define.class.getCanonicalName()));
		}

		methods.get(0).invoke(factory);
	}
}
