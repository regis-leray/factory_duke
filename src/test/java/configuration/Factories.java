package configuration;

import factoryduke.FactoryAware;
import factoryduke.FactoryContext;
import factoryduke.FactoryDuke;
import factoryduke.TFactory;
import model.Address;
import model.Role;
import model.User;

public class Factories implements FactoryAware, TFactory {

	@Override
	public FactoryContext preInit() {
		return new FactoryContext().withPackages("custom");
	}

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
