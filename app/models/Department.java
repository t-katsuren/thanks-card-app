package models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.*;

@Entity
public class Department extends Model {

	@Id
	public Integer id;

	@Required
	public String departmentCd;

	@Required
	@ManyToOne
	public Section section;

	@Required
	public String departmentName;

	public static Find<Integer, Department> find = new Find<Integer, Department>(){};

}
