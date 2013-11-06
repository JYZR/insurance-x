package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import play.mvc.Http.Context;

@Entity
@DiscriminatorValue(User.CUSTOMER)
public class CustomerUser extends User {

	public String phone;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	public List<Insurance> insurances;

	public CustomerUser(String name, String username, String password) {
		super(name, username, password);
	}

	public CustomerUser(String name, String phone, String username, String password) {
		super(name, username, password);
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

	public static int count() {
		return find.findRowCount();
	}

	public static Map<String, String> options() {
		HashMap<String, String> options = new HashMap<String, String>();
		for (CustomerUser cUser : allCustomers()) {
			options.put(cUser.username, cUser.name);
		}
		return options;
	}

	public static Map<String, String> options(Context ctx) {
		if (User.isEmployee(ctx))
			return options();
		HashMap<String, String> options = new HashMap<String, String>();
		CustomerUser cUser = fetch(ctx);
		options.put(cUser.username, cUser.name);
		return options;
	}

	public static CustomerUser fetch(Context ctx) {
		return (CustomerUser) ctx.args.get("user");
	}
}
