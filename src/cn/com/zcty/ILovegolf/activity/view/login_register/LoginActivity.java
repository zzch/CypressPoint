package cn.com.zcty.ILovegolf.activity.view.login_register;


import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.User;
import cn.com.zcty.ILovegolf.tools.RegexMobile;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 登录类
 * @author deii
 *
 */
public class LoginActivity extends Activity {

	private EditText et_username;
	private EditText et_password;
	private Button but_login;
	private User user;
	private String u_name,p_pwd;
	private SharedPreferences ps;
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		et_username=(EditText) findViewById(R.id.et_username);
		et_password=(EditText) findViewById(R.id.et_password);
		but_login=(Button) findViewById(R.id.but_login);
		
		ps=getSharedPreferences("mobile", Context.MODE_PRIVATE);
		
	}
	
	/**
	 * 注册按钮
	 * @param v
	 */
	public void register(View v){
		Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
		startActivity(intent);
		finish();
	}
	/**
	 * 登录按钮
	 * @param v
	 */
	public void login(View v){
		
		
			//获取用户名
			u_name=et_username.getText().toString().trim();
			//获取密码
			p_pwd=et_password.getText().toString().trim();
			if("".equals(u_name))
			{
				Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
				return;
			}else if(!RegexMobile.VildateMobile(u_name)){
				Toast.makeText(this, "用户名不合法！", Toast.LENGTH_SHORT).show();
				return;
			}else if("".equals(p_pwd)){
				Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
				return;
			}
				//验证成功，向服务器发送请求
				
	}
	
	/**
	 * 忘记密码按钮
	 * @param v
	 */
	public void forget_password(View v){
		
		Intent intent=new Intent(LoginActivity.this,ForGetPasswordActivity.class);
		startActivity(intent);
		finish();
		
	}
	

}
