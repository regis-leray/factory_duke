package factoryduke.generators;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

public class ConstantGeneratorTest {

	@Test
	public void constant_string(){
		Generator<String> constant = Generators.constant("1");
		assertThat(constant.nextValue()).isEqualTo("1");
		assertThat(constant.nextValue()).isEqualTo("1");
		assertThat(constant.nextValue()).isEqualTo("1");
	}

	@Test
	public void constant_long(){
		Generator<Long> constant = Generators.constant(1L);
		assertThat(constant.nextValue()).isEqualTo(1L);
		assertThat(constant.nextValue()).isEqualTo(1L);
		assertThat(constant.nextValue()).isEqualTo(1L);
	}

	@Test
	public void constant_date(){
		final Date now = new Date();
		Generator<Date> constant = Generators.constant(now);
		assertThat(constant.nextValue()).isEqualTo(now);
		assertThat(constant.nextValue()).isEqualTo(now);
		assertThat(constant.nextValue()).isEqualTo(now);
	}

}
