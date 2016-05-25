package models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.*;

@Entity
public class Category extends Model {

	@Id
	@OneToMany
	public Integer categoryId;

	@Required
	public String categoryCd;

	@Required
	public String categoryName;

	public static Find<Integer, Category> find = new Find<Integer, Category>(){};

}
