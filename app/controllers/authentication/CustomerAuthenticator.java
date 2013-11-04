package controllers.authentication;

import controllers.*;
import models.*;
import play.mvc.*;
import play.mvc.Http.*;

public class CustomerAuthenticator extends Security.Authenticator {

	/*
	 * DetectUser should always be called before this is executed
	 * 
	 * Example for how to create annotations:
	 * 
	 * @With(DetectUser.class)
	 * @Authenticated(CustomerAuthenticator.class)
	 */

	public String getUsername(Context ctx) {
		if (ctx.args.get("type") == "customer")
			return ((CustomerUser) ctx.args.get("user")).username;
		return null;
	}

	public Result onUnauthorized(Context ctx) {
		return redirect(routes.LoginController.login());
	}

	public static boolean signedIn(Context ctx) {
		return ctx.args.get("type") == "customer";
	}
}
