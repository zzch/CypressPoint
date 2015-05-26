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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class VerifyPhoneActivity extends Activity{
	private TextView phoneTextView;
	private Button submitButton;
	private EditText verifyEditText;
	private EditText newpassword;
	private EditText affirmpassword;
	private String verify;
	private String newpsd;
	private String affirpsd;
	private LinearLayout linear;
	private String phone;
	private String result;
	private String map;
	private String type;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(msg.what==1){
					if(msg.obj.equals("404")||msg.obj.equals("500")){
						Toast.makeText(VerifyPhoneActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();

					}else if(msg.obj.equals("403")){
						Toast.makeText(VerifyPhoneActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
						FileUtil.delFile();
						Intent intent = new Intent(VerifyPhoneActivity.this,ShouYeActivity.class);
						startActivity(intent);
						finish();
					}else{
						if(result==null){
							map = (String) msg.obj;
							new VerifyPhone_().start();
						}else if(result.equals("success")){
							//保存数据
							SharedPreferences sharedpre=getSharedPreferences("register",Context.MODE_PRIVATE);
							SharedPreferences.Editor editor = sharedpre.edit();
						    editor.putString("type", "member");
							editor.putString("phone", phone);
							editor.commit();
							Toast.makeText(VerifyPhoneActivity.this, "设置密码成功", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(VerifyPhoneActivity.this,SettingActivity.class);
							startActivity(intent);
							finish();
						}
					}
					}
			}
			if(msg.what==2){
				Toast.makeText(VerifyPhoneActivity.this, result, Toast.LENGTH_LONG).show();
				
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_verify);
		initView();
		setListeners();
	}
	private void setListeners() {
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				verify = verifyEditText.getText().toString();
				newpsd = newpassword.getText().toString();
				affirpsd = affirmpassword.getText().toString();
				if(verify.length()!=4){
					Toast.makeText(VerifyPhoneActivity.this, "请输入4位验证码", Toast.LENGTH_LONG).show();
				}else if(!newpsd.equals(affirpsd)){
					Toast.makeText(VerifyPhoneActivity.this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
				}else if(newpsd.length()<1||affirpsd.length()<1){
					Toast.makeText(VerifyPhoneActivity.this, "请把信息输入完整", Toast.LENGTH_LONG).show();									
				}else if(newpsd.length()<6||newpsd.length()>15){
					Toast.makeText(VerifyPhoneActivity.this, "新密码必须大于6位，小于15位", Toast.LENGTH_LONG).show();

				}else{
					//上传设置的信息 启动线程
					new VerifyPhone().start();
				}
			}
		});
	}
	private void initView() {
		phone = getIntent().getStringExtra("phone");
		
		phoneTextView = (TextView) findViewById(R.id.phone);
		phoneTextView.setText("请输入+86"+phone+"手机上的验证码");
		linear = (LinearLayout) findViewById(R.id.linear);
		verifyEditText = (EditText) findViewById(R.id.verify_phonesd);
		/*
         *设置数字键盘
         */
		verifyEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		verifyEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		
		newpassword = (EditText) findViewById(R.id.psw);
		affirmpassword = (EditText) findViewById(R.id.qdpsw);
		submitButton = (Button) findViewById(R.id.tijiao);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(VerifyPhoneActivity.this,Myself.class);
		startActivity(intent);
		finish();
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.back:
			Intent intent = new Intent(VerifyPhoneActivity.this,Myself.class);
			startActivity(intent);
			finish();
			break;

		
		}
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
	class VerifyPhone extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.UPGRADE+"token="+token+
					"&phone="+phone+
					"&password="+newpsd+
					"&password_confirmation="+affirpsd+"&verification_code="+verify;
			String jsonData = HttpUtils.HttpClientPut(path);
			Log.i("verifyPhone", path);
			Log.i("verifyPhone", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				result = jsonObject.getString("result");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what=1;
			msg.obj = jsonData;
			handler.sendMessage(msg);			
		}
	}
	class VerifyPhone_ extends Thread{
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
			msg.what=2;
			handler.sendMessage(msg);			
		}
	}
}
