package cn.com.zcty.ILovegolf.activity.view.login_register;


import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.HomePageActivity;
import cn.com.zcty.ILovegolf.activity.view.TabHostActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

public class SplashActivity extends Activity {
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		ShouYeActivity.getInstance().addActivity(this);
		login();
	   }
	public void login(){
		new Thread(){
			public void run(){
				try {
					Thread.sleep(1000);
					next();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	public void next(){
		SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
		String isRegister=sp.getString("isRegister", "false");
		if(isRegister.equals("true")){
			Intent intent=new Intent(this,HomePageActivity.class);
			startActivity(intent);
			finish();	
			
		}else{
			Intent intent=new Intent(this,ShouYeActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
