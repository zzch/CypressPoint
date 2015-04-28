package cn.com.zcty.ILovegolf.model;

import java.io.Serializable;
import java.util.ArrayList;

public class TeeBoxs implements Serializable{
	private ArrayList<String> boxs;
	private String name;//球场名字
	public ArrayList<String> getBoxs() {
		return boxs;
	}

	public void setBoxs(ArrayList<String> boxs) {
		this.boxs = boxs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
