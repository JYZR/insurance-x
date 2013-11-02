package controllers;

import models.User;
import controllers.LoginController.Login;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	public static boolean userExists() {
		String username = session().get("username");
		return username != null && User.fetch(username) != null;
	}
	
	public static Result index() {
    	if (userExists())
    		return redirect(routes.TaskController.tasks());
		return ok(main.render(null, Form.form(Login.class), index.render()));
    	
    }

    public static Result welcome() {
    	return ok(welcome_to_play.render("Welcome to Play"));
    }
}
