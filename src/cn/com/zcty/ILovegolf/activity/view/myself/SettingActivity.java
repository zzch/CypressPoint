package cn.com.zcty.ILovegolf.activity.view.myself;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;

public class SettingActivity extends Activity{
	private String phone;
	private TextView phoneTextView;
	private RelativeLayout zhanghaoLayout;
	private RelativeLayout shoujiLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		initView();
		setListeners();
	}

	private void setListeners() {
		zhanghaoLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//点击帐号与密码
				Intent intent = new Intent(SettingActivity.this,ChangesPwsActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		shoujiLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//更换手机
				/*Intent intent = new Intent(SettingActivity.this,ChangesPhone.class);
				startActivity(intent);*/
				
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(SettingActivity.this,Myself.class);
		startActivity(intent);
		finish();
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.back:
			Intent intent = new Intent(SettingActivity.this,Myself.class);
			startActivity(intent);
			finish();
			break;

		
		}
	}
	private void initView() {
		phoneTextView = (TextView) findViewById(R.id.phone);
		SharedPreferences sharedpre=getSharedPreferences("register",Context.MODE_PRIVATE);
		phone =	sharedpre.getString("phone", "phone");
		phoneTextView.setText(phone);
		
		zhanghaoLayout = (RelativeLayout) findViewById(R.id.zhanghao);
		shoujiLayout = (RelativeLayout) findViewById(R.id.shouji);
	}
}
