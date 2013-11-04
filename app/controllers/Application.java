package controllers;

import controllers.actions.DetectUser;
import controllers.authentication.*;
import models.User;
import play.mvc.*;
import play.mvc.Security.Authenticated;
import views.html.*;


public class Application extends Controller {

	@With(DetectUser.class)
	@Authenticated(UserAuthenticator.class)
	public static Result index() {
		if (User.isEmployee(ctx()))
			return redirect(routes.EmployeeController.index());
		return redirect(routes.TaskController.tasks());
	}

	public static Result welcome() {
		return ok(welcome_to_play.render("Welcome to Play"));
	}
}
