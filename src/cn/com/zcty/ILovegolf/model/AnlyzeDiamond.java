package cn.com.zcty.ILovegolf.model;



public class AnlyzeDiamond {
	/**
	 * 定义属性
	 */
	String uuid;//uuid
	String name;//高尔夫球场名称
	String address;//高尔夫球场地址
	String visited_count;//场次
	public AnlyzeDiamond() {
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
	public String getVisited_count() {
		return visited_count;
	}
	public void setVisited_count(String visited_count) {
		this.visited_count = visited_count;
	}
	
}
