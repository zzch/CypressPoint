package cn.com.zcty.ILovegolf.model;

public class Clubs {
	private String name;
	private String users;//击球次数
	private String average_length;//
	private String less_than_average_length;
	private String greater_than_average_length;
	private String minimum_length;
	private String maximum_length;
	public Clubs() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getAverage_length() {
		return average_length;
	}
	public void setAverage_length(String average_length) {
		this.average_length = average_length;
	}
	public String getLess_than_average_length() {
		return less_than_average_length;
	}
	public void setLess_than_average_length(String less_than_average_length) {
		this.less_than_average_length = less_than_average_length;
	}
	public String getGreater_than_average_length() {
		return greater_than_average_length;
	}
	public void setGreater_than_average_length(String greater_than_average_length) {
		this.greater_than_average_length = greater_than_average_length;
	}
	public String getMinimum_length() {
		return minimum_length;
	}
	public void setMinimum_length(String minimum_length) {
		this.minimum_length = minimum_length;
	}
	public String getMaximum_length() {
		return maximum_length;
	}
	public void setMaximum_length(String maximum_length) {
		this.maximum_length = maximum_length;
	}
	
}
