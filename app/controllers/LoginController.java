package controllers;

import controllers.actions.DetectUser;
import models.User;
import play.data.*;
import play.mvc.*;
import views.html.*;

@With(DetectUser.class)
public class LoginController extends Controller {

	public static Form<Login> loginForm = Form.form(Login.class);

	public static Result login() {
		if (DetectUser.isPresent(ctx()))
			return redirect(routes.Application.index());
		return ok(main.render(null, loginForm, index.render()));
	}

	public static Result authenticate() {
		Form<Login> filledForm = loginForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(main.render(null, filledForm, index.render()));
		} else {
			session().clear();
			session("username", filledForm.get().username);
			return redirect(routes.Application.index());
		}
	}

	public static Result logout() {
		session().clear();
		flash("success", "You have been successfully logged out.");
		return redirect(routes.LoginController.login());
	}

	public static class Login {

		public String username;
		public String password;

		public String validate() {
			if (User.authenticate(username, password) == null) {
				return "Invalid username or password";
			}
			return null;
		}

	}

}
