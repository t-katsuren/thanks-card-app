package models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.*;

@Entity
public class Section extends Model {

	@Id
	public Integer id;

	@Required
	public String sectionCd;

	@Required
	public String sectionName;

	public static Find<Integer, Section> find = new Find<Integer, Section>(){};

}
