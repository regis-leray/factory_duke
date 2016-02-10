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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RandomValuesGeneratorTest {

	@Test
	public void with_1_element() {
		RandomValuesGenerator<String> random = Generators.random("1");
		assertThat(random.nextValue()).isEqualTo("1");
	}

	@Test
	public void with_10_elements() {
		String[] elements = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		String[] valuesGenerated = new String[elements.length];

		RandomValuesGenerator<String> random = Generators.random(elements);

		for (int i = 0; i < elements.length; i++) {
			valuesGenerated[i] = random.nextValue();
		}

		assertThat(elements).containsOnly(valuesGenerated);

	}


}


