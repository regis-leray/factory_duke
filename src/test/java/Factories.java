import factoryduke.IFactory;
import factoryduke.FactoryDuke;
import model.Address;
import model.Role;
import model.User;

public class Factories implements IFactory {
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
