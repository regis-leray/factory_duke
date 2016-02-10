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


public final class SequenceGenerator implements Generator<Long> {

	private long next = 1L;
	private int increment = 1;

	SequenceGenerator() {
		this(1, 1);
	}

	private SequenceGenerator(long start, int increment) {
		this.next = start;
		this.increment = increment;
	}

	/**
	 * Restarts the sequence at the given value
	 * @param start the starting value of the created generator
	 * @return this instance, for chaining
	 */
	public SequenceGenerator startingAt(long start) {
		this.next = start;
		return this;
	}

	/**
	 * Increments the value by the given increment.
	 * @return this instance, for chaining
	 */
	public SequenceGenerator incrementingBy(int increment) {
		this.increment = increment;
		return this;
	}

	@Override
	public Long nextValue() {
		long result = next;
		next += increment;
		return result;
	}

	@Override
	public String toString() {
		return "SequenceGenerator["
				+ "next="
				+ next
				+ ", increment="
				+ increment
				+ "]";
	}
}
