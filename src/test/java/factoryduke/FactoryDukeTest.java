package factoryduke;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import model.Address;
import model.Role;
import model.User;

public class FactoryDukeTest {

	@Before
	public void defineManualFactory(){
		FactoryDuke.reset();

		FactoryDuke.define(User.class, u -> {
			u.setLastName("Scott");
			u.setName("Malcom");
			u.setRole(Role.USER);
		});

		FactoryDuke.define(User.class, "admin_user", u -> {
			u.setLastName("John");
			u.setName("Malcom");
			u.setRole(Role.ADMIN);
		});

		FactoryDuke.define(User.class, "user_with_address", () -> {
			User u = FactoryDuke.build(User.class);

			Address adress = new Address();
			adress.setCity("MTL");
			adress.setStreet("prince street");
			u.setAddr(adress);
			return u;
		});

		FactoryDuke.define(User.class, "user_with_fr_address", u -> {
			u.setLastName("Scott");
			u.setName("Malcom");
			u.setRole(Role.USER);

			u.setAddr(FactoryDuke.build(Address.class, "address_in_fr"));
		});

		FactoryDuke.define(Address.class, "address_in_fr", a -> {
			a.setCity("Paris");
			a.setStreet("rue d'avignon");
			a.setCountry("France");
		});
	}

	@Test
	public void defaultUser(){
		User defaultUser = FactoryDuke.build(User.class);
		assertThat(defaultUser).isNotNull();
	}

	@Test
	public void defaultUserWithOverride(){
		User user = FactoryDuke.build(User.class, u -> u.setName("toto"));
		assertThat(user).isNotNull().hasFieldOrPropertyWithValue("name", "toto");;
	}

	@Test
	public void adminUser(){
		User user = FactoryDuke.build(User.class, "admin_user");
		assertThat(user).isNotNull().hasFieldOrPropertyWithValue("role", Role.ADMIN);
	}

	@Test
	public void defaultUserWithAddr(){
		User user = FactoryDuke.build(User.class, "user_with_address");
		assertThat(user).isNotNull().hasFieldOrPropertyWithValue("addr.city", "MTL");
	}

	@Test
	public void defaultUserWithAddrWithOverride(){
		User user = FactoryDuke.build(User.class, "user_with_fr_address");
		assertThat(user).isNotNull().hasFieldOrPropertyWithValue("addr.city", "Paris");
	}

	@Test
	public void adminUserWithFrAddr(){
		User user = FactoryDuke.build(User.class, "user_with_fr_address", u -> u.setRole(Role.ADMIN));

		assertThat(user).isNotNull()
				.hasFieldOrPropertyWithValue("role", Role.ADMIN)
				.hasFieldOrPropertyWithValue("addr.city", "Paris");
	}
}
