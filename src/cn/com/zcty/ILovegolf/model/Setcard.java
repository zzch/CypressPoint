package cn.com.zcty.ILovegolf.model;

public class Setcard {
	private String rodNum;//����
	private String putts;//�Ƹ�
	private String penalties;//����
	private String te;
	private String par;
	public Setcard() {
		super();
	}
	public String getTe() {
		return te;
	}
	public void setTe(String te) {
		this.te = te;
	}
	public String getPar() {
		return par;
	}
	public void setPar(String par) {
		this.par = par;
	}
	
	public String getRodNum() {
		return rodNum;
	}
	public void setRodNum(String rodNum) {
		this.rodNum = rodNum;
	}
	public String getPutts() {
		return putts;
	}
	public void setPutts(String putts) {
		this.putts = putts;
	}
	public String getPenalties() {
		return penalties;
	}
	public void setPenalties(String penalties) {
		this.penalties = penalties;
	}
	
}
