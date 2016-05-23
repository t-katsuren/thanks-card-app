package models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

@Entity
public class Permission extends Model {

	@Id
	@Required
	
	public Integer permissionId;
	
	@Required
	@MinLength(10)
	@OneToMany()
	public String permissionName;

	public static Find<Integer, Permission> find = new Find<Integer, Permission>(){};

}
