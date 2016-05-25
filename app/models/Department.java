package models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.*;

@Entity
public class Department extends Model {

	@Id
	@OneToMany
	public Integer departmentId;

	@Required
	public String departmentCd;

	@Required
	public Integer sectionId;

	@Required
	public String departmentName;

	public static Find<Integer, Department> find = new Find<Integer, Department>(){};

}
