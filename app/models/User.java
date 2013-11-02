package models;

import java.util.List;

import javax.persistence.*;

import play.db.ebean.*;

import com.avaje.ebean.*;

@Entity
public class User extends Model {

	@Id
	public String username;
	public String password;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	public List<Task> tasks;

	public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/*
	 * Static methods
	 */

	public static User fetch(String username) {
		return find.byId(username);
	}

	public static void create(User user) {
		user.save();
	}

	public static User authenticate(String username, String password) {
		return find.where().eq("username", username).eq("password", password).findUnique();
	}
}
