package models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.*;

@Entity
public class Section extends Model {

	@Id
	@OneToMany
	public Integer sectionId;

	@Required
	@MaxLength(3)
	public String sectionCd;

	@Required
	@MaxLength(20)
	public String sectionName;

	public static Find<Integer, Section> find = new Find<Integer, Section>(){};

}
