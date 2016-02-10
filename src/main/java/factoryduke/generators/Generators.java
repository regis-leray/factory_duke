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
package factoryduke.generators;

import factoryduke.utils.Assert;

public final class Generators {
	private Generators() {
	}

	public static SequenceGenerator sequence() {
		return new SequenceGenerator();
	}

	public static StringSequenceGenerator stringSequence(String prefix) {
		Assert.that().notNull(prefix, "prefix may not be null");
		return new StringSequenceGenerator(prefix);
	}

	public static DateGenerator dateSequence() {
		return new DateGenerator();
	}

	public static <T> Generator<T> constant(final T constant) {
		return values(constant);
	}

	public static <T> SequenceValuesGenerator<T> values(T...values){
		return new SequenceValuesGenerator().withValues(values);
	}

	public static <T> RandomValuesGenerator<T> random(T...values){
		return new RandomValuesGenerator<>().withValues(values);
	}
}
