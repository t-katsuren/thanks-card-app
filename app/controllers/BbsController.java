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
public class BbsController extends Controller {

	@Inject
	private FormFactory formFactory;


	//掲示板ページを表示
	public Result bbs_main() {

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
        Collections.reverse(dateList);

        String[] loginUser = new String[2];

		loginUser[0] = User.find.where().eq("userCd", session("login")).findUnique().userName;

		loginUser[1] = User.find.where().eq("userCd", session("login")).findUnique().permission.permissionName;

		return ok(bbs_main.render(loginUser, dateList));

	}


	//掲示板一覧
	public Result bbs_cont1() {

		List<Card> cards = Card.find.all();

		Map<String, String[]> params = request().body().asFormUrlEncoded();

		if(params != null) {

			//from部署フィルター
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

			//from氏名フィルター
			if(!(params.get("fromUserName")[0].equals("default"))) {
				User fromUser = User.find.where().eq("userName", params.get("fromUserName")[0]).findUnique();
				cards = Card.find.where().eq("fromUser", fromUser).findList();
			}

			//to部署フィルター
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

			//to氏名フィルター
			if(!(params.get("toUserName")[0].equals("default"))) {
				User toUser = User.find.where().eq("userName", params.get("toUserName")[0]).findUnique();
				cards = Card.find.where().eq("toUser", toUser).findList();
			}

			//分類フィルター
			if(!(params.get("categoryName")[0].equals("default"))) {
				Category category = Category.find.where().eq("categoryName", params.get("categoryName")[0]).findUnique();
				cards = Card.find.where().eq("category", category).findList();
			}

			//期間フィルター
			if(!(params.get("fromDate")[0].equals("")) || !(params.get("toDate")[0].equals(""))) {
				String fromDate = params.get("fromDate")[0];
				String toDate = params.get("toDate")[0];
				if(!(fromDate.equals("")) && !(toDate.equals(""))) {
					cards = Card.find.where().between("date", fromDate, toDate).findList();
				} else if(toDate.equals("")) {
					cards = Card.find.where().ge("date", fromDate).findList();
				} else {
					cards = Card.find.where().le("date", toDate).findList();
				}
			}

			//いいね 降順
			if(!(params.get("good")[0].equals("default"))) {
				cards = Card.find.where().orderBy("goodCount ASC").findList();
			}

		}

		List<String[]> cardList = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		for(int i = cards.size()-1; i >=0; i--) {
			String[] temp = new String[9];

			temp[0] = String.valueOf(cards.get(i).id);
			temp[1] = cards.get(i).fromUser.department.departmentName;
			temp[2] = cards.get(i).fromUser.userName;
			temp[3] = cards.get(i).toUser.department.departmentName;
			temp[4] = cards.get(i).toUser.userName;
			temp[5] = cards.get(i).category.categoryName;
			temp[6] = cards.get(i).title;
			temp[7] = sdf.format(cards.get(i).date);
			temp[8] = String.valueOf(cards.get(i).goodCount);

			cardList.add(temp);
		}

		List<Department> departmentList = Department.find.all();

		List<Category> categoryList = Category.find.all();

		List<User> userList = User.find.where().gt("id", 1).findList();;

		return ok(bbs_cont1.render(cardList, departmentList, categoryList, userList));

	}


	//掲示板事例
	public Result bbs_cont2(String date) {

		String year = date.substring(0, 4);
		String month = date.substring(5, 7);

		String nextMonth = "";

		if((Integer.parseInt(month) + 1) < 10) {
			nextMonth = "0" + (Integer.parseInt(month) + 1);
		} else {
			nextMonth = "" + (Integer.parseInt(month) + 1);
		}

		String fromStr = year + month + "01 00:00:00";
		String toStr = year + nextMonth + "01 00:00:00";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

		List<Card> cards = new ArrayList<>();

		try {

		Date fromDate = sdf.parse(fromStr);
		Date toDate = sdf.parse(toStr);

		cards = Card.find.where().ge("date", fromDate).lt("date", toDate).orderBy("goodCount DESC").findList();

		} catch(ParseException e) {
			e.printStackTrace();
		}

		List<String[]> cardList = new ArrayList<>();

		sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		for(int i = 0; i < cards.size(); i++) {
			String[] temp = new String[10];

			temp[0] = String.valueOf(cards.get(i).id);
			temp[1] = String.valueOf(i+1);
			temp[2] = cards.get(i).fromUser.department.departmentName;
			temp[3] = cards.get(i).fromUser.userName;
			temp[4] = cards.get(i).toUser.department.departmentName;
			temp[5] = cards.get(i).toUser.userName;
			temp[6] = cards.get(i).category.categoryName;
			temp[7] = cards.get(i).title;
			temp[8] = sdf.format(cards.get(i).date);
			temp[9] = String.valueOf(cards.get(i).goodCount);

			cardList.add(temp);

			if(i == 9) {
				break;
			}
		}

		return ok(bbs_cont2.render(cardList, date));

	}


	//掲示板関連
	public Result bbs_cont3() {

		List<Department> departmentList = Department.find.all();

		List<String[]> relationList = new ArrayList<>();

		int size = departmentList.size();

		for(int i = 1; i <= size; i++) {

			String[] temp = new String[size+1];

			for(int j = 0; j <= size; j++) {

				if(j == 0) {

					temp[j] = departmentList.get(i-1).departmentName;

				} else {

					Department from = Department.find.where().eq("id", i).findUnique();
					Department to = Department.find.where().eq("id", j).findUnique();

					List<User> fromUser = User.find.where().eq("department", from).findList();
					List<User> toUser   = User.find.where().eq("department", to).findList();

					List<Card> cards = Card.find.where().in("fromUser", fromUser).in("toUser", toUser).findList();

					Integer count = cards.size();

					temp[j] = String.valueOf(count);

				}

			}

			relationList.add(temp);

		}

		return ok(bbs_cont3.render(relationList, departmentList));

	}

}
