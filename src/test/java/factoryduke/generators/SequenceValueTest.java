package factoryduke.generators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SequenceValueTest {

	@Test
	public void long_values(){
		SequenceValuesGenerator<Long> seq = Generators.values(15L, 12L, 1000L);

		assertThat(seq.nextValue()).isEqualTo(15L);
		assertThat(seq.nextValue()).isEqualTo(12L);
		assertThat(seq.nextValue()).isEqualTo(1000L);
	}

	@Test
	public void string_values(){
		SequenceValuesGenerator<String> seq = Generators.values("15L", "12L");

		assertThat(seq.nextValue()).isEqualTo("15L");
		assertThat(seq.nextValue()).isEqualTo("12L");
		assertThat(seq.nextValue()).isEqualTo("15L");
	}
}
