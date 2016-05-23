package models;

import javax.persistence.*;

import com.avaje.ebean.Model;

@Entity
public class Permission extends Model {

	@Id
	public Integer permissionId;

	public String permissionName;

	public static Find<Integer, Permission> find = new Find<Integer, Permission>(){};

}
