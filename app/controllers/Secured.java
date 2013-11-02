package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class Secured extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		String username = ctx.session().get("username");
		if (username != null && User.fetch(username) == null)
			return null;
		return username;
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.Application.index());
	}
}
