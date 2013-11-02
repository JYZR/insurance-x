package controllers;

import models.User;
import play.*;
import play.data.*;
import play.mvc.*;
import views.html.*;

public class LoginController extends Controller {

	public static Result authenticate() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(main.render(null, loginForm, index.render()));
		} else {
			session().clear();
			session("username", loginForm.get().username);
			return redirect(routes.Application.index());
		}
	}

	public static Result logout() {
		session().clear();
		flash("success", "You have been successfully logged out.");
		return redirect(routes.Application.index());
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
