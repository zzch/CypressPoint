package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.RankingAdapter;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.model.Ranking;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class RankingActivity extends Activity{

	private Button paiming_back;
	private ListView rankListView;
	private ArrayList<Ranking> rankings = new ArrayList<Ranking>();//adapter的数据
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(RankingActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(RankingActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(RankingActivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
					getData();
				}
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ranking);
		initView();
		setListeners();
		new Rankings().start();
	}

	/*
	 *存放监听器 
	 */
	private void setListeners() {
		// TODO Auto-generated method stub
		paiming_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		rankListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(RankingActivity.this,RankingStatics.class);
				intent.putExtra("uuid", rankings.get(position).getUuid());
				startActivity(intent);
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
	/*
	 * 初始化控件
	 */
	public void initView(){
		paiming_back = (Button) findViewById(R.id.paiming_back);
		rankListView = (ListView) findViewById(R.id.ranking);

	}
	/*
	 * 数据的操作
	 */
	public void getData(){
		rankListView.setAdapter(new RankingAdapter(this, rankings));
		
	}
	/**
	 * 获取排名的数据
	 * @author Administrator
	 *
	 */
	class Rankings extends Thread{
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
			try {
				JSONArray jsonArray = new JSONArray(jsonData);
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Ranking ranking = new Ranking();
					ranking.setUuid(jsonObject.getString("uuid"));
					ranking.setPosition(jsonObject.getString("position"));
					
					String user = jsonObject.getString("user");//获得user map集合
					JSONObject userJsonObject = new JSONObject(user);
					Log.i("ranking", userJsonObject.getString("nickname"));
					ranking.setNickname(userJsonObject.getString("nickname"));
					
					String portrait = userJsonObject.getString("portrait");//获得头像 map集合
					JSONObject portraitJsonObject = new JSONObject(portrait);
					ranking.setPortrait(portraitJsonObject.getString("url"));
					
					ranking.setRecorded_scorecards_count(jsonObject.getString("recorded_scorecards_count"));
					ranking.setTotal(jsonObject.getString("total"));
					rankings.add(ranking);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what=1;
			msg.obj=jsonData;
			handler.sendMessage(msg);
		}
	}
}
