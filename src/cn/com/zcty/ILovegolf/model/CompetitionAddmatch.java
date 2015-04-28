package cn.com.zcty.ILovegolf.model;

import java.io.Serializable;
import java.util.ArrayList;


public class CompetitionAddmatch implements Serializable{
	private String name;
	private String rule;//比赛类型
	private String remark;//备注
	private ArrayList<TeeBoxs> titai;//T台颜色
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ArrayList<TeeBoxs> getTitai() {
		return titai;
	}
	public void setTitai(ArrayList<TeeBoxs> titai) {
		this.titai = titai;
	}
	
}
