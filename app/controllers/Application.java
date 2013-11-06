package controllers;

import controllers.actions.DetectUser;
import views.html.main;
import controllers.authentication.*;
import models.User;
import play.mvc.*;
import play.mvc.Security.Authenticated;

public class Application extends Controller {

	@With(DetectUser.class)
	@Authenticated(UserAuthenticator.class)
	public static Result index() {
		if (User.isEmployee(ctx()) || User.isCustomer(ctx()))
			return ok(main.render(null, null, views.html.start.render()));
		return redirect(routes.TaskController.tasks());
	}
}
