package models;

import java.util.List;

import play.mvc.Http.Context;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "_type", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(User.USER)
public class User extends Model {

	@Required
	public String name;
	@Id
	@Required
	public String username;
	@Required
	public String password;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	public List<Task> tasks;

	public User(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public String getType() {
		return getClass().getSimpleName();
	}

	/*
	 * Static methods
	 */

	public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);

	public static User fetch(String username) {
		return find.byId(username);
	}

	public static void create(User user) {
		user.save();
	}

	public static List<User> all() {
		return find.all();
	}

	public static User authenticate(String username, String password) {
		return find.where().eq("username", username).eq("password", password).findUnique();
	}

	public static final String USER = "0";
	public static final String EMPLOYEE = "1";
	public static final String CUSTOMER = "2";

	public static boolean isEmployee(Context ctx) {
		return ctx.args.get("type") == "employee";
	}

	public static boolean isCustomer(Context ctx) {
		return ctx.args.get("type") == "customer";
	}

	public static int count() {
		return find.findRowCount();
	}

	public static User fetch(Context ctx) {
		return (User) ctx.args.get("user");
	}
}
