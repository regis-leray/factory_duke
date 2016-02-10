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


public class SequenceGeneratorTest {


	@Test
	public void default_generator(){

		SequenceGenerator sequence = Generators.sequence();

		assertThat(sequence.nextValue()).isEqualTo(1L);
		assertThat(sequence.nextValue()).isEqualTo(2L);
	}

	@Test
	public void start_at_2(){

		SequenceGenerator sequence = Generators.sequence().startingAt(2L);

		assertThat(sequence.nextValue()).isEqualTo(2L);
		assertThat(sequence.nextValue()).isEqualTo(3L);
	}

	@Test
	public void increment_by_2(){

		SequenceGenerator sequence = Generators.sequence().incrementingBy(2);

		assertThat(sequence.nextValue()).isEqualTo(1L);
		assertThat(sequence.nextValue()).isEqualTo(3L);
	}

	@Test
	public void start_at_2_and_increment_by_2(){

		SequenceGenerator sequence = Generators.sequence().startingAt(2L).incrementingBy(2);

		assertThat(sequence.nextValue()).isEqualTo(2L);
		assertThat(sequence.nextValue()).isEqualTo(4L);
		assertThat(sequence.nextValue()).isEqualTo(6L);
	}

}
