/**
 * The MIT License
 * Copyright (c) 2016 Regis Leray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
		FactoryRuntimeHolder.getRuntime().reset();
	}

	@Test
	public void found_factories() {
		FactoryRuntimeHolder.getRuntime().load();
		assertThat(FactoryRuntimeHolder.getRuntime().getTemplates()).hasSize(7);
	}

	@Test
	public void reset() {
		assertThat(FactoryRuntimeHolder.getRuntime().getTemplates()).isEmpty();
	}

	@Test
	public void duplicate_definition() {
		Template tp = new ConsumerTemplate(User.class, User.class.getCanonicalName(), u -> u.setName("test"));
		FactoryRuntimeHolder.getRuntime().register(tp);

		assertThatThrownBy(() -> FactoryRuntimeHolder.getRuntime().register(tp))
				.isInstanceOf(TemplateDuplicateException.class)
				.hasMessageContaining("Cannot define duplicate template with the same identifier")
				.hasNoCause();
	}

	@Test
	public void build_instance_with_supplier_wrong_cast() {
		FactoryRuntimeHolder.getRuntime().load();

		assertThatThrownBy(() -> {
			FactoryRuntimeHolder.getRuntime().<Address>build("admin_user", o -> {
			}).toOne();
		}).isInstanceOf(ClassCastException.class);
	}

	@Test
	public void build_instance_with_consumer_wrong_cast() {
		FactoryRuntimeHolder.getRuntime().load();

		assertThatThrownBy(() -> {
			FactoryRuntimeHolder.getRuntime().<Address>build("user_with_fr_address", o -> {
			}).toOne();
		}).isInstanceOf(ClassCastException.class);
	}

	@Test
	public void no_template_found() {
		assertThatThrownBy(() ->
				FactoryRuntimeHolder.getRuntime().<User>build("not_found", u -> {
				})
		).isInstanceOf(TemplateNotFoundException.class)
				.hasMessageContaining("No builder register with identifier : not_found")
				.hasNoCause();
	}

	@Test
	public void no_constructor_with_template() {
		Template tp = new ConsumerTemplate(NoConstructor.class, "no_default_constructor", c -> {
		});
		FactoryRuntimeHolder.getRuntime().register(tp);

		assertThatThrownBy(() ->
				FactoryRuntimeHolder.getRuntime().build("no_default_constructor", c -> {
				}).toOne()
		).isInstanceOf(TemplateInstanciationException.class);
	}


	private static class NoConstructor {
		public NoConstructor(String argument) {
		}
	}
}
