package model;

/**
 * Created by regis.leray on 1/23/16.
 */
public class User {
	private long id;
	private String name;
	private String lastName;
	private Role role;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private Address addr;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setAddr(Address addr) {
		this.addr = addr;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddr() {
		return addr;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return name;
	}
}
