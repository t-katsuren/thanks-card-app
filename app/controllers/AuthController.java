package controllers;

import play.mvc.*;

import views.html.*;

public class AuthController extends Controller {

	//loginページ
	public Result login() {
		return ok(login.render());
	}

	//認証メソッド
	public Result authenticate() {
		return TODO;
	}

}
