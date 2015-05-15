package cn.com.zcty.ILovegolf.activity.view;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class InviteActivity extends Activity{
	private String passWord;
	/*
	 * 四位密码
	 */
	private String first;
	private String second;
	private String thirdly;
	private String fourthly;

	private TextView firstTextView;
	private TextView secondTextView;
	private TextView thirdlyTextView;
	private TextView fourthlyTextView;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(InviteActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(InviteActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(InviteActivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{				
					first = passWord.substring(0,1);
					second = passWord.substring(1,2);
					thirdly = passWord.substring(2, 3);
					fourthly = passWord.substring(3, 4);
					firstTextView.setText(first);
					secondTextView.setText(second);
					thirdlyTextView.setText(thirdly);
					fourthlyTextView.setText(fourthly);
					
				}
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_invite);
		new Invite().start();
		initView();
	}
	private void initView() {
		firstTextView = (TextView) findViewById(R.id.first);
		secondTextView = (TextView) findViewById(R.id.second);
		thirdlyTextView = (TextView) findViewById(R.id.thirdly);
		fourthlyTextView = (TextView) findViewById(R.id.fourthly);
	}
	/*
	 * 点击事件
	 */
	public void onclick(View v){
		switch (v.getId()) {
		case R.id.invite_back:
			finish();
			break;

		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
	/**
	 * 获得比赛口令
	 * @author Administrator
	 *
	 */
	class Invite extends  Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}

		private void getData() {
			Intent intent = getIntent();
			String uuid = intent.getStringExtra("uuid");
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.PASSWORD+"token="+token+"&uuid="+uuid;
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("invite", path);
			Log.i("invite", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				passWord = jsonObject.getString("password");
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
	

}
