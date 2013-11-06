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
public class CustomerController extends Controller {

	static Form<CustomerUser> customerForm = Form.form(CustomerUser.class);

	public static Result get() {
		return ok(main.render(null, null,
				views.html.customers.render(customerForm, CustomerUser.allCustomers())));
	}

	public static Result post() {
		Form<CustomerUser> filledForm = customerForm.bindFromRequest();

		if (filledForm.hasErrors()) {
			return badRequest(main.render(null, null,
					views.html.customers.render(filledForm, CustomerUser.allCustomers())));
		} else {
			CustomerUser.create(filledForm.get());
			flash("success", "The customer have been added.");
			return created(main.render(null, null,
					views.html.customers.render(customerForm, CustomerUser.allCustomers())));
		}
	}

	public static Result postItemDelete(String username) {
		CustomerUser customer = CustomerUser.fetch(username);
		if (customer == null) {
			return badRequest(main.render(null, null,
					views.html.customers.render(customerForm, CustomerUser.allCustomers())));
		}
		customer.delete();
		flash("success", "The customer have been deleted.");
		return redirect(routes.CustomerController.get());
	}
}
