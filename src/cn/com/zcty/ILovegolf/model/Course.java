package cn.com.zcty.ILovegolf.model;

public class Course {
	/**
	 * 球场uuid
	 */
	private String uuid;
	/**
	 * 球场名称
	 */
	private String name;
	/**
	 * 球场地址
	 */
	private String address;
	
	public Course(){}

	public Course(String uuid, String name, String address) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.address = address;
	}

	public Course(String name, String address) {
		super();
		this.name = name;
		this.address = address;
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
	
	

}
