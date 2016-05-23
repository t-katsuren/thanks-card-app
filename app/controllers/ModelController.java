package controllers;

import javax.inject.Inject;

import models.*;
import play.data.*;
import play.mvc.*;

import views.html.modelviews.*;

public class ModelController extends Controller {

	@Inject
	private FormFactory formFactory;

	//cardページ
	public Result card() {
		return ok(card.render(formFactory.form(Card.class)));
	}
	//card作成
	public Result createCard() {
		Card newCard = formFactory.form(Card.class).bindFromRequest().get();
		newCard.save();
		return redirect(routes.ModelController.card());
	}

	//categoryページ
	public Result category() {
		return ok(category.render(formFactory.form(Category.class)));
	}
	//category作成
	public Result createCategory() {
		Category newCategory = formFactory.form(Category.class).bindFromRequest().get();
		newCategory.save();
		return redirect(routes.ModelController.category());
	}

	//departmentページ
	public Result department() {
		return ok(department.render(formFactory.form(Department.class)));
	}
	//department作成
	public Result createDepartment() {
		Department newDepartment = formFactory.form(Department.class).bindFromRequest().get();
		newDepartment.save();
		return redirect(routes.ModelController.department());
	}

	//permissionページ
	public Result permission() {
		return ok(permission.render(formFactory.form(Permission.class)));
	}
	//permission作成
	public Result createPermission() {
		Permission newPermission = formFactory.form(Permission.class).bindFromRequest().get();
		newPermission.save();
		return redirect(routes.ModelController.permission());
	}

	//sectionページ
	public Result section() {
		return ok(section.render(formFactory.form(Section.class)));
	}
	//section作成
	public Result createSection() {
		Section newSection = formFactory.form(Section.class).bindFromRequest().get();
		newSection.save();
		return redirect(routes.ModelController.section());
	}

	//userページ
	public Result user() {
		return ok(user.render(formFactory.form(User.class)));
	}
	//user作成
	public Result createUser() {
		User newUser = formFactory.form(User.class).bindFromRequest().get();
		newUser.save();
		return redirect(routes.ModelController.user());
	}

}
