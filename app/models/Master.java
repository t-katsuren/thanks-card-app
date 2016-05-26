package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;

@Entity
public class Master extends Model {

	@Id
	public Integer id;                 //Cardクラスフィールドから

	public String fromDepartmentName;  //Departmentクラスフィールドから

	public String fromUserName;        //Userクラスフィールドから

	public String toDepartmentName;    //Departmentクラスフィールドから

	public String toUserName;          //Userクラスフィールドから

	public String categoryName;        //Categoryクラスフィールドから

	public String title;               //Cardクラスフィールドから

	public Date date;                  //Cardクラスフィールドから

	public Integer goodCount;          //Cardクラスフィールドから

	public static Find<Integer, Master> find = new Find<Integer, Master>(){};


	/**
	 * Masterテーブルを最新の状態にするメソッド
	 *
	 * @param なし
	 *
	 * 既にデータが入っているかでupdateするか場合分け
	 * goodCountのみ既にデータが入っていてもupdateしなければいけないケース有り
	 */
	public static void upDate() {

		List<Card> cards = Card.find.all();

		List<Master> masters = Master.find.all();

		for(int i = 0; i < cards.size(); i++) {

			if(i < masters.size()) {

				if(masters.get(i).goodCount != cards.get(i).goodCount) {
					masters.get(i).goodCount = cards.get(i).goodCount;
					masters.get(i).save();
				}

			} else {

				Master newMaster = new Master();

				newMaster.id = cards.get(i).id;
				newMaster.fromDepartmentName = cards.get(i).fromUser.department.departmentName;
				newMaster.fromUserName = cards.get(i).fromUser.userName;
				newMaster.toDepartmentName = cards.get(i).toUser.department.departmentName;
				newMaster.toUserName = cards.get(i).toUser.userName;
				newMaster.categoryName = cards.get(i).category.categoryName;
				newMaster.title = cards.get(i).title;
				newMaster.date = cards.get(i).date;
				newMaster.goodCount = cards.get(i).goodCount;

				newMaster.save();

			}

		}

	}

}
