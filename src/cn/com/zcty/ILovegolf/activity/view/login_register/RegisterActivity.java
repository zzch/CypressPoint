package cn.com.zcty.ILovegolf.activity.view.login_register;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.HomePageActivity;
import cn.com.zcty.ILovegolf.tools.RegexMobile;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
/**
 * 注册类
 * @author deii
 *
 */
public class RegisterActivity extends Activity {

	private Button yanzhengma;
	private AlertDialog dialog;
	private Button confirm;
	private EditText et_mobile_reg;//手机号
	private EditText et_password_reg;//密码
	private EditText et_confirm_password; //确认密码
	private EditText et_yanzhengma_reg;//验证码
	private String isBoolean;
	private Button fanhuiButton;
	private int code;
	 private String err;
     private String messg = "";
     private String result;
     private String message;
     private String registerData;
     private String yanzhengmsg;
     private String yanzhengResut;
     private String yanzhengJson;
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:
				 Toast.makeText(RegisterActivity.this, "用户名不能为空！", Toast.LENGTH_LONG).show();
				break;
			case 1:
				Toast.makeText(RegisterActivity.this, "用户名不合法！", Toast.LENGTH_LONG).show();
				break;
			case 2:
				 Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_LONG).show();
				break;
			case 3:
				 Toast.makeText(RegisterActivity.this, "确认密码不能为空！", Toast.LENGTH_LONG).show();
				break;
			case 4:
				
				Toast.makeText(RegisterActivity.this, "验证码不能为空！", Toast.LENGTH_LONG).show();
				
				 break;
			case 8:
				Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();

			case 6:
				
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(RegisterActivity.this, "网络异常！", Toast.LENGTH_LONG).show();
				}else {
						//弹出一个Dialog
						//fristdialog();
						if(result==null){
							registerData = (String) msg.obj;
							new MyTask_().start();
						}else{
							Toast.makeText(RegisterActivity.this, "恭喜您，注册成功！", Toast.LENGTH_LONG).show();
							 Intent intent=new Intent(RegisterActivity.this,HomePageActivity.class);
								startActivity(intent);
								finish();
						}
						
					}
					
				break;
			case 7:
				 Toast.makeText(RegisterActivity.this, "确认密码与密码不一致！", Toast.LENGTH_LONG).show();
				break;
			case 5:
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(RegisterActivity.this, "网络异常！", Toast.LENGTH_LONG).show();
				}else {
						//弹出一个Dialog
						//fristdialog();
						if(yanzhengResut==null){
							yanzhengJson = (String) msg.obj;
							new RegisterTask_().start();
						}else{
							Toast.makeText(RegisterActivity.this, "获取验证码成功", Toast.LENGTH_LONG).show();
							
						}
						
					}
				break;
			case 9:
				daojishi.cancel();
				Toast.makeText(RegisterActivity.this, yanzhengmsg, Toast.LENGTH_LONG).show();
				et_mobile_reg.setText("");
				et_password_reg.setText("");
				et_confirm_password.setText("");
				et_yanzhengma_reg.setText("");
				yanzhengma.setText("获取验证码");
				break;
			}
			
			
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		ShouYeActivity.getInstance().addActivity(this);
		//找控件
		et_mobile_reg = (EditText) findViewById(R.id.et_mobile_reg);
		et_password_reg = (EditText) findViewById(R.id.et_password_reg);
		et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
		et_yanzhengma_reg = (EditText) findViewById(R.id.et_yanzhengma_reg);
		yanzhengma = (Button) findViewById(R.id.but_getyanzhengma);
		fanhuiButton = (Button) findViewById(R.id.fanhui);
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegisterActivity.this,ShouYeActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(RegisterActivity.this,ShouYeActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 获取验证码按钮
	 * @param v
	 */
	public void on_yanzhengma(View v){
				//倒计时
		     daojishi.start(); 
			new	RegisterTask().start();
	}
	
	class RegisterTask extends Thread {
		public RegisterTask(){
			
		}
		public void run(){
			getData();
		}
		
		public void getData(){
			
			String phone = et_mobile_reg.getText().toString().trim();
			String path=APIService.YANZHENGMA+"phone="+phone;
			Log.i("path======", "----"+path);
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("JsonData======", "----"+jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				yanzhengResut = jsonObject.getString("result");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	Message msg = handler.obtainMessage();
	    	msg.obj = jsonData;
	    	msg.what = 5;			
    		handler.sendMessage(msg);
		}
		
		
	}
	/**
	 * 验证异常
	 * @author yubo
	 *
	 */
	class RegisterTask_ extends Thread {
		
		public void run(){
			getData();
		}
		
		public void getData(){
			
			
			try {
				JSONObject jsonObject = new JSONObject(yanzhengJson);
				yanzhengmsg = jsonObject.getString("message");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	Message msg = handler.obtainMessage();
	    	msg.what = 9;			
    		handler.sendMessage(msg);
		}
		
		
	}
	//点击注册按钮
    public void on_but_zhuce(View v){
    	
    	 String mobilename= et_mobile_reg.getText().toString().trim();
		 String mima = et_password_reg.getText().toString().trim();
		 String querenmima = et_confirm_password.getText().toString().trim();
		 String yanzheng  = et_yanzhengma_reg.getText().toString().trim();
		 Message msg = handler.obtainMessage(); 
		 if("".equals(mobilename))
			{
				 msg.what = 0;  
              handler.sendMessage(msg); 
              return;
			}else if(!RegexMobile.VildateMobile(mobilename)){
				 msg.what = 1;  
              handler.sendMessage(msg);
              return;
			}else if("".equals(mima)){
				 msg.what = 2;  
              handler.sendMessage(msg); 
              return;
              
			}else if("".equals(querenmima)){
				 msg.what = 3;  
	              handler.sendMessage(msg); 
	              return;
	              
	              
		  }else if(!mima.equals(querenmima)){
				 msg.what = 7;  
	              handler.sendMessage(msg); 
	              return;
	              
			}
	              else if("".equals(yanzheng)){
					 msg.what = 4;  
		              handler.sendMessage(msg); 
		              return;     
		}
		 
		new MyTask().start();
       }
    
    class MyTask extends Thread{
    
    	public void run(){
    		getregister();	
    	}
    	
    	public void getregister(){
    		//向服务器发送注册数据
			
			String m_mobile = et_mobile_reg.getText().toString().trim();
			String p_password = et_password_reg.getText().toString().trim();
			String confirm_password = et_confirm_password.getText().toString().trim();
			String y_yanzhengma = et_yanzhengma_reg.getText().toString().trim();
			String url=APIService.USERREGISTER+"phone="+m_mobile+"&password="+p_password+"&password_confirmation="+confirm_password+"&verification_code="+y_yanzhengma;
			String data=HttpUtils.HttpClientPost(url);
			Log.i("resgisterss", "------"+data);
			Log.i("resgisterss", "------"+url);
			try {
				
				JSONObject obj = new JSONObject(data);
				result = obj.getString("uuid");
				String uuid=obj.getString("uuid");
				String type=obj.getString("type");
				String nickname=obj.getString("nickname");
				String token_r=obj.getString("token");
				String phone = obj.getString("phone");
				
				//保存数据
				SharedPreferences sharedpre=getSharedPreferences("register",Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedpre.edit();
			    editor.putString("uuid", uuid);
			    editor.putString("type", type);
				editor.putString("nickname", nickname);
				editor.putString("phone", phone);
				editor.putString("token", token_r);
				editor.putString("portrait", "null");
				editor.putString("isfangshi", "1");
				editor.putString("isRegister", "true");
				editor.commit();
	    		
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			Message msg = handler.obtainMessage();
			msg.what = 6;	
			msg.obj = data;
    		handler.sendMessage(msg);
    	}
    }
    
    /**
     * 如果注册异常
     */
    class MyTask_ extends Thread{
    
    	public void run(){
    		getregister();	
    	}
    	
    	public void getregister(){
    		
			
			
		try {
			JSONObject obj = new JSONObject(registerData);
			message = obj.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
		}
			Message msg = handler.obtainMessage();
			msg.what = 8;
			handler.sendMessage(msg);
	    	
    	}
    }
    
    
   		
   			//倒计时  点击后
   		CountDownTimer	daojishi =  new CountDownTimer(60*1000, 1000){

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
   				
   			};
		   
		
    

	  public String HttpClientGet(String url)
		{
		  String str = "";
			try {
				//创建HttpClient对象
				HttpClient client=new DefaultHttpClient();
				//创建请求路径的HttpGet对象
				HttpGet httpGet=new HttpGet(url);   
				//client将response与httpPost连接
				HttpResponse response=client.execute(httpGet);			
				//找到服务返回的状态码 200表示成功
				code=response.getStatusLine().getStatusCode();
				Log.i("code---->", ""+code);
				
					str = EntityUtils.toString(response.getEntity(), "utf-8");
					//return str;
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			return str;
		}
	  
	  
	  public String HttpClientPost(String url)
		{
		  String str = "";
			try {
				//创建HttpClient对象
				HttpClient client=new DefaultHttpClient();
				//创建请求路径的HttpGet对象
				HttpPost httpPost=new HttpPost(url);   
				//client将response与httpPost连接
				HttpResponse response=client.execute(httpPost);			
				//找到服务返回的状态码 200表示成功
				code=response.getStatusLine().getStatusCode();
				Log.i("code---->", ""+code);
				
					str = EntityUtils.toString(response.getEntity(), "utf-8");
					//return str;
				
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			return str;
		}
	}
    
    
