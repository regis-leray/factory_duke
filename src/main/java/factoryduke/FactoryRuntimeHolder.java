package factoryduke;

/**
 * Runtime Thread safe holder instance
 */
public class FactoryRuntimeHolder {
	private static final ThreadLocal<FactoryRuntime> context = new ThreadLocal<>();

	static FactoryRuntime getRuntime(){
		if(context.get() == null){
			context.set(new FactoryRuntime());
		}
		return context.get();
	}
}
