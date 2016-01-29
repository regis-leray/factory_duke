package factoryduke.generators;



import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class SequenceGeneratorTest {


	@Test
	public void default_generator(){

		SequenceGenerator sequence = Generators.sequence();

		assertThat(sequence.nextValue()).isEqualTo(1L);
		assertThat(sequence.nextValue()).isEqualTo(2L);
	}

	@Test
	public void start_at_2(){

		SequenceGenerator sequence = Generators.sequence().startingAt(2L);

		assertThat(sequence.nextValue()).isEqualTo(2L);
		assertThat(sequence.nextValue()).isEqualTo(3L);
	}

	@Test
	public void increment_by_2(){

		SequenceGenerator sequence = Generators.sequence().incrementingBy(2);

		assertThat(sequence.nextValue()).isEqualTo(1L);
		assertThat(sequence.nextValue()).isEqualTo(3L);
	}

	@Test
	public void start_at_2_and_increment_by_2(){

		SequenceGenerator sequence = Generators.sequence().startingAt(2L).incrementingBy(2);

		assertThat(sequence.nextValue()).isEqualTo(2L);
		assertThat(sequence.nextValue()).isEqualTo(4L);
		assertThat(sequence.nextValue()).isEqualTo(6L);
	}

}
