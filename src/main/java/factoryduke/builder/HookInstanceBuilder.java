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


import java.util.List;
import java.util.function.Consumer;

import factoryduke.function.Callback;

public class HookInstanceBuilder<T> implements HookBuilder<T> {

	private InstanceBuilder<T> builder;

	private final List<Consumer> afterHooks;

	private final List<Callback> beforeHooks;

	private boolean enableAfterHook = true;

	private boolean enableBeforeHook = true;

	public HookInstanceBuilder(List<Callback> beforeHooks, List<Consumer> afterHooks) {
		this.beforeHooks = beforeHooks;
		this.afterHooks = afterHooks;
	}

	public HookInstanceBuilder builder(final InstanceBuilder<T> builder) {
		this.builder = builder;
		return this;
	}

	@Override
	public T toOne() {
		return builder.toOne();
	}

	@Override
	public HookBuilder<T> skipBeforeHook(boolean skip) {
		this.enableBeforeHook = !skip;
		return this;
	}

	@Override
	public HookBuilder<T> skipAfterHook(boolean skip) {
		this.enableAfterHook = !skip;
		return this;
	}

	@Override
	public CollectionBuilder<T> times(int size) {
		return builder.times(size);
	}

	public void callBefore(){
		if(enableBeforeHook){
			beforeHooks.stream().forEach(c -> c.call());
		}
	}

	public void callAfter(T instance){
		if(enableAfterHook){
			afterHooks.stream().forEach(c -> c.accept(instance));
		}
	}
}
