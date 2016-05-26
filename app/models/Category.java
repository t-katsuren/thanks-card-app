package models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.*;

@Entity
public class Category extends Model {

	@Id
	public Integer id;

	@Required
	public String categoryCd;

	@Required
	public String categoryName;

	public static Find<Integer, Category> find = new Find<Integer, Category>(){};

}
