package factories;

import factoryduke.FactoryDuke;
import factoryduke.TFactory;
import model.Contact;

public class ContactFactory implements TFactory {
	@Override
	public void define(){
		FactoryDuke.define(Contact.class , c -> {
			c.setEmail("test@appdirect.com");
			c.setPhone("514-999-1000");
		});

	}
}
