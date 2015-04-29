package cn.com.zcty.ILovegolf.activity.view.myself;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;

public class SettionsActivity extends Activity{
	private Button exitButton;
	private Button fanhuiButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		ShouYeActivity.getInstance().addActivity(this);
		initView();
		setListeners();
	}
	private void setListeners() {
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettionsActivity.this,SelecPicpupExit.class);
				startActivity(intent);
				
			}
		});
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettionsActivity.this,Myself.class);
				startActivity(intent);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(SettionsActivity.this,Myself.class);
		startActivity(intent);
		finish();
	}
	private void initView() {
		exitButton = (Button) findViewById(R.id.setting_exit);
		fanhuiButton = (Button) findViewById(R.id.button1);
	}
}
