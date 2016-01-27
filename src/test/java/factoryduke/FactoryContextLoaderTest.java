package factoryduke;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class FactoryContextLoaderTest {

	@Test
	public void loadConfiguration(){
		FactoryContext context = new FactoryContextLoader(new FactoryScanner().withPackages("configuration")).load();
		assertThat(context.getPackages()).contains("custom");
	}
}
