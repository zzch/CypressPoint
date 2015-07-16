package cn.com.zcty.ILovegolf.activity.view.login_register;


import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.HomePageActivity;
import cn.com.zcty.ILovegolf.activity.view.TabHostActivity;
import cn.com.zcty.ILovegolf.activity.view.myself.VerifyPhoneActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class RegisterSuccessActivity extends Activity {
	
	private Button cancel;
	private Button ok;
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_successregister);
		initView();
		
		setlinister();
	   }
	  
	  public void initView(){
		 cancel = (Button) findViewById(R.id.cancel);
		 ok = (Button) findViewById(R.id.ok);
	  }
	
	public void  setlinister(){
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterSuccessActivity.this,HomePageActivity.class);
			    startActivity(intent);
			    finish();
			}
		});
		
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterSuccessActivity.this,VerifyPhoneActivity.class);
			   intent.putExtra("bangding", "2");
				startActivity(intent);
			    finish();
			}
		});
	}
}
