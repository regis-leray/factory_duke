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

public class FormatSequenceGeneratorTest {

	@Test
	public void default_generator() {
		FormatSequenceGenerator generator = new FormatSequenceGenerator();

		assertThat(generator.nextValue()).isEqualTo("1");
		assertThat(generator.nextValue()).isEqualTo("2");
		assertThat(generator.nextValue()).isEqualTo("3");
	}

	@Test
	public void custom_message() {
		FormatSequenceGenerator generator = new FormatSequenceGenerator().withMessage("test%s@gmail.com");

		assertThat(generator.nextValue()).isEqualTo("test1@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test2@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test3@gmail.com");
	}

	@Test
	public void increment_by_2() {
		FormatSequenceGenerator generator = new FormatSequenceGenerator().withMessage("test%s@gmail.com").incrementingBy(2);

		assertThat(generator.nextValue()).isEqualTo("test1@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test3@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test5@gmail.com");
	}

	@Test
	public void start_at_2() {
		FormatSequenceGenerator generator = new FormatSequenceGenerator().withMessage("test%s@gmail.com").startingAt(2);

		assertThat(generator.nextValue()).isEqualTo("test2@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test3@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test4@gmail.com");
	}

}
