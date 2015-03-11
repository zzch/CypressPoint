package cn.com.zcty.ILovegolf.model;

import java.util.List;

public class QuickContent {
	/**
	 * 用户uuid
	 */
	private String uuid;
	/**
	 * 赛事类型
	 */
	private String type;
    /**
     * 球场信息
     */
	private List<Course> course;
	/**
	 * 命中杆数
	 */
	private String strokes;
	/**
	 * 总杆数
	 */
	private String recorded_scorecards_count;
	/**
	 * 打球时间
	 */
	private String started_at;
   
	
    public QuickContent(){}


	public QuickContent(String uuid, String type, List<Course> course,
			String strokes, String recorded_scorecards_count, String started_at) {
		super();
		this.uuid = uuid;
		this.type = type;
		this.course = course;
		this.strokes = strokes;
		this.recorded_scorecards_count = recorded_scorecards_count;
		this.started_at = started_at;
	}


	public QuickContent(String type, List<Course> course, String strokes,
			String recorded_scorecards_count, String started_at) {
		super();
		this.type = type;
		this.course = course;
		this.strokes = strokes;
		this.recorded_scorecards_count = recorded_scorecards_count;
		this.started_at = started_at;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public List<Course> getCourse() {
		return course;
	}


	public void setCourse(List<Course> course) {
		this.course = course;
	}


	public String getStrokes() {
		return strokes;
	}


	public void setStrokes(String strokes) {
		this.strokes = strokes;
	}


	public String getRecorded_scorecards_count() {
		return recorded_scorecards_count;
	}


	public void setRecorded_scorecards_count(String recorded_scorecards_count) {
		this.recorded_scorecards_count = recorded_scorecards_count;
	}


	public String getStarted_at() {
		return started_at;
	}


	public void setStarted_at(String started_at) {
		this.started_at = started_at;
	}

	
}
