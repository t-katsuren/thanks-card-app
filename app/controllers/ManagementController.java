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

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		String[] loginUser = new String[2];

		loginUser[0] = User.find.where().eq("userCd", session("login")).findUnique().userName;

		loginUser[1] = User.find.where().eq("userCd", session("login")).findUnique().permission.permissionName;

		return ok(management_main.render(loginUser));

	}


	//社員設定
	public Result management_cont1() {

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		List<User> userList = User.find.where().gt("id", 1).findList();;

		List<Department> departmentList = Department.find.all();

		List<Permission> permissionList = Permission.find.where().gt("id", 1).findList();

		return ok(management_cont1.render(userList, formFactory.form(User.class), departmentList, permissionList));

	}

	//社員削除
	public Result deleteUser(Integer userId){

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		User delUser = User.find.byId(userId);

		Card.find.where().eq("fromUser", delUser).delete();

		Card.find.where().eq("toUser", delUser).delete();

		delUser.delete();

		return redirect(routes.ManagementController.management_cont1());

	}

	//社員追加
	public Result createUser(){

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		Map<String, String[]> params = request().body().asFormUrlEncoded();

		User user = User.find.where().eq("userCd", params.get("userCd")[0]).findUnique();

		if(user != null) {
			String str = "社員";
			return redirect(routes.HomeController.cdError(str));
		}

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

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		List<Section> sectionList = Section.find.all();

		List<Department> departmentList = Department.find.all();

		return ok(management_cont2.render(sectionList, departmentList));

	}

	//部門削除
	public Result deleteSection(Integer sectionId){

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		Section delSection = Section.find.byId(sectionId);

		List<Department> delDepartmentList = Department.find.where().eq("section", delSection).findList();

		List<User> delUserList = User.find.where().in("department", delDepartmentList).findList();

		Card.find.where().in("fromUser", delUserList).delete();

		Card.find.where().in("toUser", delUserList).delete();

		User.find.where().in("department", delDepartmentList).delete();

		Department.find.where().eq("section", delSection).delete();

		delSection.delete();

		return redirect(routes.ManagementController.management_cont2());

	}

	//部門追加
	public Result createSection(){

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		Map<String, String[]> parms = request().body().asFormUrlEncoded();

		Section section = Section.find.where().eq("sectionCd", parms.get("sectionCd")[0]).findUnique();

		if(section != null) {
			String str = "部門";
			return redirect(routes.HomeController.cdError(str));
		}

		Section newSection = new Section();

		newSection.sectionCd = parms.get("sectionCd")[0];

		newSection.sectionName = parms.get("sectionName")[0];

		newSection.save();

		return redirect(routes.ManagementController.management_cont2());

	}

	//部署削除
	public Result deleteDepartment(Integer departmentId){

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		Department delDepartment = Department.find.byId(departmentId);

		List<User> delUserList = User.find.where().eq("department", delDepartment).findList();

		Card.find.where().in("fromUser", delUserList).delete();

		Card.find.where().in("toUser", delUserList).delete();

		User.find.where().eq("department", delDepartment).delete();

		delDepartment.delete();

		return redirect(routes.ManagementController.management_cont2());

	}

	//部署追加
	public Result createDepartment(){

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		Map<String, String[]> parms = request().body().asFormUrlEncoded();

		Department department = Department.find.where().eq("departmentCd", parms.get("departmentCd")[0]).findUnique();

		if(department != null) {
			String str = "部署";
			return redirect(routes.HomeController.cdError(str));
		}

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

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		List<Category> categoryList = Category.find.all();

		return ok(management_cont3.render(categoryList));

	}

	//分類削除
	public Result deleteCategory(Integer categoryId){

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		Category delCategory = Category.find.byId(categoryId);

		Card.find.where().eq("category", delCategory).delete();

		delCategory.delete();

		return redirect(routes.ManagementController.management_cont3());

	}

	//分類追加
	public Result createCategory(){

		Integer id = User.find.where().eq("userCd", session("login")).findUnique().permission.id;

		if(id > 2) {
			return redirect(routes.HomeController.appError());
		}

		Map<String, String[]> parms = request().body().asFormUrlEncoded();

		Category category = Category.find.where().eq("categoryCd", parms.get("categoryCd")[0]).findUnique();

		if(category != null) {
			String str = "分類";
			return redirect(routes.HomeController.cdError(str));
		}

		Category newCategory = new Category();

		newCategory.categoryCd = parms.get("categoryCd")[0];

		newCategory.categoryName = parms.get("categoryName")[0];

		newCategory.save();

		return redirect(routes.ManagementController.management_cont3());

	}


}
