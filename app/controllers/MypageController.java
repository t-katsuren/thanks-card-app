package controllers;

import java.text.*;
import java.util.*;

import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import models.*;
import play.data.*;
import play.mvc.*;

import views.html.*;
import views.html.mypage.*;
import views.html.bbs.*;
import views.html.management.*;

@Security.Authenticated(Secured.class)
public class MypageController extends Controller {

	@Inject
	private FormFactory formFactory;


	//マイページを表示
	public Result mypage_main() {

		String loginUserName = User.find.where().eq("userCd", session("login")).findUnique().userName;

		return ok(mypage_main.render(loginUserName));

	}


	//マイページ受信箱
	public Result mypage_cont1() {

		List<Card> cards = Card.find.all();

		String userCd = session("login");

		User nowUser = User.find.where().eq("userCd", userCd).findUnique();

		cards = Card.find.where().eq("toUser", nowUser).findList();

		List<String[]> cardList = new ArrayList<>();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		for(int i = 0; i < cards.size(); i++) {
			String[] temp = new String[8];

			temp[0] = cards.get(i).fromUser.department.departmentName;
			temp[1] = cards.get(i).fromUser.userName;
			temp[2] = cards.get(i).toUser.department.departmentName;
			temp[3] = cards.get(i).toUser.userName;
			temp[4] = cards.get(i).category.categoryName;
			temp[5] = cards.get(i).title;
			temp[6] = formatter.format(cards.get(i).date);
			temp[7] = String.valueOf(cards.get(i).goodCount);

			cardList.add(temp);
		}

		return ok(mypage_cont1.render(cardList));

	}


	//マイページ送信箱
	public Result mypage_cont2() {

		List<Card> cards = Card.find.all();

		String userCd = session("login");

		User nowUser = User.find.where().eq("userCd", userCd).findUnique();

		cards = Card.find.where().eq("fromUser", nowUser).findList();

		List<String[]> cardList = new ArrayList<>();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		for(int i = 0; i < cards.size(); i++) {
			String[] temp = new String[8];

			temp[0] = cards.get(i).fromUser.department.departmentName;
			temp[1] = cards.get(i).fromUser.userName;
			temp[2] = cards.get(i).toUser.department.departmentName;
			temp[3] = cards.get(i).toUser.userName;
			temp[4] = cards.get(i).category.categoryName;
			temp[5] = cards.get(i).title;
			temp[6] = formatter.format(cards.get(i).date);
			temp[7] = String.valueOf(cards.get(i).goodCount);

			cardList.add(temp);
		}

		return ok(mypage_cont2.render(cardList));

	}


	//感謝カード作成
	public Result mypage_cont3() {

		List<User> userList = User.find.all();

		List<Department> departmentList = Department.find.all();

		List<Category> categoryList = Category.find.all();

		return ok(mypage_cont3.render(userList, departmentList, categoryList));

	}


	//感謝カード送信
	public Result sendCard() {

		Map<String, String[]> params = request().body().asFormUrlEncoded();

		Card newCard = new Card();

		newCard.date      = new Date();
		newCard.fromUser  = User.find.where().eq("userCd", session("login")).findUnique();
		newCard.toUser    = User.find.where().eq("userName", params.get("toUserName")[0]).findUnique();
		newCard.title     = params.get("title")[0];    //<input type="text" name="title" /> に入力された値
		newCard.detail    = params.get("detail")[0];   //<input type="text" name="detail" /> に入力された値
		newCard.message   = params.get("message")[0];  //<input type="text" name="message" /> に入力された値
		newCard.category  = Category.find.where().eq("categoryName", params.get("category")[0]).findUnique();
		newCard.goodCount = 0;

		newCard.save();

		return redirect(routes.MypageController.mypage_cont3());

	}


}
