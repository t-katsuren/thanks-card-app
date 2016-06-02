package controllers;

import java.util.*;

import models.*;
import play.mvc.*;

import views.html.*;
import views.html.errors.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
@Security.Authenticated(Secured.class)
public class HomeController extends Controller {

	/**
	* An action that renders an HTML page with a welcome message.
	* The configuration in the <code>routes</code> file means that
	* this method will be called when the application receives a
	* <code>GET</code> request with a path of <code>/</code>.
	*/
	public Result index() {
		return ok(index.render("Your new application is ready."));
	}


	/**
	 * 感謝カードを開く
	 * 感謝カード画面へ表示させる各値をListへ格納しテンプレートへ渡す
	 */
	public Result openCard(String cardId) {

		Integer id = Integer.parseInt(cardId);

		Card card = Card.find.byId(id);

		List<String> contentsList = new ArrayList<>();

		contentsList.add(String.valueOf(card.id));
		contentsList.add(card.fromUser.department.departmentName);
		contentsList.add(card.fromUser.userName);
		contentsList.add(card.toUser.department.departmentName);
		contentsList.add(card.toUser.userName);
		contentsList.add(card.category.categoryName);
		contentsList.add(card.title);
		contentsList.add(card.detail);
		contentsList.add(card.message);

		return ok(open_card.render(contentsList));

	}


	/**
	 * 「いいね」ボタンが押されたらGoodCountを足す
	 * マイページ受信一覧から押された場合元の受信一覧へリダイレクト
	 */
	public Result addGoodCount_MyCont1(String cardId) {

		Integer id = Integer.parseInt(cardId);

		Card card = Card.find.byId(id);

		card.goodCount = card.goodCount + 1;

		card.save();

		return redirect(routes.MypageController.mypage_cont1());

	}


	/**
	 * 「いいね」ボタンが押されたらGoodCountを足す
	 * マイページ送信一覧から押された場合元の送信一覧へリダイレクト
	 */
	public Result addGoodCount_MyCont2(String cardId) {

		Integer id = Integer.parseInt(cardId);

		Card card = Card.find.byId(id);

		card.goodCount = card.goodCount + 1;

		card.save();

		return redirect(routes.MypageController.mypage_cont2());

	}


	/**
	 * 「いいね」ボタンが押されたらGoodCountを足す
	 * 掲示板一覧から押された場合元の掲示板一覧へリダイレクト
	 */
	public Result addGoodCount_BbsCont1(String cardId) {

		Integer id = Integer.parseInt(cardId);

		Card card = Card.find.byId(id);

		card.goodCount = card.goodCount + 1;

		card.save();

		return redirect(routes.BbsController.bbs_cont1());

	}


	/**
	 * アプリケーション管理エラー
	 * 一般ユーザーが管理者設定画面を開くとエラーメッセージ画面へ飛ぶ
	 */
	public Result appError() {

		String[] loginUser = new String[2];

		loginUser[0] = User.find.where().eq("userCd", session("login")).findUnique().userName;

		loginUser[1] = User.find.where().eq("userCd", session("login")).findUnique().permission.permissionName;

		return badRequest(app_error.render(loginUser));

	}


	/**
	 * システム管理エラー
	 * システム管理者以外がシステム画面を開くとエラーメッセージ画面へ飛ぶ
	 */
	public Result systemError() {

		String[] loginUser = new String[2];

		loginUser[0] = User.find.where().eq("userCd", session("login")).findUnique().userName;

		loginUser[1] = User.find.where().eq("userCd", session("login")).findUnique().permission.permissionName;

		return badRequest(system_error.render(loginUser));

	}


	/**
	 * コード重複エラー
	 * 既に存在するコードを登録しようとするとエラーメッセージ画面へ飛ぶ
	 */
	public Result cdError(String str) {

		return badRequest(cd_error.render(str));

	}

}
