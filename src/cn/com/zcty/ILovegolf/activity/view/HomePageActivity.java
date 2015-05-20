package cn.com.zcty.ILovegolf.activity.view;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.count.CountActivity;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.activity.view.myself.Myself;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class HomePageActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_homepage);
		ShouYeActivity.getInstance().addActivity(this);
	}
	/*
	 * 点击跳转事件
	 */
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.homepage_match:
			Intent intent=new Intent(HomePageActivity.this,QuickScoreActivity.class);
			startActivity(intent);
	 		 finish();
			break;
		case R.id.homepage_statistics:
			Intent statisticsIntent=new Intent(HomePageActivity.this,CountActivity.class);
	 		startActivity(statisticsIntent);
	 		finish();
			break;
		case R.id.homepage_personal_center:
			Intent mySelfIntent=new Intent(HomePageActivity.this,Myself.class);
	 		startActivity(mySelfIntent);
	 		finish();
			break;
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		ShouYeActivity.getInstance().exit();
		finish();
	}
}
