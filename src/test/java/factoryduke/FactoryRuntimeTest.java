package factoryduke;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import factoryduke.exceptions.TemplateDuplicateException;
import factoryduke.exceptions.TemplateInstanciationException;
import factoryduke.exceptions.TemplateNotFoundException;
import model.User;

public class FactoryRuntimeTest {

	@Test
	public void found_factories(){
		FactoryRuntime.getRuntime().reset();
		FactoryRuntime.getRuntime().load();
		assertThat(FactoryRuntime.getRuntime().getTemplates()).hasSize(7);
	}

	@Test
	public void reset(){
		FactoryRuntime.getRuntime().reset();
		assertThat(FactoryRuntime.getRuntime().getTemplates()).isEmpty();
	}

	@Test
	public void duplicate_definition(){
		Template tp = new ConsumerTemplate(User.class, User.class.getCanonicalName(), u -> u.setName("test"));
		FactoryRuntime.getRuntime().register(tp);

		assertThatThrownBy(() -> FactoryRuntime.getRuntime().register(tp))
				.isInstanceOf(TemplateDuplicateException.class)
				.hasMessageContaining("Cannot define duplicate template with the same identifier")
				.hasNoCause();
	}

	@Test
	public void wrong_template_instance(){

	}

	@Test
	public void no_template_found(){
		assertThatThrownBy(() ->
				FactoryRuntime.getRuntime().<User>build("not_found", u -> {})
		).isInstanceOf(TemplateNotFoundException.class)
				.hasMessageContaining("No builder register with identifier : not_found")
				.hasNoCause();
	}

	@Test
	public void no_constructor_with_template(){
		Template tp = new ConsumerTemplate(NoConstructor.class, "no_default_constructor", c -> {});
		FactoryRuntime.getRuntime().register(tp);

		assertThatThrownBy(() ->
				FactoryRuntime.getRuntime().build("no_default_constructor", c -> {})
		).isInstanceOf(TemplateInstanciationException.class);
	}


	private static class NoConstructor {
		public NoConstructor(String argument){}
	}
}
