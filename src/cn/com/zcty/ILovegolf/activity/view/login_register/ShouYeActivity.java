package cn.com.zcty.ILovegolf.activity.view.login_register;


import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
			 }
			
		}
    		
     };
     Handler h = new Handler(){
    	 public void handleMessage(Message msg) {
    		 if(msg.what==1){
    			 Intent intent = new Intent(ShouYeActivity.this,HomePageActivity.class);
				 startActivity(intent);
				 finish();
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
	}
	
	/**
	 * 一键注册
	 * @param v
	 */
	public void but_register(View v){
		showProgressDialog("提示", "加载中。。。",ShouYeActivity.this);
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				//请求数据
				String url=APIService.ONE_REGISTER;
				String data=HttpUtils.HttpClientPost(url);
				Log.i("Data----->", ""+data);
				String uuid = null;
				String nickname = null;
				String token = null;
				String type = null;
				String phone = null;
				try {
					//json解析
					JSONObject jsonObject=new JSONObject(data);
					Log.i("jsonarray----->", ""+data);
						uuid=jsonObject.getString("uuid");
						Log.i("uuid--->>", ""+uuid);
						nickname=jsonObject.getString("nickname");
						Log.i("nickname--->>", ""+nickname);
						token=jsonObject.getString("token");
						Log.i("token--->>", ""+token);
						type = jsonObject.getString("type");
						phone = jsonObject.getString("phone");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			    Log.i("----uuid", ""+sp.getString("uuid", ""));
			    editor.commit();
				if(data!=null){
					hideProgressDialog();
					intent=new Intent(ShouYeActivity.this,HomePageActivity.class);
				    startActivity(intent);
				    finish();	
				}else{
					
				}
				
				return null;
			}
		}.execute();
		
		
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
			p_pwd=et_password.getText().toString().trim();
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
			String url=APIService.USERLOGIN+"phone="+u_name+"&password="+p_pwd+"&token="+token;
			String data=HttpClientPost(url);
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
			p_pwd=et_password.getText().toString().trim();
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
			String url=APIService.USERLOGIN+"phone="+u_name+"&password="+p_pwd+"&token="+token;
			String data=HttpClientPost(url);
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
								JSONObject portraitJsonObject = new JSONObject(portraits);
								String portrait = portraitJsonObject.getString("url");
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
		
}
