package controllers;

import controllers.actions.*;
import controllers.authentication.*;
import play.mvc.*;
import play.data.*;
import models.*;
import views.html.main;

@With(DetectUser.class)
public class UserController extends Controller {

	static Form<User> userForm = Form.form(User.class);

	public static Result showForm() {
		return ok(main.render("Users", null, views.html.administration.users.render(userForm,
				EmployeeUser.allEmployees(), CustomerUser.allCustomers())));
	}

	public static Result newUser() {
		Form<User> filledForm = userForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(main.render("Register user", null, views.html.administration.users
					.render(filledForm, EmployeeUser.allEmployees(), CustomerUser.allCustomers())));
		} else {
			User.create(filledForm.get());
			flash("success", "The user have been registered.");
			return redirect(routes.UserController.showForm());
		}
	}

	@Security.Authenticated(UserAuthenticator.class)
	public static Result deleteAccount() {
		User user = (User) ctx().args.get("user");
		user.delete();
		session().clear();
		flash("success", "The account have been deleted.");
		return redirect(routes.Application.index());
	}

	public static Result removeUser(String username) {
		User user = User.fetch(username);
		user.delete();
		flash("success", "The user have been removed.");
		return redirect(routes.UserController.showForm());
	}

}
