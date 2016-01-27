package factoryduke;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import model.Address;
import model.User;

public class FactoryDukeTest {

	@Before
	public void initialize(){
		FactoryDuke.load();
	}

	@Test
	public void user_default(){
		assertThat(FactoryDuke.build(User.class)).extracting(User::getName, User::getLastName)
				.contains("Malcom", "Scott");
	}

	@Test
	public void adress_default(){
		assertThat(FactoryDuke.build(Address.class)).extracting(Address::getCity, Address::getStreet).contains("Montreal", "prince street");
	}

	@Test
	public void user_with_fr_address(){
		assertThat(FactoryDuke.build(User.class, "user_with_fr_address")).extracting("name", "addr.city").contains("Malcom", "Paris");
	}
}
