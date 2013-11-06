package controllers;

import java.util.List;

import com.avaje.ebean.Ebean;

import controllers.actions.*;
import controllers.authentication.*;
import play.libs.Yaml;
import play.mvc.*;
import play.data.*;
import models.*;
import views.html.main;

@With(DetectUser.class)
public class AdministrationController extends Controller {

	static Form<EmployeeUser> employeeForm = Form.form(EmployeeUser.class);

	public static Result showForm() {
		return ok(main.render("Users", null,
				views.html.administration.render(employeeForm, EmployeeUser.allEmployees())));
	}

	public static Result newUser() {
		Form<EmployeeUser> filledForm = employeeForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(main.render("Register user", null,
					views.html.administration.render(filledForm, EmployeeUser.allEmployees())));
		} else {
			User.create(filledForm.get());
			flash("success", "The user have been registered.");
			return redirect(routes.AdministrationController.showForm());
		}
	}

	@With(DetectUser.class)
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
		return redirect(routes.AdministrationController.showForm());
	}

	public static Result resetDatabase() {
		session().clear();
		for (User user : User.all()) {
			user.delete();
		}
		Ebean.save((List<?>) Yaml.load("initial-data.yml"));
		return redirect(routes.Application.index());
	}

}
