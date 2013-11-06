package controllers;

import java.util.List;

import controllers.actions.DetectUser;
import controllers.authentication.*;
import models.CustomerUser;
import models.Insurance;
import models.User;
import play.data.Form;
import play.mvc.*;
import play.mvc.Security.Authenticated;
import views.html.main;

@With(DetectUser.class)
@Authenticated(UserAuthenticator.class)
public class InsuranceController extends Controller {

	static Form<Insurance> insuranceForm = Form.form(Insurance.class);

	private static List<Insurance> allInsurances() {
		if (User.isEmployee(ctx())) {
			return Insurance.all();
		} else {
			return Insurance.ofCustomer(CustomerUser.fetch(ctx()));
		}
	}

	public static Result get() {
		return ok(main.render(null, null,
				views.html.insurances.render(insuranceForm, allInsurances(), false)));
	}

	public static Result post() {
		Form<Insurance> filledForm = insuranceForm.bindFromRequest();

		if (filledForm.hasErrors()) {
			return badRequest(main.render(null, null,
					views.html.insurances.render(filledForm, allInsurances(), false)));
		} else {
			Insurance.create(filledForm.get());
			flash("success", "The insurance have been added.");
			return created(main.render(null, null,
					views.html.insurances.render(insuranceForm, allInsurances(), false)));
		}
	}

	public static Result getItem(long id) {
		Form<Insurance> editInsuranceForm = insuranceForm.fill(Insurance.fetch(id));
		return ok(main.render(null, null,
				views.html.insurances.render(editInsuranceForm, allInsurances(), true)));
	}

	public static Result postItem(long id) {
		Form<Insurance> filledForm = insuranceForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(main.render(null, null,
					views.html.insurances.render(filledForm, allInsurances(), true)));
		} else {
			filledForm.get().update(id);
			flash("success", "The insurance have been updated.");
			return get();
		}
	}

	public static Result postItemDelete(long id) {
		Insurance insurance = Insurance.fetch(id);
		if (insurance == null) {
			return badRequest(main.render(null, null,
					views.html.insurances.render(insuranceForm, allInsurances(), false)));
		}
		insurance.delete();
		flash("success", "The insurance have been deleted.");
		return redirect(routes.InsuranceController.get());
	}
}
