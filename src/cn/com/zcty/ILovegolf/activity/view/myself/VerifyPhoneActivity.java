package cn.com.zcty.ILovegolf.activity.view.myself;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import cn.com.zcty.ILovegolf.activity.view.HomePageActivity;
import cn.com.zcty.ILovegolf.activity.view.login_register.RegisterActivity;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.tools.RegexMobile;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class VerifyPhoneActivity extends Activity {
	private TextView phoneTextView;
	private EditText phoneEditText;
	private Button submitButton;
	private EditText verifyEditText;
	private EditText newpassword;
	private EditText affirmpassword;
	private String verify;
	private Button back;
	private Button but_getyanzhengma;
	private String newpsd;
	private String affirpsd;
	private LinearLayout linear;
	private String phone;
	private String result;
	private String map;
	private String type;
	private boolean dianji;
	private String bangding;
	private String success;
	private Intent intent;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (msg.obj.equals("404") || msg.obj.equals("500")) {
					Toast.makeText(VerifyPhoneActivity.this, "网络异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					if (result == null) {
						map = (String) msg.obj;
						new BingDing_().start();
					} else {
						Toast.makeText(VerifyPhoneActivity.this,
								"已将验证码成功发送至您的手机！", Toast.LENGTH_SHORT).show();

					}

				}
			}
			if (msg.what == 2) {
				daojishi.cancel();
				if (!(result == null || result.equals(""))) {

					Toast.makeText(VerifyPhoneActivity.this, result,
							Toast.LENGTH_LONG).show();
				}
				phoneEditText.setText("");
				newpassword.setText("");
				affirmpassword.setText("");
				but_getyanzhengma.setText("获取验证码");
				dianji = false;
			}
			if (msg.what == 3) {
				if (msg.what == 3) {
					if (msg.obj.equals("404") || msg.obj.equals("500")) {
						Toast.makeText(VerifyPhoneActivity.this, "网络错误，请稍后再试",
								Toast.LENGTH_LONG).show();

					} else if (msg.obj.equals("401")) {
						Toast.makeText(VerifyPhoneActivity.this,
								"帐号异地登录，请重新登录", Toast.LENGTH_LONG).show();
						FileUtil.delFile();
						Intent intent = new Intent(VerifyPhoneActivity.this,
								ShouYeActivity.class);
						startActivity(intent);
						overridePendingTransition(android.R.anim.slide_in_left,
								android.R.anim.slide_out_right);
						finish();
					} else {
						if (result == null) {
							map = (String) msg.obj;
							new VerifyPhone_().start();
						} else if (result.equals("success")) {
							// 保存数据
							SharedPreferences sharedpre = getSharedPreferences(
									"register", Context.MODE_PRIVATE);
							SharedPreferences.Editor editor = sharedpre.edit();
							editor.putString("type", "member");
							editor.putString("phone", phone);
							editor.putString("isfangshi", "1");
							editor.commit();
							Toast.makeText(VerifyPhoneActivity.this, "设置密码成功",
									Toast.LENGTH_LONG).show();
							Intent intent = new Intent(
									VerifyPhoneActivity.this,
									SettingActivity.class);
							startActivity(intent);
							finish();

						}
					}
				}
			}
			if (msg.what == 4) {
				if (result == null) {
					result = "验证码错误";
				}
				Toast.makeText(VerifyPhoneActivity.this, result,
						Toast.LENGTH_LONG).show();

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

	private void initView() {
		back = (Button) findViewById(R.id.back_verify);
		but_getyanzhengma = (Button) findViewById(R.id.but_getyanzhengma);
		phoneEditText = (EditText) findViewById(R.id.exittext_phon);
		linear = (LinearLayout) findViewById(R.id.linear);
		verifyEditText = (EditText) findViewById(R.id.verify_phonesd);
		/*
		 * 设置数字键盘
		 */
		verifyEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		verifyEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);

		newpassword = (EditText) findViewById(R.id.psw);
		affirmpassword = (EditText) findViewById(R.id.qdpsw);
		submitButton = (Button) findViewById(R.id.tijiao);
		bangding = getIntent().getStringExtra("bangding");

	}


	private void setListeners() {
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				phone = phoneEditText.getText().toString();
				verify = verifyEditText.getText().toString();
				newpsd = newpassword.getText().toString();
				affirpsd = affirmpassword.getText().toString();
				if ("".equals(phone)) {
					Toast.makeText(VerifyPhoneActivity.this, "手机号不能为空！",
							Toast.LENGTH_SHORT).show();
				} else if (!RegexMobile.VildateMobile(phone)) {
					Toast.makeText(VerifyPhoneActivity.this, "手机号不合法！",
							Toast.LENGTH_SHORT).show();
				} else if (verify.length() != 4) {
					Toast.makeText(VerifyPhoneActivity.this, "请输入4位验证码",
							Toast.LENGTH_SHORT).show();
				} else if (!newpsd.equals(affirpsd)) {
					Toast.makeText(VerifyPhoneActivity.this, "两次输入的密码不一致",
							Toast.LENGTH_SHORT).show();
				} else if (newpsd.length() < 1 || affirpsd.length() < 1) {
					Toast.makeText(VerifyPhoneActivity.this, "请把信息输入完整",
							Toast.LENGTH_SHORT).show();
				} else if (newpsd.length() < 6 || newpsd.length() > 15) {
					Toast.makeText(VerifyPhoneActivity.this, "新密码必须大于6位，小于15位",
							Toast.LENGTH_SHORT).show();

				} else {
					// 上传设置的信息 启动线程
					new VerifyPhone().start();
				}
			}
		});
		// 获取验证码
		but_getyanzhengma.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phone = phoneEditText.getText().toString().trim();
				if (RegexMobile.VildateMobile(phone)) {
					if (!dianji) {
						dianji = true;
						// 倒计时
						daojishi.start();
						new BingDing().start();
					//Toast.makeText(VerifyPhoneActivity.this, "以获取验证码", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(VerifyPhoneActivity.this, "手机号为空或手机格式错误！",
							Toast.LENGTH_LONG).show();

				}
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(bangding.equals("1")){
					Log.i("bangding", ""+bangding);
					 intent = new Intent(VerifyPhoneActivity.this, Myself.class);
					 startActivity(intent);
						overridePendingTransition(android.R.anim.slide_in_left,
								android.R.anim.slide_out_right);
						finish();
				}else {
					Log.i("home", "回到首页");
					 intent = new Intent(VerifyPhoneActivity.this, HomePageActivity.class);
					 startActivity(intent);
						overridePendingTransition(android.R.anim.slide_in_left,
								android.R.anim.slide_out_right);
						finish();  
				}
				
			}
		});
	}
	

	// 倒计时 点击后
	CountDownTimer daojishi = new CountDownTimer(60 * 1000, 1000) {

		// 计时结束
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			but_getyanzhengma.setText("获取验证码");
			dianji = false;
		}

		// 正在计时
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			but_getyanzhengma.setText("" + millisUntilFinished / 1000 + "");
		}

	};


	
	class BingDing extends Thread {
		@Override
		public void run() {
			super.run();
			getData();
		}

		public void getData() {
			SharedPreferences sp = getSharedPreferences("register",
					Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			String path = APIService.VERIFYPHONE + "token=" + token + "&phone="
					+ phone;
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
	 * 
	 * @author Administrator
	 * 
	 */
	class BingDing_ extends Thread {
		@Override
		public void run() {
			super.run();
			getData();
		}

		public void getData() {

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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(bangding.equals("1")){
			 intent = new Intent(VerifyPhoneActivity.this, Myself.class);
			 startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
				finish();
		}else {
			Log.i("home", "回到首页");
			 intent = new Intent(VerifyPhoneActivity.this, HomePageActivity.class);
			 startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
				finish();  
		}
		
	}



	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					linear.requestFocus();
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	class VerifyPhone extends Thread {
		@Override
		public void run() {
			super.run();
			getData();
		}

		public void getData() {
			SharedPreferences sp = getSharedPreferences("register",
					Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			String path = APIService.UPGRADE + "token=" + token;
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("phone", phone);
			map.put("password", newpsd);
			map.put("password_confirmation", affirpsd);
			map.put("verification_code", verify);
			String jsonData = HttpUtils.Httpput(path, map);
			Log.i("verifyPhone", path);
			Log.i("verifyPhone", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				result = jsonObject.getString("result");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 3;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
	}

	class VerifyPhone_ extends Thread {
		@Override
		public void run() {
			super.run();
			getData();
		}

		public void getData() {

			try {
				JSONObject jsonObject = new JSONObject(map);
				result = jsonObject.getString("message");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 4;
			handler.sendMessage(msg);
		}
	}
}
