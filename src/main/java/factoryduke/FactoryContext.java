package factoryduke;

import java.util.function.Consumer;

public interface FactoryContext {
	void registerGlobalCallback(Consumer...callbacks);
}
