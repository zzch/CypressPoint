package cn.com.zcty.ILovegolf.model;

import java.io.Serializable;
import java.util.ArrayList;


public class CompetitionAddmatch implements Serializable{
	private String useName;
	private String portrait;//头像
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
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	
}
