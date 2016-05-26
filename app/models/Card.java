package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.*;

@Entity
public class Card extends Model {

	@Id
	public Integer id;

	@Required
	public Date date;

	@Required
	@ManyToOne
	public User fromUser;

	@Required
	@ManyToOne
	public User toUser;

	@Required
	@MaxLength(30)
	public String title;

	@Required
	@MaxLength(60)
	public String detail;

	@Required
	@MaxLength(150)
	public String message;

	@Required
	@ManyToOne
	public Category category;

	@Required
	public Integer goodCount;

	public static Find<Integer, Card> find = new Find<Integer, Card>(){};

}
