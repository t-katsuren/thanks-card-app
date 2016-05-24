package controllers;

import play.mvc.*;
import play.mvc.Http.Context;

public class Secured extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("login");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.AuthController.login());
	}

}
