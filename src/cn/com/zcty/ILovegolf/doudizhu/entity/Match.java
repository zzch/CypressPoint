package cn.com.zcty.ILovegolf.doudizhu.entity;

import java.io.Serializable;
import java.sql.Date;



public class Match implements Serializable{

	public final static int LEFT = 0;

	public final static int RIGHT = 1;

	/*
	 *比赛id
	 */
	private String id;


	private int currenthole;
	/*
	 * 比赛类型
	 */
	private String type;
	/*
	 * 比赛时间
	 */
	private String played_at;
	/*
	 * 老鹰球
	 */
	private String eagle_x4;
	/*
	 * 小鸟球
	 */
	private String birdie_x2;
	/*
	 * 柏忌球
	 */
	private String double_par_x2;
	/*
	 * 平局进入下一洞
	 */
	private String draw_to_next;
	/*
	 * 平局让杆
	 */
	private String draw_to_win;
	/*
	 * 该用户每洞所挣积分
	 */
	private int earned;
	/*
	 * 球洞1---18的标准杆
	 */
	private int par_1;
	private int par_2;
	private int par_3;
	private int par_4;
	private int par_5;
	private int par_6;
	private int par_7;
	private int par_8;
	private int par_9;
	private int par_10;
	private int par_11;
	private int par_12;
	private int par_13;
	private int par_14;
	private int par_15;
	private int par_16;
	private int par_17;
	private int par_18;

	private int drawWhoWin = -1;
	
	public Match(){}

	public Match(String id, String type, String played_at, String eagle_x4,
			String birdie_x2, String double_par_x2, String draw_to_next,
			String draw_to_win, int earned, int par_1, int par_2, int par_3,
			int par_4, int par_5, int par_6, int par_7, int par_8, int par_9,
			int par_10, int par_11, int par_12, int par_13, int par_14,
			int par_15, int par_16, int par_17, int par_18) {
		super();
		this.id = id;
		this.type = type;
		this.played_at = played_at;
		this.eagle_x4 = eagle_x4;
		this.birdie_x2 = birdie_x2;
		this.double_par_x2 = double_par_x2;
		this.draw_to_next = draw_to_next;
		this.draw_to_win = draw_to_win;
		this.earned = earned;
		this.par_1 = par_1;
		this.par_2 = par_2;
		this.par_3 = par_3;
		this.par_4 = par_4;
		this.par_5 = par_5;
		this.par_6 = par_6;
		this.par_7 = par_7;
		this.par_8 = par_8;
		this.par_9 = par_9;
		this.par_10 = par_10;
		this.par_11 = par_11;
		this.par_12 = par_12;
		this.par_13 = par_13;
		this.par_14 = par_14;
		this.par_15 = par_15;
		this.par_16 = par_16;
		this.par_17 = par_17;
		this.par_18 = par_18;
	}

	public int getCurrenthole()
	{
		return currenthole;
	}

	public void setCurrenthole(int currenthole)
	{
		this.currenthole = currenthole;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getPlayed_at() {
		return played_at;
	}


	public void setPlayed_at(String played_at) {
		this.played_at = played_at;
	}


	public String getEagle_x4() {
		return eagle_x4;
	}


	public void setEagle_x4(String eagle_x4) {
		this.eagle_x4 = eagle_x4;
	}


	public String getBirdie_x2() {
		return birdie_x2;
	}


	public void setBirdie_x2(String birdie_x2) {
		this.birdie_x2 = birdie_x2;
	}


	public String getDouble_par_x2() {
		return double_par_x2;
	}


	public void setDouble_par_x2(String double_par_x2) {
		this.double_par_x2 = double_par_x2;
	}

	public int getEarned() {
		return earned;
	}


	public void setEarned(int earned) {
		this.earned = earned;
	}

	public String getDraw_to_next() {
		return draw_to_next;
	}

	public void setDraw_to_next(String draw_to_next) {
		this.draw_to_next = draw_to_next;
	}

	public String getDraw_to_win() {
		return draw_to_win;
	}

	public void setDraw_to_win(String draw_to_win) {
		this.draw_to_win = draw_to_win;
	}

	public int getPar_1() {
		return par_1;
	}


	public void setPar_1(int par_1) {
		this.par_1 = par_1;
	}


	public int getPar_2() {
		return par_2;
	}


	public void setPar_2(int par_2) {
		this.par_2 = par_2;
	}


	public int getPar_3() {
		return par_3;
	}


	public void setPar_3(int par_3) {
		this.par_3 = par_3;
	}


	public int getPar_4() {
		return par_4;
	}


	public void setPar_4(int par_4) {
		this.par_4 = par_4;
	}


	public int getPar_5() {
		return par_5;
	}


	public void setPar_5(int par_5) {
		this.par_5 = par_5;
	}


	public int getPar_6() {
		return par_6;
	}


	public void setPar_6(int par_6) {
		this.par_6 = par_6;
	}


	public int getPar_7() {
		return par_7;
	}


	public void setPar_7(int par_7) {
		this.par_7 = par_7;
	}


	public int getPar_8() {
		return par_8;
	}


	public void setPar_8(int par_8) {
		this.par_8 = par_8;
	}


	public int getPar_9() {
		return par_9;
	}


	public void setPar_9(int par_9) {
		this.par_9 = par_9;
	}


	public int getPar_10() {
		return par_10;
	}


	public void setPar_10(int par_10) {
		this.par_10 = par_10;
	}


	public int getPar_11() {
		return par_11;
	}


	public void setPar_11(int par_11) {
		this.par_11 = par_11;
	}


	public int getPar_12() {
		return par_12;
	}


	public void setPar_12(int par_12) {
		this.par_12 = par_12;
	}


	public int getPar_13() {
		return par_13;
	}


	public void setPar_13(int par_13) {
		this.par_13 = par_13;
	}


	public int getPar_14() {
		return par_14;
	}


	public void setPar_14(int par_14) {
		this.par_14 = par_14;
	}


	public int getPar_15() {
		return par_15;
	}


	public void setPar_15(int par_15) {
		this.par_15 = par_15;
	}


	public int getPar_16() {
		return par_16;
	}


	public void setPar_16(int par_16) {
		this.par_16 = par_16;
	}


	public int getPar_17() {
		return par_17;
	}


	public void setPar_17(int par_17) {
		this.par_17 = par_17;
	}


	public int getPar_18() {
		return par_18;
	}


	public void setPar_18(int par_18) {
		this.par_18 = par_18;
	}
	
	public int getPar(int hole)
	{
		switch (hole)
		{
			case 1:
				return this.getPar_1();
			case 2:
				return this.getPar_2();
			case 3:
				return this.getPar_3();
			case 4:
				return this.getPar_4();
			case 5:
				return this.getPar_5();
			case 6:
				return this.getPar_6();
			case 7:
				return this.getPar_7();
			case 8:
				return this.getPar_8();
			case 9:
				return this.getPar_9();
			case 10:
				return this.getPar_10();
			case 11:
				return this.getPar_11();
			case 12:
				return this.getPar_12();
			case 13:
				return this.getPar_13();
			case 14:
				return this.getPar_14();
			case 15:
				return this.getPar_15();
			case 16:
				return this.getPar_16();
			case 17:
				return this.getPar_17();
			case 18:
				return this.getPar_18();
		}
		return 99;
	}

	public void setPar(int hole,int par)
	{
		switch (hole)
		{
			case 1:
				this.setPar_1(par);
				break;
			case 2:
				this.setPar_2(par);break;
			case 3:
				this.setPar_3(par);break;
			case 4:
				this.setPar_4(par);break;
			case 5:
				this.setPar_5(par);break;
			case 6:
				this.setPar_6(par);break;
			case 7:
				this.setPar_7(par);break;
			case 8:
				this.setPar_8(par);break;
			case 9:
				this.setPar_9(par);break;
			case 10:
				this.setPar_10(par);break;
			case 11:
				this.setPar_11(par);break;
			case 12:
				this.setPar_12(par);break;
			case 13:
				this.setPar_13(par);break;
			case 14:
				this.setPar_14(par);break;
			case 15:
				this.setPar_15(par);break;
			case 16:
				this.setPar_16(par);break;
			case 17:
				this.setPar_17(par);break;
			case 18:
				this.setPar_18(par);break;
		}
	}

	public int getDrawWhoWin() {
		return drawWhoWin;
	}

	public void setDrawWhoWin(int drawWhoWin) {
		this.drawWhoWin = drawWhoWin;
	}
}
