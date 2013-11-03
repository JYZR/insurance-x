package models;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(User.EMPLOYEE)
public class EmployeeUser extends User {

	public EmployeeUser(String username, String password) {
		super(username, password);
	}

	/*
	 * Static methods
	 */

	public static Finder<String, EmployeeUser> find = new Finder<String, EmployeeUser>(
			String.class, EmployeeUser.class);

	public static List<EmployeeUser> allEmployees() {
		return find.all();
	}

	public static EmployeeUser fetch(String username) {
		return find.byId(username);
	}
}