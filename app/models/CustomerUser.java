package models;

import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;

@Entity
@DiscriminatorValue(User.CUSTOMER)
public class CustomerUser extends User {

	@Required
	private String name;
	private String phone;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	public List<Insurance> insurances;

	public CustomerUser(String username, String password) {
		super(username, password);
	}
	
	public CustomerUser(String name, String phone, String username, String password) {
		super(username, password);
		this.name = name;
		this.phone = phone;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
