package controllers;

import play.mvc.*;
import play.data.*;
import models.*;
import views.html.*;

public class UserController extends Controller {

	static Form<User> userForm = Form.form(User.class);

	public static Result showForm() {
		return ok(main.render("Register user", null, user_view.render(userForm)));
	}

	public static Result newUser() {
		Form<User> filledForm = userForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(main.render("Register user", null, user_view.render(filledForm)));
		} else {
			User.create(filledForm.get());
			flash("success", "The user have been registered.");
			return redirect(routes.UserController.showForm());
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result deleteAccount() {
		User user = User.fetch(session().get("username"));
		user.delete();
		session().clear();
		flash("success", "The account have been deleted.");
		return redirect(routes.Application.index());
	}

}
