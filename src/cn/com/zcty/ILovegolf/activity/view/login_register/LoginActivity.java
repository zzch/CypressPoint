package cn.com.zcty.ILovegolf.activity.view.login_register;




import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.User;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ��¼��
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
	 * ע�ᰴť
	 * @param v
	 */
	public void register(View v){
		Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
		startActivity(intent);
		finish();
	}
	/**
	 * ��¼��ť
	 * @param v
	 */
	public void login(View v){
		
		 Toast.makeText(this, "������ע�ᣡ", Toast.LENGTH_LONG);
		//��ȡ�û���
		u_name=et_username.getText().toString().trim();
		//��ȡ����
		p_pwd=et_password.getText().toString().trim();
		if("".equals(u_name))
		{
			Toast.makeText(this, "�û�������Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}else if(!cn.com.zcty.ILovegolf.tools.RegexMobile.VildateMobile(u_name)){
			Toast.makeText(this, "�û������Ϸ�", Toast.LENGTH_SHORT).show();
			return;
		}else if("".equals(p_pwd)){
			Toast.makeText(this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
			//��֤�ɹ������������������
			
		
	}
	
	/**
	 * �������밴ť
	 * @param v
	 */
	public void forget_password(View v){
		
		Intent intent=new Intent(LoginActivity.this,ForGetPasswordActivity.class);
		startActivity(intent);
		finish();
		
	}
	

}
