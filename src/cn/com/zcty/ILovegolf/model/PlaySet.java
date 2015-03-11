package cn.com.zcty.ILovegolf.model;

import java.util.List;

public class PlaySet {
	
	private String name;
	private List<Groups> groups;
	
	public PlaySet(){}

	public PlaySet(String name, List<Groups> groups) {
		super();
		this.name = name;
		this.groups = groups;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}
	
	

}
