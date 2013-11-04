package controllers;

import controllers.actions.DetectUser;
import controllers.authentication.*;
import models.CustomerUser;
import play.data.Form;
import play.mvc.*;
import play.mvc.Security.Authenticated;
import views.html.main;

@With(DetectUser.class)
@Authenticated(EmployeeAuthenticator.class)
public class EmployeeController extends Controller {

	static Form<CustomerUser> customerForm = Form.form(CustomerUser.class);

	public static Result start() {
		return ok(main.render(null, null, views.html.employee.start.render()));
	}

	public static Result newCustomer() {
		return ok(main.render(null, null, views.html.employee.new_customer.render(customerForm)));
	}

	public static Result addCustomer() {
		Form<CustomerUser> filledForm = customerForm.bindFromRequest();

		if (filledForm.hasErrors()) {
			return badRequest(main
					.render(null, null, views.html.employee.new_customer.render(filledForm)));
		} else {
			CustomerUser.create(filledForm.get());
			flash("success", "The customer have been added.");
			return created(main.render(null, null, views.html.employee.new_customer.render(customerForm)));
		}
	}
}
