package factoryduke;


import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;

import model.Address;
import model.Role;
import model.User;

public class FactoryDukeTest {

	@Before
	public void initialize(){
		FactoryDuke.load();
	}

	@Test
	public void build_consumer_definition(){
		assertThat(FactoryDuke.build(User.class)).extracting(User::getName, User::getLastName)
				.contains("Malcom", "Scott");
	}

	@Test
	public void adress_default(){
		assertThat(FactoryDuke.build(Address.class)).extracting(Address::getCity, Address::getStreet).contains("Montreal", "prince street");
	}

	@Test
	public void factory_call_other_factory(){
		assertThat(FactoryDuke.build(User.class, "user_with_fr_address")).extracting("name", "addr.city").contains("Malcom", "Paris");
	}

	@Test
	public void repeat_1_user(){
		assertThat(FactoryDuke.repeat(User.class).toOne()).extracting(User::getName, User::getLastName)
				.contains("Malcom", "Scott");
	}

	@Test
	public void repeat_2_users(){
			assertThat(FactoryDuke.repeat(User.class).times(2).toList())
					.hasSize(2)
					.extracting(User::getName, User::getLastName)
					.containsExactly(Tuple.tuple("Malcom", "Scott"), Tuple.tuple("Malcom", "Scott"));
	}

	@Test
	public void repeat_2_users_as_set(){
		assertThat(FactoryDuke.repeat(User.class).times(2).toSet())
				.hasSize(2)
				.extracting(User::getName, User::getLastName)
				.containsExactly(Tuple.tuple("Malcom", "Scott"), Tuple.tuple("Malcom", "Scott"));
	}

	@Test
	public void repeat_2_users_with_identifier(){
		assertThat(FactoryDuke.repeat(User.class, "user_with_fr_address").times(2).toList())
				.hasSize(2)
				.extracting(User::getName, User::getLastName, u -> u.getAddr().getCity())
				.containsExactly(Tuple.tuple("Malcom", "Scott", "Paris"), Tuple.tuple("Malcom", "Scott", "Paris"));
	}

	@Test
	public void repeat_2_users_with_override(){
		assertThat(FactoryDuke.repeat(User.class, u -> {
			u.setRole(Role.USER);
			u.setId(1L);
		}).times(2).toList())
				.hasSize(2)
				.extracting(User::getId, User::getLastName, User::getRole)
				.containsExactly(Tuple.tuple(1L, "Scott", Role.USER), Tuple.tuple(1L, "Scott", Role.USER));
	}

	@Test
	public void repeat_2_users_with_identifier_and_override(){
		assertThat(FactoryDuke.repeat(User.class, "user_with_fr_address", u -> {
			u.getAddr().setCity("NY");
			u.setRole(Role.USER);
		}).times(2).toList())
				.hasSize(2)
				.extracting(User::getName, User::getLastName, User::getRole, u -> u.getAddr().getCity())
				.containsExactly(Tuple.tuple("Malcom", "Scott", Role.USER, "NY"), Tuple.tuple("Malcom", "Scott", Role.USER, "NY"));
	}

}
