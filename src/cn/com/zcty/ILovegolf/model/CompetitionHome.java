package cn.com.zcty.ILovegolf.model;

import android.graphics.Bitmap;

/**
 * 竞技比赛创建房间
 * @author Administrator
 *
 */
public class CompetitionHome {
	private String uuid;//uuid
	private String bitmap;//用户头像
	private String nickname;//用户昵称
	private String name;//比赛房间昵称
	private String rule;//比赛类型
	private String players_count;//房间人数
	private String password;//房间密码
	private String started_at;//创建时间
	public CompetitionHome() {
		// TODO Auto-generated constructor stub
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getPlayers_count() {
		return players_count;
	}
	public void setPlayers_count(String players_count) {
		this.players_count = players_count;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStarted_at() {
		return started_at;
	}
	public void setStarted_at(String started_at) {
		this.started_at = started_at;
	}
	public String getBitmap() {
		return bitmap;
	}
	public void setBitmap(String bitmap) {
		this.bitmap = bitmap;
	}

	
}
