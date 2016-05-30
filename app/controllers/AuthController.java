package controllers;

import javax.inject.Inject;

import models.*;
import play.data.*;
import play.mvc.*;

import views.html.*;

public class AuthController extends Controller {

	@Inject
	private FormFactory formFactory;

	//loginページを表示
	public Result login() {

		//ログイン中であればログイン画面は表示しない
		if(session("login") != null) {
			return redirect(routes.MypageController.mypage_main());
		}

		return ok(login.render(formFactory.form(Login.class)));

	}

	//認証判定メソッド
	public Result authenticate() {

		Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();

		if(loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			Login login = loginForm.get();
			session("login", login.usercode);
			return redirect(routes.MypageController.mypage_main());
		}

	}

	//logoutメソッド
	public Result logout() {
		session().clear();
		return redirect(routes.AuthController.login());
	}

}
