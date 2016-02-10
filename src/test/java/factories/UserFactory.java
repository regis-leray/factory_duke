/**
 * The MIT License
 * Copyright (c) 2016 Regis Leray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package factories;

import factoryduke.TFactory;
import factoryduke.FactoryDuke;
import model.Address;
import model.Role;
import model.User;

public class UserFactory implements TFactory {
	@Override
	public void define() {
		FactoryDuke.define(User.class, "admin_user", u -> {
			u.setLastName("John");
			u.setName("Malcom");
			u.setRole(Role.ADMIN);
		});

		FactoryDuke.define(User.class, "user_with_fr_address", () -> {
			User u = FactoryDuke.build(User.class).toOne();
			u.setAddr(FactoryDuke.build(Address.class, "address_in_fr").toOne());
			return u;
		});
	}
}
