package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class Task extends Model {

	@Id
	public Long id;
	@Required
	public String label;
	//@ManyToOne
	@ManyToOne(cascade = CascadeType.REMOVE, optional = false)
	public User owner;

	public static Finder<Long, Task> find = new Finder<Long, Task>(Long.class, Task.class);

	public static Task fetch(Long id) {
		return find.byId(id);
	}
	
	public static void delete(Long id) {
		find.byId(id).delete();
	}

	public static List<Task> findTasks(User user) {
		return find.where().eq("owner", user).findList();
	}

}