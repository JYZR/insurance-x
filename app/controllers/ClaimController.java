package controllers;

import java.util.List;

import controllers.actions.DetectUser;
import controllers.authentication.*;
import models.Claim;
import models.CustomerUser;
import models.User;
import play.data.Form;
import play.mvc.*;
import play.mvc.Security.Authenticated;
import views.html.main;

@With(DetectUser.class)
@Authenticated(UserAuthenticator.class)
public class ClaimController extends Controller {

	static Form<Claim> claimForm = Form.form(Claim.class);

	private static List<Claim> allClaims() {
		if (User.isEmployee(ctx())) {
			return Claim.all();
		} else {
			return Claim.ofCustomer(CustomerUser.fetch(ctx()));
		}
	}

	public static Result get() {
		return ok(main.render(null, null, views.html.claims.render(claimForm, allClaims(), false)));
	}

	public static Result post() {
		Form<Claim> filledForm = claimForm.bindFromRequest();

		if (filledForm.hasErrors()) {
			return badRequest(main.render(null, null,
					views.html.claims.render(filledForm, allClaims(), false)));
		} else {
			Claim.create(filledForm.get());
			flash("success", "The claim have been added.");
			return created(main.render(null, null,
					views.html.claims.render(claimForm, allClaims(), false)));
		}
	}

	public static Result getItem(long id) {
		Form<Claim> editClaimForm = claimForm.fill(Claim.fetch(id));
		return ok(main.render(null, null,
				views.html.claims.render(editClaimForm, allClaims(), true)));
	}

	public static Result postItem(long id) {
		Form<Claim> filledForm = claimForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(main.render(null, null,
					views.html.claims.render(filledForm, allClaims(), true)));
		} else {
			filledForm.get().update(id);
			;
			flash("success", "The claim have been updated.");
			return get();
		}
	}

	public static Result postItemDelete(long id) {
		Claim claim = Claim.fetch(id);
		if (claim == null) {
			return badRequest(main.render(null, null,
					views.html.claims.render(claimForm, allClaims(), false)));
		}
		claim.delete();
		flash("success", "The claim have been deleted.");
		return redirect(routes.ClaimController.get());
	}

}
