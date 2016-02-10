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


public class StringSequenceGeneratorTest {

	@Test
	public void default_with_int(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-");

		assertThat(generator.nextValue()).isEqualTo("myprefix-1");
		assertThat(generator.nextValue()).isEqualTo("myprefix-2");
	}

	@Test
	public void start_at_2(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-").startingAt(2);

		assertThat(generator.nextValue()).isEqualTo("myprefix-2");
		assertThat(generator.nextValue()).isEqualTo("myprefix-3");
	}

	@Test
	public void increment_by_2(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-").incrementingBy(2);

		assertThat(generator.nextValue()).isEqualTo("myprefix-1");
		assertThat(generator.nextValue()).isEqualTo("myprefix-3");
	}

	@Test
	public void increment_by_2_and_start_at_2(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-").startingAt(2).incrementingBy(2);

		assertThat(generator.nextValue()).isEqualTo("myprefix-2");
		assertThat(generator.nextValue()).isEqualTo("myprefix-4");
	}

	@Test
	public void left_padding_at_4(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-").withLeftPadding(4);

		assertThat(generator.nextValue()).isEqualTo("myprefix-0001");
		assertThat(generator.nextValue()).isEqualTo("myprefix-0002");
	}

	@Test
	public void no_left_padding(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-").withLeftPadding(4).withoutLeftPadding();

		assertThat(generator.nextValue()).isEqualTo("myprefix-1");
		assertThat(generator.nextValue()).isEqualTo("myprefix-2");
	}

}
