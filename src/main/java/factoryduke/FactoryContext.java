package factoryduke;

import java.util.function.Consumer;

public interface FactoryContext {
	void registerGlobalCallbacks(Consumer...callbacks);
}
