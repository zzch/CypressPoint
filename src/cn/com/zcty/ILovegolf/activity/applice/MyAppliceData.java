package cn.com.zcty.ILovegolf.activity.applice;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.model.QuickContent;

import android.app.Application;

public class MyAppliceData extends Application{
	private static MyAppliceData instance;
	public static  ArrayList<QuickContent> quickArrayList;
	public MyAppliceData() {
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}
	public static MyAppliceData getInstance(){
		if(null==instance){
			instance = new MyAppliceData();
		}
		return instance;
	}
	/**
	 * 存储QuickScoreActivity的数据
	 * @param data
	 */
	public void quickContent(ArrayList<QuickContent> data){
		quickArrayList = data;
	} 
	
}
