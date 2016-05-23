package models;

import javax.persistence.*;


import com.avaje.ebean.Model;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

@Entity
public class Category extends Model {

	@Id
	@OneToMany
	public Integer categoryId;

	@Required
	@MaxLength(2)
	@MinLength(2)
	public String categoryCd;

	@Required
	@MaxLength(20)
	public String categoryName;


	public static Find<Integer, Category> find = new Find<Integer, Category>(){};

}