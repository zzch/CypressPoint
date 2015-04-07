package cn.com.zcty.ILovegolf.model;

import java.io.Serializable;

public class MajorScore implements Serializable{
	private String order;
	private String distance;
	private String cool;
	private String pentails;
	private String count;
	private String uuid;
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getCool() {
		return cool;
	}
	public void setCool(String cool) {
		this.cool = cool;
	}
	public String getPentails() {
		return pentails;
	}
	public void setPentails(String pentails) {
		this.pentails = pentails;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
}
