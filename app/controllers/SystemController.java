package controllers;

import java.util.List;

import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import models.*;
import play.data.*;
import play.mvc.*;

import views.html.system.*;

public class SystemController extends Controller {

	@Inject
	private FormFactory formFactory;


	//systemメインページ -> cardメインへリダイレクト
	public Result system_main() {
		return redirect(routes.SystemController.card_main());
	}


	//card メインページ
	public Result card_main() {
		return ok(card_main.render());
	}
	//card TOPページ
	public Result card_cont1() {
		return TODO;
	}
	//card 作成ページ
	public Result card_cont2() {
		return ok(card_cont2.render(formFactory.form(Card.class)));
		//return TODO;
	}
	//card 作成
	public Result createCard() {
		Card newCard = formFactory.form(Card.class).bindFromRequest().get();
		newCard.save();
		return redirect(routes.SystemController.card_cont1());
	}


	//category メインページ
	public Result category_main() {
		return ok(category_main.render());
	}
	//category TOPページ
	public Result category_cont1() {
		return TODO;
	}
	//category 作成ページ
	public Result category_cont2() {
		return ok(category_cont2.render(formFactory.form(Category.class)));
	}
	//category 作成
	public Result createCategory() {
		Category newCategory = formFactory.form(Category.class).bindFromRequest().get();
		newCategory.save();
		return redirect(routes.SystemController.category_cont1());
	}


	//department メインページ
	public Result department_main() {
		return ok(department_main.render());
	}
	//department TOPページ
	public Result department_cont1() {
		return TODO;
	}
	//department 作成ページ
	public Result department_cont2() {
		return ok(department_cont2.render(formFactory.form(Department.class)));
	}
	//department 作成
	public Result createDepartment() {
		Department newDepartment = formFactory.form(Department.class).bindFromRequest().get();
		newDepartment.save();
		return redirect(routes.SystemController.department_cont1());
	}


	//permission メインページ
	public Result permission_main() {
		return ok(permission_main.render());
	}
	//permission TOPページ
	public Result permission_cont1() {
		return ok(permission_cont1.render());
	}
	//permission 作成ページ
	public Result permission_cont2() {
		return ok(permission_cont2.render(formFactory.form(Permission.class)));
	}
	//department 作成
	public Result createPermission() {
		Permission newPermission = formFactory.form(Permission.class).bindFromRequest().get();
		newPermission.save();
		return redirect(routes.SystemController.permission_cont1());
	}


	//section メインページ
	public Result section_main() {
		return ok(section_main.render());
	}
	//section TOPページ
	public Result section_cont1() {
		return ok(section_cont1.render());
	}
	//section 作成ページ
	public Result section_cont2() {
		return ok(section_cont2.render(formFactory.form(Section.class)));
	}
	//section 作成
	public Result createSection() {
		Section newSection = formFactory.form(Section.class).bindFromRequest().get();
		newSection.save();
		return redirect(routes.SystemController.section_cont1());
	}


	//user メインページ
	public Result user_main() {
		return ok(user_main.render());
	}
	//user TOPページ
	public Result user_cont1() {
		return ok(user_cont1.render());
	}
	//user 作成ページ
	public Result user_cont2() {
		return ok(user_cont2.render(formFactory.form(User.class)));
	}
	//user 作成
	public Result createUser() {
		User newUser = formFactory.form(User.class).bindFromRequest().get();
		newUser.userPass = BCrypt.hashpw(newUser.userPass, BCrypt.gensalt());
		newUser.save();
		return redirect(routes.SystemController.user_cont1());
	}


	//集計テーブル メインページ
	public Result master_main() {
		return ok(master_main.render());
	}
	//集計テーブル TOPページ
	public Result master_cont1() {
		return ok(master_cont1.render());
	}

}
