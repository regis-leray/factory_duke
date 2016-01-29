package configuration;

import factoryduke.FactoryDuke;
import factoryduke.TFactory;
import model.Address;
import model.Role;
import model.User;

public class Factories implements TFactory {

	@Override
	public void define() {
		FactoryDuke.define(User.class, u -> {
			u.setLastName("Scott");
			u.setName("Malcom");
			u.setRole(Role.USER);
		});

		FactoryDuke.define(Address.class, a -> {
			a.setCity("Montreal");
			a.setStreet("prince street");
			a.setCountry("Canada");
		});
	}
}
