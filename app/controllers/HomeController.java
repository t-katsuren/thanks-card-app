package controllers;

import java.text.SimpleDateFormat;
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

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
@Security.Authenticated(Secured.class)
public class HomeController extends Controller {

	@Inject
	private FormFactory formFactory;


	/**
	* An action that renders an HTML page with a welcome message.
	* The configuration in the <code>routes</code> file means that
	* this method will be called when the application receives a
	* <code>GET</code> request with a path of <code>/</code>.
	*/
	public Result index() {
		return ok(index.render("Your new application is ready."));
	}



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
		List<User> listUser = User.find.all();
		List<Department> listDepartment = Department.find.all();
		List<Category> listCategory = Category.find.all();
		return ok(mypage_cont3.render(listUser,listCategory,listDepartment));
	}



	/*一旦コメントアウト
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
	*/




	//管理者設定ページを表示
	public Result management_main() {

		String loginUserName = User.find.where().eq("userCd", session("login")).findUnique().userName;

		return ok(management_main.render(loginUserName));

	}
	//社員設定
	public Result management_cont1() {

		List<User> userList = User.find.all();

		return ok(management_cont1.render(userList, formFactory.form(User.class)));

	}
	//社員削除
	public Result deleteUser(Integer userId){

		User.find.deleteById(userId);

		return redirect(routes.HomeController.management_cont1());

	}
	//社員追加
	public Result createUser(){

		Map<String, String[]> params = request().body().asFormUrlEncoded();

		User newUser = new User();

		newUser.userCd = params.get("userCd")[0];
		newUser.userPass = BCrypt.hashpw(params.get("userPass")[0], BCrypt.gensalt());

		String departmentName = params.get("departmentName")[0];
		newUser.department = Department.find.where().eq("departmentName", departmentName).findUnique();

		newUser.userName = params.get("userName")[0];

		String permissionName = params.get("permissionName")[0];
		newUser.permission = Permission.find.where().eq("permissionName", permissionName).findUnique();

		newUser.save();

		return redirect(routes.HomeController.management_cont1());

	}
	//所属設定
	public Result management_cont2() {

		List<Section> sectionList = Section.find.all();

		List<Department> departmentList = Department.find.all();

		return ok(management_cont2.render(sectionList, departmentList));

	}
	//部門削除
	public Result deleteSection(Integer sectionId){

		Section.find.byId(sectionId).delete();

		return redirect(routes.HomeController.management_cont2());

	}

	//部門追加
	public Result createSection(){

		Map<String, String[]> parms = request().body().asFormUrlEncoded();

		Section newSection = new Section();

		newSection.sectionCd = parms.get("sectionCd")[0];

		newSection.sectionName = parms.get("sectionName")[0];

		newSection.save();

		return redirect(routes.HomeController.management_cont2());

	}
	//部署削除
	public Result deleteDepartment(Integer departmentId){

		Department.find.byId(departmentId).delete();

		return redirect(routes.HomeController.management_cont2());

	}
	//部署追加
	public Result createDepartment(){
		Map<String, String[]> parms = request().body().asFormUrlEncoded();

		Department newDepartment = new Department();

		newDepartment.departmentCd = parms.get("departmentCd")[0];

		String sectionName = parms.get("sectionName")[0];
		newDepartment.section = Section.find.where().eq("sectionName", sectionName).findUnique();

		newDepartment.departmentName = parms.get("departmentName")[0];

		newDepartment.save();

		return redirect(routes.HomeController.management_cont2());
	}

	//分類設定
	public Result management_cont3() {

		List<Category> categoryList = Category.find.all();

		return ok(management_cont3.render(categoryList));

	}
	//分類削除
	public Result deleteCategory(Integer categoryId){

		Category.find.byId(categoryId).delete();

		return redirect(routes.HomeController.management_cont3());

	}

	//分類追加
	public Result createCategory(){

		Map<String, String[]> parms = request().body().asFormUrlEncoded();

		Category newCategory = new Category();

		newCategory.categoryCd = parms.get("categoryCd")[0];

		newCategory.categoryName = parms.get("categoryName")[0];

		newCategory.save();

		return redirect(routes.HomeController.management_cont3());

	}

	public Result sendCard() {
	    Map<String, String[]> params = request().body().asFormUrlEncoded();
	    Card newCard = new Card();
	    newCard.date = new Date();
	    newCard.fromUser= User.find.where().eq("userCd", session("login")).findUnique();;
	    newCard.toUser = User.find.where().eq("userName", params.get("toUserName")[0]).findUnique();;
	    newCard.title = params.get("title")[0]; // <input type="text" name="title" /> に入力された値
	    newCard.detail = params.get("detail")[0]; // <input type="text" name="detail" /> に入力された値
	    newCard.message = params.get("message")[0]; // <input type="text" name="message" /> に入力された値
	    newCard.category = Category.find.where().eq("categoryName", params.get("category")[0]).findUnique();
	    newCard.goodCount = 0;
	    newCard.save();
	    return redirect(routes.HomeController.mypage_cont3());
	}

}
