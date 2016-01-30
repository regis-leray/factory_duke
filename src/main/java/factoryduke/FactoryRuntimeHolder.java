package factoryduke;

/**
 * Runtime Thread safe holder instance
 */
public class FactoryRuntimeHolder {
	private static final ThreadLocal<FactoryRuntime> context = new ThreadLocal<>();

	public static FactoryRuntime getRuntime(){
		if(context.get() == null){
			context.set(new FactoryRuntime());
		}
		return context.get();
	}
}
