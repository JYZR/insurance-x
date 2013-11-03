package models;

import java.util.List;

import javax.persistence.*;

import play.db.ebean.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "_type", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(User.USER)
public class User extends Model {

	@Id
	public String username;
	public String password;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	public List<Task> tasks;

	public User(String username, String password) {
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
}
