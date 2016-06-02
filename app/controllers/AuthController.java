package controllers;

import javax.inject.Inject;

import models.*;
import play.data.*;
import play.mvc.*;

import views.html.*;


/**
 * ログイン認証をコントロールするクラス
 */
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


	/**
	 * 認証コントロールメソッド
	 *
	 * 認証NGであればテンプレートへエラーメッセージを渡し
	 * ログイン画面でエラーメッセージを表示
	 *
	 * 認証OKであればマイページ受信箱へリダイレクト
	 */
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


	/**
	 * logoutメソッド
	 * セッションをクリアし、ログイン画面へリダイレクト
	 */
	public Result logout() {

		session().clear();

		return redirect(routes.AuthController.login());

	}

}
