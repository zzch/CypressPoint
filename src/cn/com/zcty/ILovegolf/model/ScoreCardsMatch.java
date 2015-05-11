package cn.com.zcty.ILovegolf.model;

import java.util.ArrayList;

public class ScoreCardsMatch {
	private String uuid;
	private String number;
	private String par;
	private String score;
	private String putts;
	private String penalties;
	private String driving_distance;
	private String direction;
	private ArrayList<TeeBoxsMatch> teeboxs;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPar() {
		return par;
	}
	public void setPar(String par) {
		this.par = par;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
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
	public String getDriving_distance() {
		return driving_distance;
	}
	public void setDriving_distance(String driving_distance) {
		this.driving_distance = driving_distance;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public ArrayList<TeeBoxsMatch> getTeeboxs() {
		return teeboxs;
	}
	public void setTeeboxs(ArrayList<TeeBoxsMatch> teeboxs) {
		this.teeboxs = teeboxs;
	}
	
	
}