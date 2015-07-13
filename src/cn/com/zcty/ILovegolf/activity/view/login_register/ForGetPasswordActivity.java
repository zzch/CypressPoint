package cn.com.zcty.ILovegolf.activity.view.login_register;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import cn.com.zcty.ILovegolf.activity.view.myself.BingDingActivity;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
/**
 * 忘记密码类
 * @author deii
 *
 */
public class ForGetPasswordActivity extends Activity {
	 private String phone;
	 private String password;
	 private String password_confirmation;
	 private String verification_code;
	 private EditText et_mobile;
	 private EditText codeEditText;
	 private EditText passWordEditText;
	 private EditText surePaEditText;
	 private Button codeButton;
	 private String result;
	 private String jData;
	 private String message;
	 private String forGetPsd;
	 private String forGetData;
	 private String forGetMessage;
	 private LinearLayout linear;
	 private boolean dianji;
	 Handler handler = new Handler(){
		 public void handleMessage(Message msg) {
			 if(msg.what==1){
				 if(msg.obj.equals("404")||msg.obj.equals("500")){
					 Toast.makeText(ForGetPasswordActivity.this, "网络异常", Toast.LENGTH_LONG).show();
				 }else{
					 if(result==null){
						 jData = (String) msg.obj;
						 new RegisterTask_().start();
					 }else{
						 if(result.equals("success")){
							 Toast.makeText(ForGetPasswordActivity.this, "获取验证码成功", Toast.LENGTH_LONG).show();
						 }
					 }
				 }
			 }
			 if(msg.what==2){
				 Toast.makeText(ForGetPasswordActivity.this, message, Toast.LENGTH_LONG).show();
				 daojishi.cancel();
				 codeButton.setText("获取验证码");
				 result = null;
			 }
			 if(msg.what==3){
				 if(msg.obj.equals("404")||msg.obj.equals("500")){
					 Toast.makeText(ForGetPasswordActivity.this, "网络异常", Toast.LENGTH_LONG).show();
				 }else{
					 if(forGetPsd==null){
						 forGetData = (String) msg.obj;
						 new ForGetPassWord_().start();
					 }else{
						 if(forGetPsd.equals("success")){
							 Toast.makeText(ForGetPasswordActivity.this, "重置密码成功", Toast.LENGTH_LONG).show();
							 Intent intent=new Intent(ForGetPasswordActivity.this,ShouYeActivity.class);
							 startActivity(intent);
						     finish();
						 }
						 }
					 }
			 }
			 if(msg.what==4){
				 Toast.makeText(ForGetPasswordActivity.this, forGetMessage, Toast.LENGTH_LONG).show();
				
			 }
		 };
	 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forgetpassword);
		ShouYeActivity.getInstance().addActivity(this);
		initview();
		setListeners();
	}
	
	private void setListeners() {
		codeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("button", "点击了一下");
				phone = et_mobile.getText().toString().trim();
				if(!isMobileNO(phone)){
					Toast.makeText(ForGetPasswordActivity.this, "输入手机格式不对", Toast.LENGTH_LONG).show();
				}else{
				if(!dianji){
				dianji = true;
				daojishi.start();
				Log.i("button1", "点击了一下");
				new RegisterTask().start();
				}
			 }
				
			}
		});
		et_mobile.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				if(!s.toString().equals(phone)){
					dianji = false;
					daojishi.cancel();
					codeButton.setText("获取验证码");
				}
			}
		});
	}

	public void initview(){
		linear = (LinearLayout) findViewById(R.id.linear);
		et_mobile=(EditText) findViewById(R.id.et_mobile);
		codeEditText = (EditText) findViewById(R.id.verification_code);
		passWordEditText = (EditText) findViewById(R.id.pswd);
		surePaEditText = (EditText) findViewById(R.id.pswd_sure);
		codeButton = (Button) findViewById(R.id.forget_mobile);
		setEdittext(passWordEditText);
		setEdittext(surePaEditText);
		
		shuzijianpan(et_mobile);
		shuzijianpan(codeEditText);
		//未点击显示
			codeButton.setText("获取验证码");
	}
	public void setEdittext(EditText t){
		t.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		//t.setInputType(InputType.TYPE_CLASS_PHONE);//只能输入电话号码
		//t.setInputType(InputType.TYPE_CLASS_NUMBER);//只能输入数字
		//t.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);//只能输入邮箱地址
	}
	public void shuzijianpan(EditText s){
		/*
         *设置数字键盘
         */
		s.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		s.setInputType(EditorInfo.TYPE_CLASS_PHONE);
	}
	/**
	 * 忘记密码--返回按钮
	 * @param v
	 */
	public void forgetp_back(View v){
		Intent intent=new Intent(this,LoginActivity.class);
		startActivity(intent);
		finish();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent=new Intent(this,ShouYeActivity.class);
		startActivity(intent);
		finish();
	}
   			//倒计时  点击后
	CountDownTimer	daojishi =	new CountDownTimer(60*1000, 1000){

   				//计时结束
   				@Override
   				public void onFinish() {
   					// TODO Auto-generated method stub
   					codeButton.setText("获取验证码");
   					dianji = false;
   				}
                   //正在计时
   				@Override
   				public void onTick(long millisUntilFinished) {
   					// TODO Auto-generated method stub
   					codeButton.setText(""+millisUntilFinished/1000+"");
   				}
   				
   			};
		   
		
	
	/**
	 * 忘记密码--提交按钮
	 * @param v
	 */
	public void on_submit(View v){
		// phone = et_mobile.getText().toString().trim();
		 password = passWordEditText.getText().toString();
		 password_confirmation = surePaEditText.getText().toString();
		 verification_code = codeEditText.getText().toString().trim();
		if(!isMobileNO(phone)){
			Toast.makeText(ForGetPasswordActivity.this, "输入手机格式不对", Toast.LENGTH_SHORT).show();
		}else if(!password.equals(password_confirmation)){
			Toast.makeText(ForGetPasswordActivity.this, "输入的两次密码不一致", Toast.LENGTH_SHORT).show();

		}else if(password.length()<6||password_confirmation.length()>15){
			Toast.makeText(ForGetPasswordActivity.this, "请输入6位到15位数字的密码", Toast.LENGTH_SHORT).show();
		}else if(verification_code.equals("")||verification_code.length()!=4){
			Toast.makeText(ForGetPasswordActivity.this, "请输入4位数字的验证码", Toast.LENGTH_SHORT).show();

		}else{
			new ForGetPassWord().start();
		}
		 
	}
	/**
	 * 判断输入手机格式
	 * @param mobiles
	 * @return
	 */
	public boolean isMobileNO(String mobiles) {
		if(mobiles==null||mobiles.equals("")){
			return false;
		}
		Pattern p = Pattern
				.compile("^1[3458]\\d{9}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}
	
	/**
	 * 获取验证码
	 * @author Administrator
	 *
	 */
	class RegisterTask extends Thread {
		public RegisterTask(){
			
		}
		public void run(){
			getData();
		}
		
		public void getData(){
			result = null;
			String phone = et_mobile.getText().toString().trim();
			String path=APIService.RESTPSDYANZHENGMA+"phone="+phone;
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("verification_code",jsonData);
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
	/**
	 * 获取验证码有问题
	 * @author Administrator
	 *
	 */
	class RegisterTask_ extends Thread {
		
		public void run(){
			getData();
		}
		
		public void getData(){
			
	    	try {
				JSONObject jsonObject = new JSONObject(jData);
				message = jsonObject.getString("message");
				Log.i("message++", message);
			} catch (JSONException e) {
				e.printStackTrace();
			}
	    	Message msg = handler.obtainMessage();
	    	msg.what=2;
	    	handler.sendMessage(msg);
		}
		
		
	}
	
	
	/**
	 * 重置密码
	 * @author Administrator
	 *
	 */
	class ForGetPassWord extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			
			String path = APIService.RESTPASSWORD;
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("phone", phone);	
			map.put("password", password);
			map.put("password_confirmation", password_confirmation);
			map.put("verification_code", verification_code);
			String jsonData = HttpUtils.Httpput(path, map);
			Log.i("ForGetPassword", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				forGetPsd = jsonObject.getString("result");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what=3;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 重置密码失败
	 * @author Administrator
	 *
	 */
	class ForGetPassWord_ extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			
			
			try {
				JSONObject jsonObject = new JSONObject(forGetData);
				forGetMessage = jsonObject.getString("message");
				Log.i("forGetMessage--", forGetMessage);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what=4;
			handler.sendMessage(msg);
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

}
