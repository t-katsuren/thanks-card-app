package controllers;

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


	//cardメインページ
	public Result card_main() {
		return ok(card_main.render());
	}
	//cardTOPページ
	public Result card_top() {
		return TODO;
	}
	//card作成ページ
	public Result card_create() {
		return TODO;
	}
	//card作成
	public Result createCard() {
		Card newCard = formFactory.form(Card.class).bindFromRequest().get();
		newCard.save();
		return redirect(routes.SystemController.card_main());
	}


	//categoryメインページ
	public Result category_main() {
		return ok(category_main.render());
	}
	//categoryTOPページ
	public Result category_top() {
		return TODO;
	}
	//category作成ページ
	public Result category_create() {
		return TODO;
	}
	//category作成
	public Result createCategory() {
		Category newCategory = formFactory.form(Category.class).bindFromRequest().get();
		newCategory.save();
		return redirect(routes.SystemController.category_main());
	}


	//departmentメインページ
	public Result department_main() {
		return ok(department_main.render());
	}
	//departmentTOPページ
	public Result department_top() {
		return TODO;
	}
	//department作成ページ
	public Result department_create() {
		return TODO;
	}
	//department作成
	public Result createDepartment() {
		Department newDepartment = formFactory.form(Department.class).bindFromRequest().get();
		newDepartment.save();
		return redirect(routes.SystemController.department_main());
	}


	//permissionメインページ
	public Result permission_main() {
		return ok(permission_main.render());
	}
	//permissionTOPページ
	public Result permission_top() {
		return TODO;
	}
	//permission作成ページ
	public Result permission_create() {
		return TODO;
	}
	//department作成
	public Result createPermission() {
		Permission newPermission = formFactory.form(Permission.class).bindFromRequest().get();
		newPermission.save();
		return redirect(routes.SystemController.permission_main());
	}


	//sectionメインページ
	public Result section_main() {
		return ok(section_main.render());
	}
	//sectionTOPページ
	public Result section_top() {
		return TODO;
	}
	//section作成ページ
	public Result section_create() {
		return TODO;
	}
	//section作成
	public Result createSection() {
		Section newSection = formFactory.form(Section.class).bindFromRequest().get();
		newSection.save();
		return redirect(routes.SystemController.section_main());
	}


	//userメインページ
	public Result user_main() {
		return ok(user_main.render());
	}
	//userTOPページ
	public Result user_top() {
		return TODO;
	}
	//user作成ページ
	public Result user_create() {
		return TODO;
	}
	//user作成
	public Result createUser() {
		User newUser = formFactory.form(User.class).bindFromRequest().get();
		newUser.userPass = BCrypt.hashpw(newUser.userPass, BCrypt.gensalt());
		newUser.save();
		return redirect(routes.SystemController.user_main());
	}

}
