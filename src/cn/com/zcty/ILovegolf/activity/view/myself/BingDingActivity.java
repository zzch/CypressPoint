package cn.com.zcty.ILovegolf.activity.view.myself;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;

public class BingDingActivity extends Activity{
	private EditText phoneEditText;
	private String phone;
	private Button submitButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bingding);
		initView();
		setListeners();
	}

	private void setListeners() {
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				phone = phoneEditText.getText().toString();
				if(!isMobileNO(phone)){
					Toast.makeText(BingDingActivity.this, "输入手机格式不对", Toast.LENGTH_LONG).show();
				}else{
					//上传
				}
			}
		});
	}

	private void initView() {
		phoneEditText = (EditText) findViewById(R.id.exittext_phon);
		/*
         *设置数字键盘
         */
		phoneEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		phoneEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		phone = tm.getLine1Number();
		phoneEditText.setText(phone);
		submitButton = (Button) findViewById(R.id.bingding_next);
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(this,Myself.class);
		startActivity(intent);
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.back:
			Intent intent = new Intent(this,Myself.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}
	public static boolean isMobileNO(String mobiles){  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  

		Matcher m = p.matcher(mobiles);  

		 return m.matches();  
	

		}
}
