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
package factoryduke.builder;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import factoryduke.Template;
import factoryduke.function.Callback;

public class InstanceBuilderImpl<T> implements InstanceBuilder<T>, InstanceBuilder.CollectionBuilder<T> {

	private final Template template;

	private final Consumer<T> override;

	private int times;

	public InstanceBuilderImpl(Template template, Consumer<T> override) {
		this.template = template;
		this.override = override;
	}

	public T toOne() {
		return times(1).toList().get(0);
	}

	public CollectionBuilder<T> times(int times) {
		this.times = times;
		return this;
	}

	public List<T> toList() {
		return callTemplate();
	}

	public Set<T> toSet() {
		final HashSet<T> set = new HashSet<>();
		set.addAll(callTemplate());
		return set;
	}

	private List<T> callTemplate() {
		List<T> list = new ArrayList<>(times);
		AtomicInteger counter = new AtomicInteger(times);

		while (counter.getAndDecrement() > 0) {
			list.add(createInstance());
		}
		return list;
	}

	private T createInstance() {
		doBefore();

		final T instance = template.create();
		override.accept(instance);

		doAfter(instance);

		return instance;
	}

	public void doAfter(T instance) {
	}

	public void doBefore() {
	}
}
