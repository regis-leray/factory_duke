package custom;

import factoryduke.FactoryDuke;
import factoryduke.TFactory;
import model.Role;
import model.User;

public class EmptyFactory implements TFactory {
	@Override
	public void define() {
		FactoryDuke.define(User.class, u -> {
			u.setName("Empty");
			u.setRole(Role.ADMIN);
		});

	}
}
