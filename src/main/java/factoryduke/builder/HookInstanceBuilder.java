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
