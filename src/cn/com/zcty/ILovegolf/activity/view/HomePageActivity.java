package cn.com.zcty.ILovegolf.activity.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.update.UpdateManager;
import cn.com.zcty.ILovegolf.activity.view.count.CountActivity;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.activity.view.myself.Myself;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class HomePageActivity extends Activity {
	private Bitmap bitmap;
	HashMap<String, String> hashMap = new HashMap<String, String>();
	private String url;
	private String  day;
	private String daytime;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				SimpleDateFormat format = new SimpleDateFormat("dd");
				daytime = format.format(new Date());
				Log.i("daytime", daytime);
				SharedPreferences ss = getSharedPreferences("time",
						Context.MODE_PRIVATE);
			    day = ss.getString("day", "day");
				
				 Log.i("2222", ""+day);
				if(!day.equals(daytime)){
					UpdateManager manager = new UpdateManager(
							HomePageActivity.this, hashMap);
					manager.checkUpdate();
					timeDay();
				}
			}
			if (msg.what == 2) {
				FileUtil.saveMyBitmap(bitmap);
				Log.i("imagephoto", bitmap + "");
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_homepage);
		ShouYeActivity.getInstance().addActivity(this);
		SimpleDateFormat format = new SimpleDateFormat("dd");
		day = format.format(new Date());
		Log.i("wwwww", day);
		new Json().start();
		SharedPreferences sp = getSharedPreferences("register",
				Context.MODE_PRIVATE);
		url = sp.getString("portrait", "null");

		if (!url.equals("null")) {
			new Imageloder().start();
		}	
	}
  
	public void timeDay(){
		
		SharedPreferences ss=getSharedPreferences("time",Context.MODE_PRIVATE);
		Editor editor = ss.edit();
	    editor.putString("day", daytime);
	    editor.commit();
	   
	}
	 
	/*
	 * 点击跳转事件
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.homepage_match:
			Intent intent = new Intent(HomePageActivity.this,
					QuickScoreActivity.class);
			startActivity(intent);

			finish();
			break;
		case R.id.homepage_statistics:
			Intent statisticsIntent = new Intent(HomePageActivity.this,
					CountActivity.class);
			startActivity(statisticsIntent);

			finish();
			break;
		case R.id.homepage_personal_center:
			Intent mySelfIntent = new Intent(HomePageActivity.this,
					Myself.class);
			startActivity(mySelfIntent);

			finish();
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		ShouYeActivity.getInstance().exit();
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		finish();
	}

	class Json extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			getData();
		}

		public void getData() {
			String path = APIService.hostName + "/v1/versions/newest";
			Log.i("versoncode", path);
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("versoncode", jsonData);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(jsonData);
				hashMap.put("version", jsonObject.getString("code"));
				hashMap.put("name", jsonObject.getString("name"));

				String file = jsonObject.getString("file");
				JSONObject fileJsonObject = new JSONObject(file);
				hashMap.put("url", fileJsonObject.getString("url"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	}

	class Imageloder extends Thread {
		@Override
		public void run() {
			super.run();
			getData();
		}

		public void getData() {
			bitmap = HttpUtils.imageloder(url);
			Message msg = handler.obtainMessage();
			msg.what = 2;
			handler.sendMessage(msg);
		}
	}
}
