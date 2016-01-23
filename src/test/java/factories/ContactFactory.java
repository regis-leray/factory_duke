package factories;

import factoryduke.Factory;
import factoryduke.FactoryDuke;
import model.Contact;

@Factory
public class ContactFactory {

	@Factory.Define
	public void define(){

		FactoryDuke.define(Contact.class , c -> {
			c.setEmail("test@appdirect.com");
			c.setPhone("514-999-1000");
		});

	}
}
