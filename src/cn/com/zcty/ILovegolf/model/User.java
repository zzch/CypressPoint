package cn.com.zcty.ILovegolf.model;

public class User {
	
	private int uuid; 
	private String nickname;
	private int token;
	
	public User(){}

	public User(int uuid, String nickname, int token) {
		super();
		this.uuid = uuid;
		this.nickname = nickname;
		this.token = token;
	}

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

    
}
