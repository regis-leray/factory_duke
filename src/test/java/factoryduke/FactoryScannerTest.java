package factoryduke;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FactoryScannerTest {

	private FactoryScanner scanner;

	@Before
	public void createScanner() {
		scanner = new FactoryScanner().withPackages("factories")
				.withInterfaces(IFactory.class)
				.withAnnotations(Factory.class);
	}

	@Test
	public void defaultScan() throws Exception {
		List<String> founded = scanner.scan();
		assertThat(founded).hasSize(3).contains("factories.AdressFactory", "factories.UserFactory", "factories.ContactFactory");
	}

	@Test
	public void scan_custom_package() throws Exception {
		List<String> founded = scanner.withPackages("custom").scan();
		assertThat(founded).hasSize(1).contains("custom.EmptyFactory");
	}

	@Test
	public void nothing() throws Exception {
		List<String> founded = scanner.withPackages("test").scan();
		assertThat(founded).hasSize(0);
	}

	@Test
	public void nothing_with_wrong_annotation() throws Exception {
		List<String> founded = new FactoryScanner().withInterfaces(new Class[0]).withAnnotations(NoFactory.class).scan();
		assertThat(founded).hasSize(0);
	}

	@Test
	public void nothing_with_wrong_interface() throws Exception {
		List<String> founded = new FactoryScanner().withInterfaces(NoIFactory.class).withAnnotations(new Class[0]).scan();
		assertThat(founded).hasSize(0);
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE) //can use in class only.
	public @interface NoFactory {}

	public interface NoIFactory {}
}
