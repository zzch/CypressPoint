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

public class RankingActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking);
		new Ranking().start();
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
