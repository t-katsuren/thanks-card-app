package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

@Entity
public class User extends Model {

	@Id
	@Required
	@OneToMany
	public Integer userId;

	@Required
	@MinLength(3)
	@MaxLength(3)
	public String userCd;

	@Required
	@MinLength(5)
	@MaxLength(20)
	public String userPass;

	@Required
	@ManyToOne
	public Integer departmentId;

	@Required
	@MaxLength(25)
	public String userName;

	@Required
	@ManyToOne
	public Integer permissionId;

	public static Find<Integer, User> find = new Find<Integer, User>(){};

}
