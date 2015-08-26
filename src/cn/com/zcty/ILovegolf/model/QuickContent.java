package cn.com.zcty.ILovegolf.model;



public class QuickContent {
	private String uuid;
	private String name;
	/**
	 * 类型
	 */
	private String scoring_type;
	/**
	 * 成绩
	 */
	private String score;
	/**
	 * 进度
	 */
	private String recorded_scorecards_count;
	/**
	 * 房间人数
	 */
	private String players_count;
	/**
	 * 时间戳
	 */
	private String started_at;
	private String owend;
	
	
	public QuickContent() {
		// TODO Auto-generated constructor stub
	}
	public String getOwend() {
		return owend;
	}
	public void setOwend(String owend) {
		this.owend = owend;
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
	public String getScoring_type() {
		return scoring_type;
	}
	public void setScoring_type(String scoring_type) {
		this.scoring_type = scoring_type;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getRecorded_scorecards_count() {
		return recorded_scorecards_count;
	}
	public void setRecorded_scorecards_count(String recorded_scorecards_count) {
		this.recorded_scorecards_count = recorded_scorecards_count;
	}
	public String getPlayers_count() {
		return players_count;
	}
	public void setPlayers_count(String players_count) {
		this.players_count = players_count;
	}
	public String getStarted_at() {
		return started_at;
	}
	public void setStarted_at(String started_at) {
		this.started_at = started_at;
	}
	
	
}
