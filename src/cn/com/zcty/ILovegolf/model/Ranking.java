package cn.com.zcty.ILovegolf.model;
/**
 * 排行榜
 * @author Administrator
 *
 */
public class Ranking {
	private String uuid;
	/**
	 * 排名
	 */
	private String position;
	/**
	 * 用户名
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String portrait;
	/**
	 * 进度
	 */
	private String recorded_scorecards_count;
	/**
	 * 成绩
	 */
	private String total;
	/**
	 * 判断是否为自己
	 */
	private String self;
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getRecorded_scorecards_count() {
		return recorded_scorecards_count;
	}
	public void setRecorded_scorecards_count(String recorded_scorecards_count) {
		this.recorded_scorecards_count = recorded_scorecards_count;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSelf() {
		return self;
	}
	public void setSelf(String self) {
		this.self = self;
	}
	
}
