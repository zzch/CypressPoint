package cn.com.zcty.ILovegolf.model;

import java.io.Serializable;

public class Setcard implements Serializable{
	private String rodNum;//杆数
	private String putts;//推杆
	private String penalties;//罚干
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
