package controllers;

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

  //管理者設定ページを表示
    public Result management_main() {
    	return ok(management_main.render());
    }

}
