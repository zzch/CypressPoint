package cn.com.zcty.ILovegolf.activity.view.competition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.CompetitionAddmatch;
import cn.com.zcty.ILovegolf.tools.SecurityPasswordEditText;

public class CompetitionPassWord extends Activity{
	private SecurityPasswordEditText passwordEditText;
	private Button quedingButton;
	private Button fanhuiButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_pass);
		initView();
		setListeners();
		//getData();
	}
	private void setListeners() {
		quedingButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String passwad = passwordEditText.getInputnumber();
				Log.i("passwad",passwad);
				Intent intent = getIntent();
				String pwd = intent.getStringExtra("pass");
				String uuid = intent.getStringExtra("uuid");
				CompetitionAddmatch add = (CompetitionAddmatch) intent.getSerializableExtra("add");
				if(pwd.equals(passwad)){
					Intent i = new Intent(CompetitionPassWord.this,CompetitionAdd.class);
					i.putExtra("uuid", uuid);
					i.putExtra("pwd", pwd);
					i.putExtra("add", add);
					startActivity(i);
				}else{
					Toast.makeText(CompetitionPassWord.this, "请输入正确的密码", Toast.LENGTH_LONG).show();
				}
			}
		});
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	private void initView() {
		passwordEditText = (SecurityPasswordEditText) findViewById(R.id.password);
		quedingButton = (Button) findViewById(R.id.btn_queding);
		fanhuiButton = (Button) findViewById(R.id.btn_cancel);
		/**
		 * 自动弹出小键盘
		 */
		passwordEditText.setFocusable(true);
		passwordEditText.setFocusableInTouchMode(true);
		InputMethodManager inputManager =  
	               (InputMethodManager)passwordEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
		inputManager.showSoftInput(passwordEditText, 0);
	}
}
