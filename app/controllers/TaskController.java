package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Security.Authenticated;
import play.data.*;
import models.*;
import views.html.*;

@Authenticated(Secured.class)
public class TaskController extends Controller {

	static Form<Task> taskForm = Form.form(Task.class);

	public static User loadUser() {
		return User.fetch(session().get("username"));
	}

	public static Result tasks() {
		User user = loadUser();
		return ok(main.render("Tasks", null, views.html.tasks.render(user.tasks, taskForm)));
	}

	public static Result newTask() {
		User user = loadUser();
		Form<Task> filledForm = taskForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			flash("error", "The task could not be saved.");
			return badRequest(main.render("Tasks", null, tasks.render(user.tasks, filledForm)));
		} else {
			Task task = filledForm.get();
			task.owner = user;
			task.save();
			return redirect(routes.TaskController.tasks());
		}
	}

	public static Result deleteTask(Long id) {
		Task.delete(id);
		return redirect(routes.TaskController.tasks());
	}
}
