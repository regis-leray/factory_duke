package factoryduke;

import java.util.Arrays;

public class FactoryContext {

	private String[] packages = new String[0];

	public FactoryContext withPackages(String... packages) {
		this.packages = packages;
		return this;
	}

	public FactoryContext withPackages(Class... packages) {
		this.packages = Arrays.asList(packages).stream().map(Class::getCanonicalName).toArray(String[]::new);
		return this;
	}

	public String[] getPackages() {
		return packages;
	}
}
