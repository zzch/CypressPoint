package cn.com.zcty.ILovegolf.activity.view.competition;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.CompetitionHomeAdapter;
import cn.com.zcty.ILovegolf.model.CompetitionHome;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class CompetitionHomeActivity extends Activity{
	private ArrayList<CompetitionHome> compttitionArrayList = new ArrayList<CompetitionHome>();
	private ListView listView;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				listView.setAdapter(new CompetitionHomeAdapter(CompetitionHomeActivity.this, compttitionArrayList));
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_competition_home);
		initView();
		new CompetitionHomes().start();
	}
	private void initView() {
		listView = (ListView) findViewById(R.id.competition_listview_home);
	}
	public void qiehuan(View v){
		Intent i = getIntent();
		String uuid = i.getStringExtra("uuid");
		Intent intent = new Intent(CompetitionHomeActivity.this,CompetitionNewActivity.class);
		intent.putExtra("uuid", uuid);
		startActivity(intent);
	}
	/**
	 * 监听返回键
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(this,CompetitionScoreActivity.class);
		startActivity(intent);
		finish();
		
	}
	public void choosepith_back(View v){
         //Intent intent = new Intent(this,CompetitionScoreActivity.class);
		//startActivity(intent);
		finish();
	}
	class CompetitionHomes extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			Intent i = getIntent();
			String uuid = i.getStringExtra("uuid");
			String path = APIService.COMPETITIONCHOOSEPITCH+"token="+token+"&uuid="+uuid;
			String jsonData = HttpUtils.HttpClientGet(path);
			try {
				JSONArray jsonArray = new JSONArray(jsonData);
				for(int j=0;j<jsonArray.length();j++){
					JSONObject jsonObject = jsonArray.getJSONObject(j);
					CompetitionHome competitionHome = new CompetitionHome();
					JSONObject jss = new JSONObject(jsonObject.getString("user"));				
					competitionHome.setNickname(jss.getString("nickname"));
					competitionHome.setBitmap(jss.getString("portrait"));
					competitionHome.setName(jsonObject.getString("name"));
					competitionHome.setRule(jsonObject.getString("rule"));
					competitionHome.setPlayers_count(jsonObject.getString("players_count"));
					competitionHome.setPassword(jsonObject.getString("password"));
					competitionHome.setUuid(jsonObject.getString("uuid"));
					competitionHome.setStarted_at(jsonObject.getString("started_at"));
					compttitionArrayList.add(competitionHome);
				}
				Message msg = handler.obtainMessage();
				msg.what=1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
