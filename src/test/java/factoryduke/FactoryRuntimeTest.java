package factoryduke;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.function.Consumer;

import org.junit.Test;

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
		FactoryRuntime.getRuntime().register(User.class, User.class.getCanonicalName(), (Consumer<User>) u -> u.setName("test"));

		assertThatThrownBy(() -> FactoryRuntime.getRuntime().register(User.class, User.class.getCanonicalName(), (Consumer<User>) u -> u.setName("test")))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("Cannot define duplicate template with the same identifier")
				.hasNoCause();
	}

	@Test
	public void wrong_template_instance(){

	}

	@Test
	public void no_template_found(){
		assertThatThrownBy(() ->
				FactoryRuntime.getRuntime().build(User.class, "not_found", u -> {})
		).isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("No builder register with identifier : not_found")
				.hasNoCause();
	}

	@Test
	public void no_constructor_with_template(){
		FactoryRuntime.getRuntime().register(NoConstructor.class, "no_default_constructor", (Consumer<NoConstructor>) c -> {});

		assertThatThrownBy(() ->
				FactoryRuntime.getRuntime().build(NoConstructor.class, "no_default_constructor", c -> {})
		).isInstanceOf(RuntimeException.class)
				.hasRootCauseInstanceOf(NoSuchMethodException.class);
	}


	private static class NoConstructor {
		public NoConstructor(String argument){}
	}
}
