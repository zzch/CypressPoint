package cn.com.zcty.ILovegolf.activity.view.myself;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.RankingActivity;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class BingDingActivity extends Activity{
	private EditText phoneEditText;
	private String phone;
	private Button submitButton;
	private LinearLayout linear;
	private String result = null;
	private String map;
	Handler handler = new Handler(){//接受消息 发送消息
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(BingDingActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();

				}else if(msg.obj.equals("401")){
					Toast.makeText(BingDingActivity.this, "帐号异地登录，请重新登录", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(BingDingActivity.this,ShouYeActivity.class);
					startActivity(intent);
					overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
					finish();
				}else{
					if(result==null){
						map = (String) msg.obj;
						new BingDing_().start();


					}else if(result.equals("success")){					 
						//如果手机号验证成功 跳转下一步
						Intent intent = new Intent(BingDingActivity.this,VerifyPhoneActivity.class);
						intent.putExtra("phone", phone);
						startActivity(intent);
						finish();
					}

				}
			}
			if(msg.what==2){
				Toast.makeText(BingDingActivity.this, result, Toast.LENGTH_LONG).show();
			}
		};
	};
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
					new BingDing().start();
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
		if(phone!=null){			
		if(phone.length()>12){
			phone = phone.substring(3, 14);
		}
		}
		phoneEditText.setText(phone);

		submitButton = (Button) findViewById(R.id.bingding_next);
		linear = (LinearLayout) findViewById(R.id.linear);

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(this,Myself.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
		finish();
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.back:
			Intent intent = new Intent(this,Myself.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			finish();
			break;

		default:
			break;
		}
	}
	class BingDing extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.VERIFYPHONE+"token="+token+"&phone="+phone;
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("bingding", path);
			Log.i("bingding", jsonData);
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
	 * 当手机号码有问题运行
	 * @author Administrator
	 *
	 */
	class BingDing_ extends Thread{
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
	/**
	 * 判断输入手机格式
	 * @param mobiles
	 * @return
	 */
	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^1[3458]\\d{9}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}
	@Override 
	public boolean dispatchTouchEvent(MotionEvent ev) { 
		if (ev.getAction() == MotionEvent.ACTION_DOWN) { 
			View v = getCurrentFocus(); 
			if (isShouldHideInput(v, ev)) { 

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE ); 
				if (imm != null ) { 
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0); 
					linear.requestFocus();
				} 
			} 
			return super .dispatchTouchEvent(ev); 
		} 
		// 必不可少，否则所有的组件都不会有TouchEvent了  
		if (getWindow().superDispatchTouchEvent(ev)) { 
			return true ; 
		} 
		return onTouchEvent(ev); 
	} 
	public  boolean isShouldHideInput(View v, MotionEvent event) { 
		if (v != null && (v instanceof EditText)) { 
			int[] leftTop = { 0, 0 }; 
			//获取输入框当前的location位置  
			v.getLocationInWindow(leftTop); 
			int left = leftTop[0]; 
			int top = leftTop[1]; 
			int bottom = top + v.getHeight(); 
			int right = left + v.getWidth(); 
			if (event.getX() > left && event.getX() < right 
					&& event.getY() > top && event.getY() < bottom) { 
				// 点击的是输入框区域，保留点击EditText的事件  
				return false ; 
			} else { 
				return true ; 
			} 
		} 
		return false ; 
	}

}
