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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import factoryduke.utils.Assert;

public class SequenceValuesGenerator<T> implements Generator<T> {

	protected List<T> actual;

	private AtomicInteger counter;

	SequenceValuesGenerator(){
		this(new ArrayList<>());
	}

	private SequenceValuesGenerator(List<T> values) {
		this.actual = values;
		counter = new AtomicInteger();
	}

	public SequenceValuesGenerator withValues(T... values){
		Assert.that().notNull(values).isTrue(values.length > 0);

		for(T v : values){
			this.actual.add(v);
		}

		return this;
	}

	@Override
	public T nextValue() {
		T value = actual.isEmpty() ? null : actual.get(counter.getAndIncrement());

		if(isloopFinished()){
			counter = new AtomicInteger();
		}

		return value;
	}

	protected boolean isloopFinished() {
		return actual.size() == counter.intValue();
	}
}
