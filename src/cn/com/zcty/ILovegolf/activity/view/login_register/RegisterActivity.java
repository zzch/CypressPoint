package cn.com.zcty.ILovegolf.activity.view.login_register;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 注册类
 * @author deii
 *
 */
public class RegisterActivity extends Activity implements OnClickListener{

	private Button yanzhengma;
	private AlertDialog dialog;
	private Button confirm;
	private EditText et_mobile_reg;//手机号
	private EditText et_password_reg;//密码
	private EditText et_yanzhengma_reg;//验证码
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		//找控件
		et_mobile_reg=(EditText) findViewById(R.id.et_mobile_reg);
		et_password_reg=(EditText) findViewById(R.id.et_password_reg);
		et_yanzhengma_reg=(EditText) findViewById(R.id.et_yanzhengma_reg);
		yanzhengma=(Button) findViewById(R.id.but_getyanzhengma);
	}
	/**
	 * 获取验证码按钮
	 * @param v
	 */
	public void on_yanzhengma(View v){
		
		new AsyncTask<Void, Void, Void>() {
			
			
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				//倒计时
				daojishi();
				SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
				String token=sp.getString("token", "token");
				String m_mobile = et_mobile_reg.getText().toString().trim();
				String path=APIService.YANZHENGMA+"m_mobile="+m_mobile+"&token"+token;	
				
				
			}
			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				//获取发送到手机上的验证码
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
			
			
		}.execute();
		
	}
	
	//点击注册按钮
    public void on_but_zhuce(View v){
    	new AsyncTask<Void, Void, Void>(){

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//弹出一个Dialog
				fristdialog();
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				//向服务器发送注册数据
				SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
				String token=sp.getString("token", "token");
				String m_mobile = et_mobile_reg.getText().toString().trim();
				String p_password = et_password_reg.getText().toString().trim();
				String y_yanzhengma = et_yanzhengma_reg.getText().toString().trim();
				String url=APIService.USERREGISTER+"m_mobile="+m_mobile+"&p_password="+p_password+"&y_yanzhengma="+y_yanzhengma+"&token="+token;
				//String data=HttpUtils.HttpClientPost(url);
			}

			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				return null;
			}
    		
    	}.execute();
    	
       }
    
    /**
   	 * 点击获取验证码时，倒计时
   	 */
   	public void daojishi(){
   		//判断获取验证码是否被点击
   		boolean isChecked=false;
   		if(isChecked){
   			//未点击显示
   			yanzhengma.setText("获取验证码");
   		}else{
   			//倒计时  点击后
   			new CountDownTimer(60*1000, 1000){

   				//计时结束
   				@Override
   				public void onFinish() {
   					// TODO Auto-generated method stub
   					yanzhengma.setText("获取验证码");
   				}
                   //正在计时
   				@Override
   				public void onTick(long millisUntilFinished) {
   					// TODO Auto-generated method stub
   					yanzhengma.setText(""+millisUntilFinished/1000+"");
   				}
   				
   			}.start();
		   }
		}
    
   	/**
     * 点击注册按钮后弹出一个Dialog
     */
    public void fristdialog(){
    	AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.register_dialog, null);
		confirm=(Button)view.findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
       }

    /**
     * dialog中确定按钮的点击事件
     */
	  @Override
	   public void onClick(View v) {
		// TODO Auto-generated method stub
		  Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
			startActivity(intent);
			finish();
	  }
	  
	  
	}
    
    
