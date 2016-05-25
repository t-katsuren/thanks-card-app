package models;

import javax.persistence.*;

import org.mindrot.jbcrypt.BCrypt;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.*;

@Entity
public class User extends Model {

	@Id
	@OneToMany
	public Integer userId;

	@Required
	public String userCd;

	@Required
	public String userPass;

	@Required
	@ManyToOne
	public Integer departmentId;

	@Required
	public String userName;

	@Required
	@ManyToOne
	public Integer permissionId;

	public static Find<Integer, User> find = new Find<Integer, User>(){};

	/**
	 * ログイン時の認証を判定するメソッド
	 *
	 * @param usercode 社員コード
	 * @param password 社員パス
	 * @return 社員コードと社員パスのセットが正しければtrue
	 */
	public static Boolean authenticate(String usercode, String password) {

		User user = User.find.where().eq("userCd", usercode).findUnique();

		return (user != null && BCrypt.checkpw(password, user.userPass));

	}

}
