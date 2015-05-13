package cn.com.zcty.ILovegolf.activity.view;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class RankingActivity extends Activity{
	
	private Button paiming_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ranking);
		initView();
		new Ranking().start();
	}
	
	
	public void initView(){
		paiming_back = (Button) findViewById(R.id.paiming_back);
		paiming_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    finish();
			}
		});
	}
	/**
	 * 获取排名的数据
	 * @author Administrator
	 *
	 */
	class Ranking extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			Intent intent=getIntent();
			String	uuid = intent.getStringExtra("uuid");
			String path = APIService.RANKING+"token="+token+"&match_uuid="+uuid;
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("rankingdata", path);
			Log.i("rankingdata", jsonData);
		}
	}
}
