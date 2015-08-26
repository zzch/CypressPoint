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
public class ShouYeActivity extends Activity {
	private static ShouYeActivity instance; 
	 private List<Activity> activityList = new LinkedList<Activity>();  
     
	 private Intent intent;
	 private Button but2;
     private int code = 404;
    
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
				 hideProgressDialog();
				 if(msg.obj.equals("404")||msg.obj.equals("500")){
					 Toast.makeText(ShouYeActivity.this, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();
				 }else{
				 Log.i("----uuid", ""+uuid);
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
						intent=new Intent(ShouYeActivity.this,RegisterSuccessActivity.class);
					    startActivity(intent);
					    finish();	

					}else{
						
			   }
				
				 }
				break; 
			 }
		}
    		
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
	/**
     * 手机登录
     * @param v
     */
	public void initView(){
		but2 = (Button) findViewById(R.id.but2);
		but2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent=new Intent(ShouYeActivity.this,LoginActivity.class);
				  startActivity(intent);
				
			}
		});
	}
	/**
	 * 一键注册
	 * @param v
	 */
	public void but_register(View v){	
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
				msg.what = 0;	
				msg.obj = data;
	    		handler.sendMessage(msg);
				
			}
      	
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
