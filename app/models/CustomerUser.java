package models;

import java.util.List;

import javax.persistence.*;

@Entity
@DiscriminatorValue(User.CUSTOMER)
public class CustomerUser extends User {

	public String phone;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	public List<Insurance> insurances;

	public CustomerUser(String username, String password) {
		super(username, password);
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
