package cn.com.zcty.ILovegolf.doudizhu.entity;

import android.content.Context;

import cn.com.zcty.ILovegolf.doudizhu.utills.CacheUtils;
import com.google.gson.Gson;

public class User {
	
	private String username;
	private String password;
	private int uid;
	private String portrait;


	public User(){}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public int getUid()
	{
		return uid;
	}

	public void setUid(int uid)
	{
		this.uid = uid;
	}

	public String getPortrait()
	{
		return portrait;
	}

	public void setPortrait(String portrait)
	{
		this.portrait = portrait;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public static User getMyuserFromJson(Context context) {
		String str = CacheUtils.getString(context, "myuser", "");
		Gson gson = new Gson();
		if (!"".equals(str)) {
			return gson.fromJson(str, User.class);
		} else {
			return null;
		}
	}
	
    
}
