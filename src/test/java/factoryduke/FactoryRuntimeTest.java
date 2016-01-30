package factoryduke;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;

import factoryduke.exceptions.TemplateDuplicateException;
import factoryduke.exceptions.TemplateInstanciationException;
import factoryduke.exceptions.TemplateNotFoundException;
import model.Address;
import model.User;

public class FactoryRuntimeTest {

	@Before
	public void removeTemplate() {
		FactoryRuntime.getRuntime().reset();
	}

	@Test
	public void found_factories() {
		FactoryRuntime.getRuntime().load();
		assertThat(FactoryRuntime.getRuntime().getTemplates()).hasSize(7);
	}

	@Test
	public void reset() {
		assertThat(FactoryRuntime.getRuntime().getTemplates()).isEmpty();
	}

	@Test
	public void duplicate_definition() {
		Template tp = new ConsumerTemplate(User.class, User.class.getCanonicalName(), u -> u.setName("test"));
		FactoryRuntime.getRuntime().register(tp);

		assertThatThrownBy(() -> FactoryRuntime.getRuntime().register(tp))
				.isInstanceOf(TemplateDuplicateException.class)
				.hasMessageContaining("Cannot define duplicate template with the same identifier")
				.hasNoCause();
	}

	@Test
	public void build_instance_with_supplier_wrong_cast() {
		FactoryRuntime.getRuntime().load();

		assertThatThrownBy(() -> {
			FactoryRuntime.getRuntime().<Address>build("admin_user", o -> {
			}).toOne();
		}).isInstanceOf(ClassCastException.class);
	}

	@Test
	public void build_instance_with_consumer_wrong_cast() {
		FactoryRuntime.getRuntime().load();

		assertThatThrownBy(() -> {
			FactoryRuntime.getRuntime().<Address>build("user_with_fr_address", o -> {
			}).toOne();
		}).isInstanceOf(ClassCastException.class);
	}

	@Test
	public void no_template_found() {
		assertThatThrownBy(() ->
				FactoryRuntime.getRuntime().<User>build("not_found", u -> {
				})
		).isInstanceOf(TemplateNotFoundException.class)
				.hasMessageContaining("No builder register with identifier : not_found")
				.hasNoCause();
	}

	@Test
	public void no_constructor_with_template() {
		Template tp = new ConsumerTemplate(NoConstructor.class, "no_default_constructor", c -> {
		});
		FactoryRuntime.getRuntime().register(tp);

		assertThatThrownBy(() ->
				FactoryRuntime.getRuntime().build("no_default_constructor", c -> {
				}).toOne()
		).isInstanceOf(TemplateInstanciationException.class);
	}


	private static class NoConstructor {
		public NoConstructor(String argument) {
		}
	}
}
