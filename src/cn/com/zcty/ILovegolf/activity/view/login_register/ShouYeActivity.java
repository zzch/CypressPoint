package cn.com.zcty.ILovegolf.activity.view.login_register;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
public class ShouYeActivity extends Activity {
	private static ShouYeActivity instance; 
	 private List<Activity> activityList = new LinkedList<Activity>();  

	 private Intent intent;
	 private Context context;
	 private EditText et_username;
     private EditText et_password;
     private Button but_login;
     private String u_name;
     private String p_pwd;
     private int code;
     private String isBoolean;
     private String err;
     private String messg = "";
     private ProgressDialog progressDialog;
     private LinearLayout linear;
     private String uuid = null;
     private String nickname = null;
     private String token = null;
     private String type = null;
     private String phone = null;
     private String data;
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
				 Toast.makeText(ShouYeActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
				 break;
			 case 1:
				 Toast.makeText(ShouYeActivity.this, "用户名不合法！", Toast.LENGTH_SHORT).show();
				 break;
			 case 2:
				 Toast.makeText(ShouYeActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
				 break;
			 case 3:
				if(msg.arg1==0){
				   Toast.makeText(ShouYeActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
					}
				if(msg.arg1==1){
					if(messg.equals("无效的密码")){
						
						 Toast.makeText(ShouYeActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
					}
					else if(messg.equals("未注册过的用户")){
						Log.i("mimaceshi", msg.obj+"");
						 Toast.makeText(ShouYeActivity.this, "您还没有注册！", Toast.LENGTH_SHORT).show();
					}else{
						new ShouYeTask_login().start();
					//Toast.makeText(ShouYeActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();		
					    
					    }
					}
					
				 break;
				 
			 case 4:
				 
				//保存数据
					SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
					Editor editor = sp.edit();
				    editor.putString("uuid", uuid);
				    editor.putString("nickname", nickname);
				    editor.putString("token", token);
				    editor.putString("type", type);
				    editor.putString("phone", phone);
				    editor.putString("isRegister", "true");
				    editor.putString("isfangshi", "0");
				    editor.putString("portrait", "null");
				    editor.commit();
				    Log.i("----uuid", ""+sp.getString("uuid", ""));
				    Log.i("----nickname", ""+sp.getString("nickname", ""));
				    Log.i("Regis===token----", ""+sp.getString("token", ""));
				    Log.i("----type", ""+sp.getString("type", ""));
				    Log.i("----phone", ""+sp.getString("phone", ""));
				    if(data!=null){
				    	Toast.makeText(ShouYeActivity.this, "注册成功！",  Toast.LENGTH_SHORT).show();
						hideProgressDialog();
						intent=new Intent(ShouYeActivity.this,HomePageActivity.class);
					    startActivity(intent);
					    finish();	

					}else{
						
			   }
				
				break; 
			 }
		}
    		
     };
     Handler h = new Handler(){
    	 public void handleMessage(Message msg) {
    		 if(msg.what==1){
    			 Intent intent = new Intent(ShouYeActivity.this,HomePageActivity.class);
				 startActivity(intent);			
				 finish();
				 Toast.makeText(ShouYeActivity.this, "登录成功", Toast.LENGTH_SHORT).show(); 
    		 }
    	 };
     };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shouye);
		FileUtil.delFile();
		initView();
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
		et_username.setInputType(InputType.TYPE_CLASS_PHONE);//只能输入电话号码
		et_username.setInputType(InputType.TYPE_CLASS_NUMBER);//只能输入数字
		et_username.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);//只能输入邮箱地址
		
	}
	
	
	

	/**
	 * 一键注册
	 * @param v
	 */
	public void but_register(View v){
		
		showProgressDialog("提示", "加载中。。。",ShouYeActivity.this);
		
	new RegisterTask().start();
		
	}
	
	  class RegisterTask extends Thread{
			
			public void run(){
				getData();
			}
			
			public void getData(){
				
				//请求数据
				String url=APIService.ONE_REGISTER;
				data=HttpUtils.HttpClientPost(url);
				Log.i("Data----->", ""+data);
				
				Message msg = handler.obtainMessage(); 
				try {
					//json解析
					JSONObject jsonObject=new JSONObject(data);
						uuid=jsonObject.getString("uuid");
						 Log.i("uuid====",uuid );
						nickname=jsonObject.getString("nickname");
						 Log.i("nickname====",nickname );
						token=jsonObject.getString("token");
						 Log.i("token====",token );
						type = jsonObject.getString("type");
						 Log.i("type====",type );
						phone = jsonObject.getString("phone");
						 Log.i("phone====",phone );
						
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				msg.what = 4;	
	    		handler.sendMessage(msg);
				
			}
      	
      }
    
	
	
 
	
	/**
	 * 手机注册
	 * @param v
	 */
	public void but_login(View v){
		intent=new Intent(ShouYeActivity.this,RegisterActivity.class);
		startActivity(intent);
		finish();
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
				 msg.what = 0;  
                 handler.sendMessage(msg); 
                 return;
			}else if(!RegexMobile.VildateMobile(u_name)){
				 msg.what = 1;  
                 handler.sendMessage(msg);
                 return;
			}else if("".equals(p_pwd)){
				 msg.what = 2;  
                 handler.sendMessage(msg); 
                 return;
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
	    		msg.what = 3;	
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
				 msg.what = 0;  
                 handler.sendMessage(msg); 
                 return;
			}else if(!RegexMobile.VildateMobile(u_name)){
				 msg.what = 1;  
                 handler.sendMessage(msg);
                 return;
			}else if("".equals(p_pwd)){
				 msg.what = 2;  
                 handler.sendMessage(msg); 
                 return;
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
		
		Intent intent=new Intent(ShouYeActivity.this,ForGetPasswordActivity.class);
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

	        
	        
	      
}
