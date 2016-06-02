package controllers;

import java.text.*;
import java.util.*;

import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import com.avaje.ebean.ExpressionList;

import models.*;
import play.data.*;
import play.mvc.*;

import views.html.*;
import views.html.mypage.*;
import views.html.bbs.*;
import views.html.management.*;


/*
 * 掲示板画面をコントロールするクラス
 */
@Security.Authenticated(Secured.class)
public class BbsController extends Controller {

	@Inject
	private FormFactory formFactory;


	/**
	 * 掲示板ページを表示
	 *
	 * テンプレート側へ渡す因数
	 * ①カードリストから取得した年月
	 * ②ログインユーザー名
	 * ③ログインユーザーの権限
	 */
	public Result bbs_main() {

		List<Card> cardList = Card.find.all();

		List<String> dateAll = new ArrayList<>();

		for(int i = 0; i < cardList.size(); i++) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");

			String str = sdf.format(cardList.get(i).date);

			//頭8桁のみ取得しListへ追加
			dateAll.add(str.substring(0, 8));

		}

		//年月の重複を削除する為、HashSet化
		HashSet<String> dateSet = new HashSet<String>();
		dateSet.addAll(dateAll);


		//再度List化し降順でソート
		List<String> dateList = new ArrayList<>(dateSet);
		Collections.sort(dateList);
		Collections.reverse(dateList);


		//ログインユーザー名と権限名を取得
		String[] loginUser = new String[2];
		loginUser[0] = User.find.where().eq("userCd", session("login")).findUnique().userName;
		loginUser[1] = User.find.where().eq("userCd", session("login")).findUnique().permission.permissionName;

		return ok(bbs_main.render(loginUser, dateList));

	}


	/**
	 * 掲示板一覧を表示
	 *
	 * フィルター機能で送信されたリクエストパラメータがnullの場合は全件数を表示
	 * not nullの場合は各フィルター機能の条件で絞込を行う
	 */
	public Result bbs_cont1() {

		List<Card> cards = new ArrayList<>();

		//組み合わせ検索をする為にListではなくExpressionListクラスのインスタンスを取得する
		ExpressionList<Card> expression = Card.find.where();

		Map<String, String[]> params = request().body().asFormUrlEncoded();

		if(params != null) {

			//from部署フィルター
			if(!(params.get("fromDepartmentName")[0].equals("default"))) {

				Department department = Department.find.where().eq("departmentName", params.get("fromDepartmentName")[0]).findUnique();

				List<User> fromUserList = User.find.where().eq("department", department).findList();

				expression = expression.in("fromUser", fromUserList);

			}

			//from氏名フィルター
			if(!(params.get("fromUserName")[0].equals("default"))) {

				User fromUser = User.find.where().eq("userName", params.get("fromUserName")[0]).findUnique();

				expression = expression.eq("fromUser", fromUser);

			}

			//to部署フィルター
			if(!(params.get("toDepartmentName")[0].equals("default"))) {

				Department department = Department.find.where().eq("departmentName", params.get("toDepartmentName")[0]).findUnique();

				List<User> toUserList = User.find.where().eq("department", department).findList();

				expression = expression.in("toUser", toUserList);

			}

			//to氏名フィルター
			if(!(params.get("toUserName")[0].equals("default"))) {

				User toUser = User.find.where().eq("userName", params.get("toUserName")[0]).findUnique();

				expression = expression.eq("toUser", toUser);

			}

			//分類フィルター
			if(!(params.get("categoryName")[0].equals("default"))) {

				Category category = Category.find.where().eq("categoryName", params.get("categoryName")[0]).findUnique();

				expression = expression.eq("category", category);

			}

			//期間フィルター
			if(!(params.get("fromDate")[0].equals("")) || !(params.get("toDate")[0].equals(""))) {

				String fromDate = params.get("fromDate")[0];
				String toDate = params.get("toDate")[0];

				if(!(fromDate.equals("")) && !(toDate.equals(""))) {

					expression = expression.between("date", fromDate, toDate);

				} else if(toDate.equals("")) {

					expression = expression.ge("date", fromDate);

				} else {

					expression = expression.le("date", toDate);

				}

			}

			/*
			 * いいね 降順
			 *
			 * この時にExpressionListからList化する
			 */
			if(!(params.get("good")[0].equals("default"))) {

				cards = expression.orderBy("goodCount ASC").findList();

			} else {

				cards = expression.findList();

			}

		} else {

			//リクエストパラメータがnullの場合は全件取得
			cards = Card.find.all();

		}

		//dateの表示を"yyyy/MM/dd hh:mm:ss"形式で表示させたいので
		//List<Card>でなくList<String[]>でテンプレート側へ値を渡す入れ物を用意
		List<String[]> cardList = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		for(int i = (cards.size() - 1); i >= 0; i--) {
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


		/**
		 * 部署、分類、ユーザーの値もテンプレートへ渡す
		 * administratorは除外する
		 */
		List<Department> departmentList = Department.find.all();
		List<Category> categoryList = Category.find.all();
		List<User> userList = User.find.where().gt("id", 1).findList();;

		return ok(bbs_cont1.render(cardList, departmentList, categoryList, userList));

	}


	/**
	 * 掲示板事例
	 * テンプレート側から渡された"yyyy年mm月"形式の値を利用し
	 * 適切な月の「いいね」TOP10を表示する
	 */
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
		String toStr   = year + nextMonth + "01 00:00:00";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

		List<Card> cards = new ArrayList<>();

		try {

		Date fromDate = sdf.parse(fromStr);
		Date toDate   = sdf.parse(toStr);

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


	/**
	 * 掲示板関連
	 * 部署間のやり取り件数をマトリックス表で
	 * 表示させる為の入れ物を作成しテンプレート側へ渡す
	 */
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
					Department to   = Department.find.where().eq("id", j).findUnique();

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
