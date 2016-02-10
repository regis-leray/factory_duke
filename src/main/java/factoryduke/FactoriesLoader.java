/**
 * The MIT License
 * Copyright (c) 2016 Regis Leray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
