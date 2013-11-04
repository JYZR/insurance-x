package models;

import java.util.List;

import javax.persistence.*;

@Entity
@DiscriminatorValue(User.CUSTOMER)
public class CustomerUser extends User {

	private String phone;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	public List<Insurance> insurances;

	public CustomerUser(String name, String username, String password) {
		super(name, username, password);
	}

	public CustomerUser(String name, String phone, String username, String password) {
		super(name, username, password);
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/*
	 * Static methods
	 */

	public static Finder<String, CustomerUser> find = new Finder<String, CustomerUser>(
			String.class, CustomerUser.class);

	public static List<CustomerUser> allCustomers() {
		return find.all();
	}

	public static CustomerUser fetch(String username) {
		return find.byId(username);
	}
}
