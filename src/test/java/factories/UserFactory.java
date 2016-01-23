package factories;

import factoryduke.IFactory;
import factoryduke.FactoryDuke;
import model.Address;
import model.Role;
import model.User;

public class UserFactory implements IFactory {
	@Override
	public void define() {
		FactoryDuke.define(User.class, "admin_user", u -> {
			u.setLastName("John");
			u.setName("Malcom");
			u.setRole(Role.ADMIN);
		});

		FactoryDuke.define(User.class, "user_with_fr_address", u -> {
			u.setLastName("Scott");
			u.setName("Malcom");
			u.setRole(Role.USER);

			u.setAddr(FactoryDuke.build(Address.class, "address_in_fr"));
		});
	}
}
