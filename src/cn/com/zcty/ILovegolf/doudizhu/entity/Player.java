package cn.com.zcty.ILovegolf.doudizhu.entity;

import java.io.Serializable;




public class Player implements Serializable{



	//用户id 主键
	private String uid;

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Player player = (Player) o;

		if (uid != null ? !uid.equals(player.uid) : player.uid != null) return false;
		if (match_id != null ? !match_id.equals(player.match_id) : player.match_id != null)
			return false;
		if (is_owner != null ? !is_owner.equals(player.is_owner) : player.is_owner != null)
			return false;
		if (nickname != null ? !nickname.equals(player.nickname) : player.nickname != null)
			return false;
		return !(portrait != null ? !portrait.equals(player.portrait) : player.portrait != null);

	}

	@Override
	public int hashCode()
	{
		int result = uid != null ? uid.hashCode() : 0;
		result = 31 * result + (match_id != null ? match_id.hashCode() : 0);
		result = 31 * result + (is_owner != null ? is_owner.hashCode() : 0);
		result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
		result = 31 * result + (portrait != null ? portrait.hashCode() : 0);
		return result;
	}

	/**

	 * 玩家id,外键，与Match表关联
	 */
	private String match_id;
	/**
	 * 是否是当前用户
	 */
	private String is_owner;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String portrait;



	private int stroke_1;
	private int stroke_2;
	private int stroke_3;
	private int stroke_4;
	private int stroke_5;
	private int stroke_6;
	private int stroke_7;
	private int stroke_8;
	private int stroke_9;
	private int stroke_10;
	private int stroke_11;
	private int stroke_12;
	private int stroke_13;
	private int stroke_14;
	private int stroke_15;
	private int stroke_16;
	private int stroke_17;
	private int stroke_18;


	private String position;

	public Player(){}

	public Player(String uid,String match_id, String is_owner, String nickname,
			String portrait, int stroke_1, int stroke_2, int stroke_3,
			int stroke_4, int stroke_5, int stroke_6, int stroke_7,
			int stroke_8, int stroke_9, int stroke_10, int stroke_11,
			int stroke_12, int stroke_13, int stroke_14, int stroke_15,
			int stroke_16, int stroke_17, int stroke_18) {
		super();
		this.match_id = match_id;
		this.is_owner = is_owner;
		this.nickname = nickname;
		this.portrait = portrait;
		this.stroke_1 = stroke_1;
		this.stroke_2 = stroke_2;
		this.stroke_3 = stroke_3;
		this.stroke_4 = stroke_4;
		this.stroke_5 = stroke_5;
		this.stroke_6 = stroke_6;
		this.stroke_7 = stroke_7;
		this.stroke_8 = stroke_8;
		this.stroke_9 = stroke_9;
		this.stroke_10 = stroke_10;
		this.stroke_11 = stroke_11;
		this.stroke_12 = stroke_12;
		this.stroke_13 = stroke_13;
		this.stroke_14 = stroke_14;
		this.stroke_15 = stroke_15;
		this.stroke_16 = stroke_16;
		this.stroke_17 = stroke_17;
		this.stroke_18 = stroke_18;
	}


	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getMatch_id()
	{
		return match_id;
	}

	public void setMatch_id(String match_id)
	{
		this.match_id = match_id;
	}

	public String getIs_owner() {
		return is_owner;
	}

	public void setIs_owner(String is_owner) {
		this.is_owner = is_owner;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public int getStroke_1() {
		return stroke_1;
	}

	public void setStroke_1(int stroke_1) {
		this.stroke_1 = stroke_1;
	}

	public int getStroke_2() {
		return stroke_2;
	}

	public void setStroke_2(int stroke_2) {
		this.stroke_2 = stroke_2;
	}

	public int getStroke_3() {
		return stroke_3;
	}

	public void setStroke_3(int stroke_3) {
		this.stroke_3 = stroke_3;
	}

	public int getStroke_4() {
		return stroke_4;
	}

	public void setStroke_4(int stroke_4) {
		this.stroke_4 = stroke_4;
	}

	public int getStroke_5() {
		return stroke_5;
	}

	public void setStroke_5(int stroke_5) {
		this.stroke_5 = stroke_5;
	}

	public int getStroke_6() {
		return stroke_6;
	}

	public void setStroke_6(int stroke_6) {
		this.stroke_6 = stroke_6;
	}

	public int getStroke_7() {
		return stroke_7;
	}

	public void setStroke_7(int stroke_7) {
		this.stroke_7 = stroke_7;
	}

	public int getStroke_8() {
		return stroke_8; 
	}

	public void setStroke_8(int stroke_8) {
		this.stroke_8 = stroke_8;
	}

	public int getStroke_9() {
		return stroke_9;
	}

	public void setStroke_9(int stroke_9) {
		this.stroke_9 = stroke_9;
	}

	public int getStroke_10() {
		return stroke_10;
	}

	public void setStroke_10(int stroke_10) {
		this.stroke_10 = stroke_10;
	}

	public int getStroke_11() {
		return stroke_11;
	}

	public void setStroke_11(int stroke_11) {
		this.stroke_11 = stroke_11;
	}

	public int getStroke_12() {
		return stroke_12;
	}

	public void setStroke_12(int stroke_12) {
		this.stroke_12 = stroke_12;
	}

	public int getStroke_13() {
		return stroke_13;
	}

	public void setStroke_13(int stroke_13) {
		this.stroke_13 = stroke_13;
	}

	public int getStroke_14() {
		return stroke_14;
	}

	public void setStroke_14(int stroke_14) {
		this.stroke_14 = stroke_14;
	}

	public int getStroke_15() {
		return stroke_15;
	}

	public void setStroke_15(int stroke_15) {
		this.stroke_15 = stroke_15;
	}

	public int getStroke_16() {
		return stroke_16;
	}

	public void setStroke_16(int stroke_16) {
		this.stroke_16 = stroke_16;
	}

	public int getStroke_17() {
		return stroke_17;
	}

	public void setStroke_17(int stroke_17) {
		this.stroke_17 = stroke_17;
	}

	public int getStroke_18() {
		return stroke_18;
	}

	public void setStroke_18(int stroke_18) {
		this.stroke_18 = stroke_18;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public int getStroke(int holeNum)
	{
		switch (holeNum)
		{
			case 1:
				return this.getStroke_1();
			case 2:
				return this.getStroke_2();
			case 3:
				return this.getStroke_3();
			case 4:
				return this.getStroke_4();
			case 5:
				return this.getStroke_5();
			case 6:
				return this.getStroke_6();
			case 7:
				return this.getStroke_7();
			case 8:
				return this.getStroke_8();
			case 9:
				return this.getStroke_9();
			case 10:
				return this.getStroke_10();
			case 11:
				return this.getStroke_11();
			case 12:
				return this.getStroke_12();
			case 13:
				return this.getStroke_13();
			case 14:
				return this.getStroke_14();
			case 15:
				return this.getStroke_15();
			case 16:
				return this.getStroke_16();
			case 17:
				return this.getStroke_17();
			case 18:
				return this.getStroke_18();
		}
		return 0;
	}

	public void setStroke(int holeNum,int score)
	{
		switch (holeNum)
		{
			case 1:
				this.setStroke_1(score);break;
			case 2:
				this.setStroke_2(score);break;
			case 3:
				this.setStroke_3(score);break;
			case 4:
				this.setStroke_4(score);break;
			case 5:
				this.setStroke_5(score);break;
			case 6:
				this.setStroke_6(score);break;
			case 7:
				this.setStroke_7(score);break;
			case 8:
				this.setStroke_8(score);break;
			case 9:
				this.setStroke_9(score);break;
			case 10:
				this.setStroke_10(score);break;
			case 11:
				this.setStroke_11(score);break;
			case 12:
				this.setStroke_12(score);break;
			case 13:
				this.setStroke_13(score);break;
			case 14:
				this.setStroke_14(score);break;
			case 15:
				this.setStroke_15(score);break;
			case 16:
				this.setStroke_16(score);break;
			case 17:
				this.setStroke_17(score);break;
			case 18:
				this.setStroke_18(score);break;
		}
	}

}
