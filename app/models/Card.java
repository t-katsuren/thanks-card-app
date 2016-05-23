package models;

import java.util.Date;

import javax.persistence.*;


import com.avaje.ebean.Model;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

@Entity
public class Card extends Model {

	@Id
	public Integer cardId;

	@Required
	public Date date;

	@Required
	@ManyToOne
	public Integer fromUserId;

	@Required
	@ManyToOne
	public Integer toUserId;

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
	public Integer categoryId;

	@Required
	public Integer goodCount;

	public static Find<Integer, Card> find = new Find<Integer, Card>(){};

}

