package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

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
