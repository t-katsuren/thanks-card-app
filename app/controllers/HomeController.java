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

	//感謝カードを開く
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

	//「いいね」ボタンが押されたらGoodCountを足す
	public Result addGoodCount(String cardId) {

		Integer id = Integer.parseInt(cardId);

		Card card = Card.find.byId(id);

		card.goodCount = card.goodCount + 1;

		card.save();

		return redirect(routes.MypageController.mypage_cont1());

	}

	public Result appError() {

		String[] loginUser = new String[2];

		loginUser[0] = User.find.where().eq("userCd", session("login")).findUnique().userName;

		loginUser[1] = User.find.where().eq("userCd", session("login")).findUnique().permission.permissionName;

		return badRequest(app_error.render(loginUser));

	}

}
