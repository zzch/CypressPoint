package cn.com.zcty.ILovegolf.activity.view.login_register;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.TabHostActivity;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

/**
 * ��ҳ��
 * @author deii
 *
 */
public class ShouYeActivity extends Activity {

	 private Intent intent;
	 private Context context;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shouye);
	}
	
	/**
	 * һ��ע��
	 * @param v
	 */
	public void but_register(View v){
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				//�������
				String url=APIService.ONE_REGISTER;
				String data=HttpUtils.HttpClientPost(url);
				Log.i("Data----->", ""+data);
				String uuid = null;
				String nickname = null;
				String token = null;
				try {
					//json����
					JSONObject jsonObject=new JSONObject(data);
					Log.i("jsonarray----->", ""+data);
						uuid=jsonObject.getString("uuid");
						Log.i("uuid--->>", ""+uuid);
						nickname=jsonObject.getString("nickname");
						Log.i("nickname--->>", ""+nickname);
						token=jsonObject.getString("token");
						Log.i("token--->>", ""+token);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//�������
				SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
				Editor editor = sp.edit();
			    editor.putString("uuid", uuid);
			    editor.putString("nickname", nickname);
			    editor.putString("token", token);
			    editor.putString("isRegister", "true");
			    Log.i("----uuid", ""+sp.getString("uuid", ""));
			    editor.commit();
				if(data!=null){
					intent=new Intent(ShouYeActivity.this,TabHostActivity.class);
				    startActivity(intent);
				    finish();	
				}else{
					
				}
				
				return null;
			}
		}.execute();
		
		
	}
	/**
	 * ��½
	 * @param v
	 */
	public void but_login(View v){
		intent=new Intent(ShouYeActivity.this,LoginActivity.class);
		startActivity(intent);
		finish();
	}
	/**
	 * �ֻ�ע��
	 * @param v
	 */
	public void but_mobile_reg(View v){
		
	}
 	
}
