package factoryduke.generators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class StringSequenceGeneratorTest {

	@Test
	public void default_with_int(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-");

		assertThat(generator.nextValue()).isEqualTo("myprefix-1");
		assertThat(generator.nextValue()).isEqualTo("myprefix-2");
	}

	@Test
	public void start_at_2(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-").startingAt(2);

		assertThat(generator.nextValue()).isEqualTo("myprefix-2");
		assertThat(generator.nextValue()).isEqualTo("myprefix-3");
	}

	@Test
	public void increment_by_2(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-").incrementingBy(2);

		assertThat(generator.nextValue()).isEqualTo("myprefix-1");
		assertThat(generator.nextValue()).isEqualTo("myprefix-3");
	}

	@Test
	public void increment_by_2_and_start_at_2(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-").startingAt(2).incrementingBy(2);

		assertThat(generator.nextValue()).isEqualTo("myprefix-2");
		assertThat(generator.nextValue()).isEqualTo("myprefix-4");
	}

	@Test
	public void left_padding_at_4(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-").withLeftPadding(4);

		assertThat(generator.nextValue()).isEqualTo("myprefix-0001");
		assertThat(generator.nextValue()).isEqualTo("myprefix-0002");
	}

	@Test
	public void no_left_padding(){
		final StringSequenceGenerator generator = Generators.stringSequence("myprefix-").withLeftPadding(4).withoutLeftPadding();

		assertThat(generator.nextValue()).isEqualTo("myprefix-1");
		assertThat(generator.nextValue()).isEqualTo("myprefix-2");
	}

}
