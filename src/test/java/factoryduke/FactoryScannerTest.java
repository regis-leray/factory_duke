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
				.withInterfaces(TFactory.class);
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
	public void nothing_with_wrong_interface() throws Exception {
		List<String> founded = new FactoryScanner().withInterfaces(NoIFactory.class).scan();
		assertThat(founded).hasSize(0);
	}

	public interface NoIFactory {}
}
