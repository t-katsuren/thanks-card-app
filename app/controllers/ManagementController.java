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
public class ManagementController extends Controller {

	@Inject
	private FormFactory formFactory;


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

		return redirect(routes.ManagementController.management_cont1());

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

		return redirect(routes.ManagementController.management_cont1());

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

		return redirect(routes.ManagementController.management_cont2());

	}

	//部門追加
	public Result createSection(){

		Map<String, String[]> parms = request().body().asFormUrlEncoded();

		Section newSection = new Section();

		newSection.sectionCd = parms.get("sectionCd")[0];

		newSection.sectionName = parms.get("sectionName")[0];

		newSection.save();

		return redirect(routes.ManagementController.management_cont2());

	}

	//部署削除
	public Result deleteDepartment(Integer departmentId){

		Department.find.byId(departmentId).delete();

		return redirect(routes.ManagementController.management_cont2());

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

		return redirect(routes.ManagementController.management_cont2());
	}


	//分類設定
	public Result management_cont3() {

		List<Category> categoryList = Category.find.all();

		return ok(management_cont3.render(categoryList));

	}

	//分類削除
	public Result deleteCategory(Integer categoryId){

		Category.find.byId(categoryId).delete();

		return redirect(routes.ManagementController.management_cont3());

	}

	//分類追加
	public Result createCategory(){

		Map<String, String[]> parms = request().body().asFormUrlEncoded();

		Category newCategory = new Category();

		newCategory.categoryCd = parms.get("categoryCd")[0];

		newCategory.categoryName = parms.get("categoryName")[0];

		newCategory.save();

		return redirect(routes.ManagementController.management_cont3());

	}


}
