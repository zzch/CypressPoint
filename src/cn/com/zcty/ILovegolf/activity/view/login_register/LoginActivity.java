package cn.com.zcty.ILovegolf.activity.view.login_register;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.HomePageActivity;
import cn.com.zcty.ILovegolf.activity.view.login_register.RegisterActivity.RegisterTask;
import cn.com.zcty.ILovegolf.activity.view.login_register.RegisterActivity.RegisterTask_;
import cn.com.zcty.ILovegolf.tools.RegexMobile;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import cn.com.zcty.ILovegolf.utils.ViewUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 首页类
 * @author deii
 *
 */
public class LoginActivity extends Activity {
	private static ShouYeActivity instance; 
	 private List<Activity> activityList = new LinkedList<Activity>();  
     private Button login_back;
	 private Intent intent;
	 private Context context;
	 private EditText et_username;
     private EditText et_password;
     private Button but_login;
     private Button but_register;
     private String u_name;
     private String p_pwd;
     private int code = 404;
     private String isBoolean;
     private String err;
     private String yanzhengmsg;
     private String yanzhengResut;
     private String yanzhengJson;
     private String messg = "";
     private ProgressDialog progressDialog;
     private LinearLayout linear;
     private LinearLayout layout_yanzhengma;
     private String uuid = null;
     private String nickname = null;
     private String token = null;
     private String type = null;
     private String phone = null;
     private String data;
     private Button but_type;
     private EditText et_yanzhengma;
     private Button but_getyanzhengma;
     private boolean dianji;
   //单例实现返回MyApplication实例  
     public static ShouYeActivity getInstance(){  
         if(null == instance){  
             instance = new ShouYeActivity();  
         }  
         return instance;  
     } 
     Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			 switch(msg.what){
			 
			
			 case 0:
				 hideProgressDialog();
				if(msg.arg1==0||msg.obj.equals("404")||msg.obj.equals("500")){
				   Toast.makeText(LoginActivity.this, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
					}
				if(msg.arg1==1){
					if(messg.equals("无效的密码")){
						
						 Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
					}
					else if(messg.equals("未注册过的用户")){
						Log.i("mimaceshi", msg.obj+"");
						 Toast.makeText(LoginActivity.this, "您还没有注册！", Toast.LENGTH_SHORT).show();
					}else{
						new ShouYeTask_login().start();
					//Toast.makeText(ShouYeActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();		
					    
					    }
					}
					
				 break;
				 
			 case 1:
				 if(msg.obj.equals("404")||msg.obj.equals("500")){
						Toast.makeText(LoginActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
					}else {
							if(yanzhengResut==null){
								yanzhengJson = (String) msg.obj;
								new RegisterTask_().start();
							}else{
								Toast.makeText(LoginActivity.this, "已将验证码成功发送至您的手机！", Toast.LENGTH_SHORT).show();
								
							}
							
						}
				break; 
			 case 2:
				 daojishi.cancel();
					if(!(yanzhengmsg==null||yanzhengmsg.equals(""))){
						
						Toast.makeText(LoginActivity.this, yanzhengmsg, Toast.LENGTH_LONG).show();
					}
					et_username.setText("");
					but_getyanzhengma.setText("获取验证码");
					dianji = false;
				 break;
			 }
		}
    		
     };
     Handler h = new Handler(){
    	 public void handleMessage(Message msg) {
    		 if(msg.what==1){
    			 Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
				 startActivity(intent);			
				 finish();
				 Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show(); 
    		 }
    	 };
     };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		FileUtil.delFile();
		initView();
		setListener();
	}
	//Activity加入到List中  
    public void addActivity(Activity activity){  
        activityList.add(activity);  
    } 
  //遍历每个Activity退出  
    public void exit(){  
        for(Activity activity:activityList){  
            activity.finish();  
        }  
        System.exit(0);  
    }
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Log.i("ddddd", "ddddd");
		ShouYeActivity.getInstance().exit();
		finish();
	}
	public void initView(){
		et_username=(EditText) findViewById(R.id.et_username);
		et_password=(EditText) findViewById(R.id.et_password);
		but_login=(Button) findViewById(R.id.but_login);
		linear = (LinearLayout) findViewById(R.id.linear);
		but_register = (Button) findViewById(R.id.but_register);
		login_back = (Button) findViewById(R.id.login_back);
		but_type = (Button) findViewById(R.id.but_type);
		layout_yanzhengma = (LinearLayout) findViewById(R.id.layout_yanzhengma);
		et_yanzhengma = (EditText) findViewById(R.id.et_yanzhengma);
		but_getyanzhengma = (Button) findViewById(R.id.but_getyanzhengma);
	}

	public void setListener(){
     login_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		but_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
				
			}
		});
		but_type.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(but_type.getText().equals("短信验证码登录")){
					but_type.setText("密码登录");
					layout_yanzhengma.setVisibility(View.VISIBLE);
					et_password.setVisibility(View.GONE);
				}else{
					but_type.setText("短信验证码登录");
					et_password.setVisibility(View.VISIBLE);
					layout_yanzhengma.setVisibility(View.GONE);
					}
				  }
		});
	}
   
	/**
	 * 获取验证码
	 * @param v
	 */
	public void get_yanzhengma(View v){
		u_name=et_username.getText().toString().trim();
		if(isMobileNO(u_name)){
			if(!dianji){
				dianji = true;
				//倒计时
			     daojishi.start(); 
				new	RegisterTask().start();					
			}
		}else{
			Toast.makeText(LoginActivity.this, "手机号为空或手机格式错误！", Toast.LENGTH_LONG).show();

		}
			
	}
	
	//倒计时  点击后
		CountDownTimer	daojishi =  new CountDownTimer(60*1000, 1000){

				//计时结束
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					but_getyanzhengma.setText("获取验证码");
					dianji = false;
				}
               //正在计时
				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
					but_getyanzhengma.setText(""+millisUntilFinished/1000+"");
				}
				
			};
			
			
			
			class RegisterTask extends Thread {
				public RegisterTask(){
					
				}
				public void run(){
					getData();
				}
				
				public void getData(){
					
					
					String path=APIService.YANZHENGMA+"phone="+u_name;
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
			    	msg.what = 1;			
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
			    	msg.what = 2;			
		    		handler.sendMessage(msg);
				}
				
				
			}
	
	/**
	 * 登录
	 * @param v
	 */
	public void login(View v){
		
		new ShouYeTask().start();	
	}
	
	class ShouYeTask extends Thread{
		public void MyTask(){
			
		}
		public void run(){
			getData();
		}
		
		public void getData(){
			
			SharedPreferences sharedpre=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sharedpre.getString("token", "token");
			//获取用户名
			u_name=et_username.getText().toString().trim();
			//获取密码
			p_pwd=et_password.getText().toString();
			Message msg = handler.obtainMessage(); 
			 
			if("".equals(u_name))
			{
				 Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                
			}else if(!RegexMobile.VildateMobile(u_name)){
				 Toast.makeText(LoginActivity.this, "用户名不合法！", Toast.LENGTH_SHORT).show();
			}else if("".equals(p_pwd)){
				 Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
			}
			String url=APIService.USERLOGIN+"&token="+token;
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("phone", u_name);	
			map.put("password", p_pwd);
			String data=httpliuyanpost(url, map);
			Log.i("jsonData======", "----"+data);
			
			
	    		if(code==404||code>=500){
	    			//弹出框提醒 网络异常
	    			isBoolean = "0";
					msg.arg1 = 0;
	    		}else{
	    			//json解析
	    			try {
	    				msg.arg1 = 1;
	    	    		isBoolean = "1";
	    	    		err = "";
	    	    		messg = "";
						JSONObject jsonObject=new JSONObject(data);	
						Log.i("asdfdfasdf", data);
						 err = jsonObject.getString("error_code");
						 messg = jsonObject.getString("message");
						 Log.i("mimaceshi", messg);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			
	    		}
	    		msg.what = 0;	
	    		msg.obj = data;
	    		handler.sendMessage(msg);
		}
		
	}
	
	class ShouYeTask_login extends Thread{
		public void MyTask(){
			
		}
		public void run(){
			getData();
		}
		
		public void getData(){
			
			SharedPreferences sharedpre=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sharedpre.getString("token", "token");
			//获取用户名
			u_name=et_username.getText().toString().trim();
			//获取密码
			p_pwd=et_password.getText().toString();
			Message msg = handler.obtainMessage(); 
			 
			if("".equals(u_name))
			{
				 Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                
			}else if(!RegexMobile.VildateMobile(u_name)){
				 Toast.makeText(LoginActivity.this, "用户名不合法！", Toast.LENGTH_SHORT).show();
			}else if("".equals(p_pwd)){
				 Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
			}
			String url=APIService.USERLOGIN+"&token="+token;
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("phone", u_name);	
			map.put("password", p_pwd);
			String data=httpliuyanpost(url, map);
			Log.i("jsonData======", "----"+url);
	    			//json解析
	    			try {
						JSONObject jsonObject=new JSONObject(data);						
								Log.i("jinai",data);
								String uuid=jsonObject.getString("uuid");
								String type=jsonObject.getString("type");
								String nickname=jsonObject.getString("nickname");
								String token_r=jsonObject.getString("token");	
								String phone = jsonObject.getString("phone");
								String portraits = jsonObject.getString("portrait");
								String portrait = "null";
								if(!portraits.equals("null")){
									JSONObject portraitJsonObject = new JSONObject(portraits);
									 portrait = portraitJsonObject.getString("url");
								}
								
								//保存数据
								SharedPreferences sharedpres=getSharedPreferences("register",Context.MODE_PRIVATE);
								SharedPreferences.Editor editor = sharedpres.edit();
							    editor.putString("uuid", uuid);
							    editor.putString("type", type);
								editor.putString("nickname", nickname);
								editor.putString("token", token_r);
								editor.putString("isRegister", "true");
								editor.putString("isfangshi", "1");
							    editor.putString("phone", phone);
							    editor.putString("portrait", portrait);
								editor.commit();
						 Message msgs = h.obtainMessage();
						 msgs.what=1;
						 h.sendMessage(msgs);
						 
					} catch (JSONException e) {
						e.printStackTrace();
					}
	
		}
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
	 
	 /*
	     * 提示加载
	     */
	     public   void  showProgressDialog(String title,String message,Activity context){
	            if(progressDialog ==null){
	                   progressDialog = ProgressDialog.show( context, title, message,true,true );

	           } else if (progressDialog .isShowing()){
	                   progressDialog.setTitle(title);
	                   progressDialog.setMessage(message);
	           }
	            progressDialog.show();

	    }
	     /*
	     * 隐藏加载
	     */
	     public  void hideProgressDialog(){
	            if(progressDialog !=null &&progressDialog.isShowing()){
	                   progressDialog.dismiss();
	           }
	    }
	     
	     public  String httpliuyanpost(String url,Map<String,String> map){
	 		HttpPost post = new HttpPost(url);
	 		String str = "";
	 		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	 		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
	 		while(it.hasNext()){
	 			Map.Entry<String,String> map1 = it.next();
	 			nvps.add(new BasicNameValuePair(map1.getKey(), map1.getValue()));
	 		}
	 		try {
	 			post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
	 			HttpClient httpClient = new DefaultHttpClient();
	 			HttpResponse httpResponse = httpClient.execute(post);
	 			code = httpResponse.getStatusLine().getStatusCode();
	 			if(code==201||code==200){
	 				str = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
	 				return str;
	 			}
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	 		return code+"";

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
	      
}
