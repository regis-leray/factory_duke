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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public class FactoryScanner {

	private String[] packages = new String[0];

	private Class[] interfaces = new Class[0];

	public FactoryScanner withPackages(String... packages) {
		this.packages = packages;
		return this;
	}

	public FactoryScanner withInterfaces(Class... interfaces) {
		this.interfaces = interfaces;
		return this;
	}

	public List<String> scan() {
		final FastClasspathScanner scanner = new FastClasspathScanner(packages).scan();
		List<String> matches = new ArrayList<>();
		for (Class clazz : interfaces) {
			matches.addAll(scanner.getNamesOfClassesImplementing(clazz));
		}
		return matches;
	}
}
