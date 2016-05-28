package controllers;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import models.*;
import play.data.*;
import play.mvc.*;

import views.html.system.*;

@Security.Authenticated(Secured.class)
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
		List<Card> cards = Card.find.all();
		List<String[]> cardList = new ArrayList<>();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		for(int i = 0; i < cards.size(); i++) {
			String[] temp = new String[9];

			temp[0] = String.valueOf(cards.get(i).id);
			temp[1] = formatter.format(cards.get(i).date);
			temp[2] = String.valueOf(cards.get(i).fromUser.id);
			temp[3] = String.valueOf(cards.get(i).toUser.id);
			temp[4] = cards.get(i).title;
			temp[5] = cards.get(i).detail;
			temp[6] = cards.get(i).message;
			temp[7] = String.valueOf(cards.get(i).category.id);
			temp[8] = String.valueOf(cards.get(i).goodCount);

			cardList.add(temp);
		}

		return ok(card_cont1.render(cardList));
	}
	//card 作成ページ
	public Result card_cont2() {
		return ok(card_cont2.render(formFactory.form(Card.class)));
	}
	//card 作成
	public Result createCard() {
		Card newCard = formFactory.form(Card.class).bindFromRequest().get();
		newCard.save();
		return redirect(routes.SystemController.card_cont1());
	}
	//card 削除
	public Result deleteCard(Integer cardId) {
		Card.find.deleteById(cardId);
		return redirect(routes.SystemController.card_cont1());
	}




	//category メインページ
	public Result category_main() {
		return ok(category_main.render());
	}
	//category TOPページ
	public Result category_cont1() {
		List<Category> categoryList = Category.find.all();
		return ok(category_cont1.render(categoryList));
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
	//category 削除
	public Result deleteCategory(Integer categoryId) {
		Category.find.deleteById(categoryId);
		return redirect(routes.SystemController.category_cont1());
	}




	//department メインページ
	public Result department_main() {
		return ok(department_main.render());
	}
	//department TOPページ
	public Result department_cont1() {
		List<Department> departmentList = Department.find.all();
		return ok(department_cont1.render(departmentList));
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
	//department 削除
	public Result deleteDepartment(Integer departmentId) {
		Department.find.deleteById(departmentId);
		return redirect(routes.SystemController.department_cont1());
	}




	//permission メインページ
	public Result permission_main() {
		return ok(permission_main.render());
	}
	//permission TOPページ
	public Result permission_cont1() {
		List<Permission> permissionList = Permission.find.all();
		return ok(permission_cont1.render(permissionList));
	}
	//permission 作成ページ
	public Result permission_cont2() {
		return ok(permission_cont2.render(formFactory.form(Permission.class)));
	}
	//permission 作成
	public Result createPermission() {
		Permission newPermission = formFactory.form(Permission.class).bindFromRequest().get();
		newPermission.save();
		return redirect(routes.SystemController.permission_cont1());
	}
	//permission 削除
	public Result deletePermission(Integer permissionId) {
		Permission.find.deleteById(permissionId);
		return redirect(routes.SystemController.permission_cont1());
	}




	//section メインページ
	public Result section_main() {
		return ok(section_main.render());
	}
	//section TOPページ
	public Result section_cont1() {
		List<Section> sectionList = Section.find.all();
		return ok(section_cont1.render(sectionList));
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
	//section 削除
	public Result deleteSection(Integer sectionId) {
		Section.find.deleteById(sectionId);
		return redirect(routes.SystemController.section_cont1());
	}




	//user メインページ
	public Result user_main() {
		return ok(user_main.render());
	}
	//user TOPページ
	public Result user_cont1() {
		List<User> userList = User.find.all();
		return ok(user_cont1.render(userList));
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
	//user 削除
	public Result deleteUser(Integer userId) {
		User.find.deleteById(userId);
		return redirect(routes.SystemController.user_cont1());
	}




	//Master メインページ
	public Result master_main() {
		return ok(master_main.render());
	}
	//Master TOPページ
	public Result master_cont1() {
		return ok(master_cont1.render());
	}

}
