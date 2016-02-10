package factoryduke.builder;

public interface HookBuilder<T> extends InstanceBuilder<T> {
	HookBuilder<T> skipBeforeHook(boolean skip);

	HookBuilder<T> skipAfterHook(boolean skip);
}
