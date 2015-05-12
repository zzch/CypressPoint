package cn.com.zcty.ILovegolf.activity.view;



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
import android.view.Window;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.tools.SecurityPasswordEditText;
import cn.com.zcty.ILovegolf.tools.SecurityPasswordEditText.OnEditTextListener;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class AddMatchActivity extends Activity {
	SecurityPasswordEditText passwordEditText;
	private String passWord;
	private String message = null;
	private String uuid = null;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(uuid==null||uuid.equals("")){
				new AddMatchYiChang().start();
				}else{
					//验证成功
				}
			}
			if(msg.what==2){
				Toast.makeText(AddMatchActivity.this, message, Toast.LENGTH_LONG).show();
				passwordEditText.setInputnumber(null);//如果错误就清空当前输入的密码
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addematch);
		initView();
		setListeners();
	}

	private void setListeners() {
		passwordEditText.setOnEditTextListener(new OnEditTextListener() {

			@Override
			public void inputComplete(int state, String password) {
				if(password.length()==4){
					passWord = password;
					new AddMatch().start();
				}
			}
		});
	}

	public void initView(){
		passwordEditText =  (SecurityPasswordEditText) findViewById(R.id.password);

	}
	/*
	 * 点击事件
	 */
	public void onclick(View v){
		Intent intent = new Intent(AddMatchActivity.this,QuickScoreActivity.class);
		startActivity(intent);
		finish();
	}
	/**
	 * 验证密码
	 * @author Administrator
	 *
	 */
	class AddMatch extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			//用户的token
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.CHECKINGPASSWORD+"token="+token+"&password="+passWord;
			String jsonData = HttpUtils.HttpClientPost(path);
			Log.i("addMatch", path);
			Log.i("addMatch",jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				uuid = jsonObject.getString("uuid");
				
				
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
	}
	/**
	 * 验证密码
	 * @author Administrator
	 *
	 */
	class AddMatchYiChang extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			//用户的token
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.CHECKINGPASSWORD+"token="+token+"&password="+passWord;
			String jsonData = HttpUtils.HttpClientPost(path);
			Log.i("addMatch", path);
			Log.i("addMatch",jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				message = jsonObject.getString("message");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 2;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
	}
}
