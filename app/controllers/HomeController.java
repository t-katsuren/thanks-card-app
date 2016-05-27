package controllers;

import java.util.*;

import javax.inject.Inject;

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
//@Security.Authenticated(Secured.class)
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
    	return ok(mypage_main.render());
    }
    //マイページ受信箱
    public Result mypage_cont1() {
    	return ok(mypage_cont1.render());
    }
    //マイページ受信箱
    public Result mypage_cont2() {
    	return ok(mypage_cont2.render());
    }
    //マイページ受信箱
    public Result mypage_cont3() {
    	return ok(mypage_cont3.render());
    }


    //掲示板ページを表示
    public Result bbs_main() {
    	return ok(bbs_main.render());
    }
    //掲示板一覧
    public Result bbs_cont1() {
    	List<Card> cardList = Card.find.all();
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


    //管理者設定ページを表示
    public Result management_main() {
    	return ok(management_main.render());
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
    	User newUser = formFactory.form(User.class).bindFromRequest().get();
    	newUser.save();
    	return redirect(routes.HomeController.management_cont1());
    }
    //所属設定
    public Result management_cont2() {
    	List<Section> sectionList = Section.find.all();
    	List<Department> departmentList=Department.find.all();
    	return ok(management_cont2.render(sectionList, departmentList));
    }
    //分類設定
    public Result management_cont3() {
    	List<Category> categoryList = Category.find.all();
    	return ok(management_cont3.render(categoryList));
    }

}
