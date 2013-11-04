package controllers.actions;

import models.CustomerUser;
import models.EmployeeUser;
import play.libs.F.Promise;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;

public class DetectUser extends play.mvc.Action.Simple {

	/*
	 * DetectUser should always be called before any authenticator is executed
	 * 	
	 * Example for how to create annotations:
	 * 
	 * @With(DetectUser.class)
	 * @Authenticated(UserAuthenticator.class)
	 */

	public Promise<SimpleResult> call(Context ctx) throws Throwable {
		String username = ctx.session().get("username");
		if (username != null) {
			EmployeeUser eUser = EmployeeUser.fetch(username);
			if (eUser != null) {
				ctx.args.put("type", "employee");
				ctx.args.put("user", eUser);
			} else {
				CustomerUser cUser = CustomerUser.fetch(username);
				if (cUser != null) {
					ctx.args.put("type", "customer");
					ctx.args.put("user", cUser);
				}
			}
		}
		return delegate.call(ctx);
	}

	public static boolean isPresent(Context ctx) {
		return ctx.args.get("user") != null;
	}
}
