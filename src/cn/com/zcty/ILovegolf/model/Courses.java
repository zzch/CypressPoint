package cn.com.zcty.ILovegolf.model;

public class Courses {

	private String uuid;

	private String name;

	private String holes_count;
	
	private String tee_boxes;
	
	
	public Courses(){}


	public Courses(String uuid, String name, String holes_count,
			String tee_boxes) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.holes_count = holes_count;
		this.tee_boxes = tee_boxes;
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


	public String getHoles_count() {
		return holes_count;
	}


	public void setHoles_count(String holes_count) {
		this.holes_count = holes_count;
	}


	public String getTee_boxes() {
		return tee_boxes;
	}


	public void setTee_boxes(String tee_boxes) {
		this.tee_boxes = tee_boxes;
	}

	
}
