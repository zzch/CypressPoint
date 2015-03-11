package cn.com.zcty.ILovegolf.model;

import java.util.List;


public class SortModel {

	private String titleName;   //显示球场所在的省
	private String uuid;
	private String name;
    private String address;
	private List<Course> courses;  //显示的球场信息
	
	
	public SortModel(){}


	public SortModel(String name, List<Course> courses) {
		super();
		this.name = name;
		this.courses = courses;
		
	}


	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public List<Course> getCourses() {
		return courses;
	}


	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}


	@Override
	public String toString() {
		return "SortModel [titleName=" + titleName + ", uuid=" + uuid
				+ ", name=" + name + ", address=" + address + ", courses="
				+ courses + "]";
	}


}
