package cn.com.zcty.ILovegolf.activity.view.myself;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.activity.view.myself.VerifyPhoneActivity.VerifyPhone_;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class ChangesPwsActivity extends Activity {
	private Button backButton;
	private Button submitButton;
	private EditText oldPwsdEditText;
	private EditText newPwsdEditText;
	private EditText affirPwsdEditText;
	private String oldPwsd;
	private String newPwsd;
	private String affirPwsd;
	private String result;
	private String map;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(ChangesPwsActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();

				}else if(msg.obj.equals("403")){
					Toast.makeText(ChangesPwsActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(ChangesPwsActivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
					if(result==null){
						map = (String) msg.obj;
						new ChangesPws_().start();
					}else if(result.equals("success")){
						
						Toast.makeText(ChangesPwsActivity.this, "设置密码成功", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(ChangesPwsActivity.this,SettingActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}
			if(msg.what==2){
				Toast.makeText(ChangesPwsActivity.this, result, Toast.LENGTH_LONG).show();

			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changespws);
		initView();
		setListeners();
	}
	private void setListeners() {
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				oldPwsd = oldPwsdEditText.getText().toString();
				newPwsd = newPwsdEditText.getText().toString();
				affirPwsd = affirPwsdEditText.getText().toString();
				if(oldPwsd.length()<1||newPwsd.length()<1||affirPwsd.length()<1){
					Toast.makeText(ChangesPwsActivity.this, "请把信息输入完整", Toast.LENGTH_LONG).show();
				}else if(!newPwsd.equals(affirPwsd)){
					Toast.makeText(ChangesPwsActivity.this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();

				}else if(newPwsd.length()<6||newPwsd.length()>15){
					Toast.makeText(ChangesPwsActivity.this, "新密码必须大于6位，小于15位", Toast.LENGTH_LONG).show();

				}else{
					new ChangesPws().start();

				}
			}
		});
	}
	private void initView() {
		submitButton = (Button) findViewById(R.id.tijiao);
		oldPwsdEditText = (EditText) findViewById(R.id.psw);
		newPwsdEditText = (EditText) findViewById(R.id.newpsw);
		affirPwsdEditText = (EditText) findViewById(R.id.qdnewpsw);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(ChangesPwsActivity.this,Myself.class);
		startActivity(intent);
		finish();
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.back:
			Intent intent = new Intent(ChangesPwsActivity.this,Myself.class);
			startActivity(intent);
			finish();
			break;

		
		}
	}
	class ChangesPws extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.UPPASSWORD+
					"token="+token+
					"&original_password="+oldPwsd+
					"&password="+newPwsd+
					"&password_confirmation="+affirPwsd;
			String jsonData = HttpUtils.HttpClientPut(path);
			Log.i("changesPws", path);
			Log.i("changesPws", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				result = jsonObject.getString("result");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
		
	}
	/**
	 * 当输入的密码有错误
	 * @author Administrator
	 *
	 */
	class ChangesPws_ extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
		
			try {
				JSONObject jsonObject = new JSONObject(map);
				result = jsonObject.getString("message");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 2;
			handler.sendMessage(msg);
		}
		
	}
}
