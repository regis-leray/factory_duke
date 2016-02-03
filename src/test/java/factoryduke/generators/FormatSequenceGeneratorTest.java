package factoryduke.generators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class FormatSequenceGeneratorTest {

	@Test
	public void default_generator() {
		FormatSequenceGenerator generator = new FormatSequenceGenerator();

		assertThat(generator.nextValue()).isEqualTo("1");
		assertThat(generator.nextValue()).isEqualTo("2");
		assertThat(generator.nextValue()).isEqualTo("3");
	}

	@Test
	public void custom_message() {
		FormatSequenceGenerator generator = new FormatSequenceGenerator().withMessage("test%s@gmail.com");

		assertThat(generator.nextValue()).isEqualTo("test1@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test2@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test3@gmail.com");
	}

	@Test
	public void increment_by_2() {
		FormatSequenceGenerator generator = new FormatSequenceGenerator().withMessage("test%s@gmail.com").incrementingBy(2);

		assertThat(generator.nextValue()).isEqualTo("test1@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test3@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test5@gmail.com");
	}

	@Test
	public void start_at_2() {
		FormatSequenceGenerator generator = new FormatSequenceGenerator().withMessage("test%s@gmail.com").startingAt(2);

		assertThat(generator.nextValue()).isEqualTo("test2@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test3@gmail.com");
		assertThat(generator.nextValue()).isEqualTo("test4@gmail.com");
	}

}
