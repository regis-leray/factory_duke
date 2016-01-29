package factoryduke.generators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RandomValuesGeneratorTest {

	@Test
	public void with_1_element() {
		RandomValuesGenerator<String> random = Generators.random("1");
		assertThat(random.nextValue()).isEqualTo("1");
	}

	@Test
	public void with_10_elements() {
		String[] elements = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		String[] valuesGenerated = new String[elements.length];

		RandomValuesGenerator<String> random = Generators.random(elements);

		for (int i = 0; i < elements.length; i++) {
			valuesGenerated[i] = random.nextValue();
		}

		assertThat(elements).containsOnly(valuesGenerated);

	}


}


