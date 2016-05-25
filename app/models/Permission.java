package models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.*;

@Entity
public class Permission extends Model {

	@Id
	public Integer permissionId;

	@Required
	@OneToMany()
	public String permissionName;

	public static Find<Integer, Permission> find = new Find<Integer, Permission>(){};

}
