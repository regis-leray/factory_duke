package factoryduke;

import java.util.function.Consumer;

import factoryduke.function.Callback;

public interface FactoryContext {
	FactoryContext addBeforeHook(Callback hook);

	FactoryContext addAfterHook(Consumer hook);
}
