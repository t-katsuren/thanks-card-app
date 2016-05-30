package controllers;

import java.text.*;
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

			Map<String, String[]> params = request().body().asFormUrlEncoded();

			if(params != null) {

				if(!(params.get("fromDepartmentName")[0].equals("default"))) {
					Department department = Department.find.where().eq("departmentName", params.get("fromDepartmentName")[0]).findUnique();
					List<User> fromUserList = User.find.where().eq("department", department).findList();
					List<Card> temp = new ArrayList<>();
					for(int i = 0; i < fromUserList.size(); i++) {
						List<Card> temp1 = Card.find.where().eq("fromUser", fromUserList.get(i)).findList();
						for(int j = 0; j < temp1.size(); j++) {
							temp.add(temp1.get(j));
						}
					}
					cards = temp;
				}

				if(!(params.get("fromUserName")[0].equals(""))) {
					User fromUser = User.find.where().eq("userName", params.get("fromUserName")[0]).findUnique();
					cards = Card.find.where().eq("fromUser", fromUser).findList();
				}

				if(!(params.get("toDepartmentName")[0].equals("default"))) {
					Department department = Department.find.where().eq("departmentName", params.get("toDepartmentName")[0]).findUnique();
					List<User> toUserList = User.find.where().eq("department", department).findList();
					List<Card> temp = new ArrayList<>();
					for(int i = 0; i < toUserList.size(); i++) {
						List<Card> temp1 = Card.find.where().eq("toUser", toUserList.get(i)).findList();
						for(int j = 0; j < temp1.size(); j++) {
							temp.add(temp1.get(j));
						}
					}
					cards = temp;
				}

				if(!(params.get("toUserName")[0].equals(""))) {
					User toUser = User.find.where().eq("userName", params.get("toUserName")[0]).findUnique();
					cards = Card.find.where().eq("toUser", toUser).findList();
				}

				if(!(params.get("categoryName")[0].equals("default"))) {
					Category category = Category.find.where().eq("categoryName", params.get("categoryName")[0]).findUnique();
					cards = Card.find.where().eq("category", category).findList();
				}
				//date
				/*
				if(!(params.get("fromDate")[0].equals("default"))) {
					String fromDate = params.get("fromDate")[0];
					String toDate = params.get("toDate")[0];
					cards =Ebean.createSqlQuery("SELECT * FROM Card WHERE date between date fromDate and date toDate;" ).findList();
				}*/


				//いいね　降順
				if(!(params.get("good")[0].equals("default"))) {
					cards = Card.find.where().orderBy("goodCount DESC").findList();

				}
			}

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

			List<Department> departmentList = Department.find.all();

			List<Category> categoryList = Category.find.all();

			return ok(bbs_cont1.render(cardList, departmentList, categoryList));

		}
		//掲示板事例1
		public Result bbs_cont2() {

			List<Card> cardList = Card.find.all();

			List<String> dateAll = new ArrayList<>();

			for(int i = 0; i < cardList.size(); i++) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
				String str = sdf.format(cardList.get(i).date);
				dateAll.add(str.substring(0, 8));
			}

			HashSet<String> dateSet = new HashSet<String>();
	        dateSet.addAll(dateAll);

	        List<String> dateList = new ArrayList<>(dateSet);

	        Collections.sort(dateList);

			return ok(bbs_cont2.render(dateList));

		}
		//掲示板関連
		public Result bbs_cont3() {

			return ok(bbs_cont3.render());

		}
		//掲示板事例2
		public Result bbs_cont4(String date) {

			String year = date.substring(0, 4);
			String month = date.substring(5, 7);

			int monthInt = Integer.parseInt(month) + 1;

			String nextMonth = "";

			if(monthInt < 10) {
				nextMonth = "0" + monthInt;
			} else {
				nextMonth = "" + monthInt;
			}

			String fromStr = year + month + "01 00:00:00";
			String toStr = year + nextMonth + "01 00:00:00";

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

			List<Card> cardList = new ArrayList<>();

			try {

			Date fromDate = sdf.parse(fromStr);
			Date toDate = sdf.parse(toStr);

			cardList = Card.find.where().ge("date", fromDate).lt("date", toDate).findList();

			} catch(ParseException e) {
				e.printStackTrace();
			}

			return ok(bbs_cont4.render(cardList));

		}

}
