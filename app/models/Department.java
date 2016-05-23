package models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.*;

@Entity
public class Department extends Model {

	@Id
	@Required
	@OneToMany
	public Integer departmentId;

	@Required
	@MinLength(4)
	@ManyToOne()
	public String departmentCd;

	@Required
	public Integer sectionId;

	@Required
	@MinLength(25)
	public String departmentName;

	public static Find<Integer, Department> find = new Find<Integer, Department>(){};

}
