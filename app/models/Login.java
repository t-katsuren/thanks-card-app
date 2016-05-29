package models;

public class Login {

	public String usercode;

	public String password;

	/**
	 * 独自バリデーションメソッド
	 * 制約に引っかかった場合はhasErrors()がtrueになる
	 * テンプレート側で取得したい場合はhasGlobalErrors()を使用する
	 *
	 * @return 認証成功した場合はnull、失敗した場合はエラーメッセージ
	 */
	public String validate() {

		if(authenticate(usercode, password)) {
			return null;
		}
		return "社員コードとパスワードの一方、または両方が間違っています。<br>"
				+ "社員コードとパスワードを確かめ、再度入力して下さい。";

	}

	/**
	 * 社員コードとパスワードが正しいか判定するメソッド
	 *
	 * @param  usercode 社員コード
	 * @param  password パスワード
	 * @return 社員コードとパスワードのセットが正しければtrue
	 */
	private Boolean authenticate(String usercode, String password) {

		return User.authenticate(usercode, password);

	}

}
