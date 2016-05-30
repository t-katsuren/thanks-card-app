package controllers;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.inject.Inject;

import models.*;
import play.data.*;
import play.mvc.*;

import views.html.*;
import views.html.mypage.*;
import views.html.bbs.*;
import views.html.management.*;

public class ModelController extends Controller {

	@Inject
	private FormFactory formFactory;

	//掲示板ページを表示
		public Result bbs_main() {

			String loginUserName = User.find.where().eq("userCd", session("login")).findUnique().userName;

			return ok(bbs_main.render(loginUserName));

		}
		//掲示板一覧
		public Result bbs_cont1() {

			List<Card> cards = Card.find.all();

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

			return ok(bbs_cont1.render(cardList));

		}
		//掲示板事例1
		public Result bbs_cont2() {



			return ok(bbs_cont2.render());

		}
		//掲示板関連
		public Result bbs_cont3() {

			return ok(bbs_cont3.render());

		}
		//掲示板事例2
		public Result bbs_cont4() {

			return ok(bbs_cont4.render());

		}

}
