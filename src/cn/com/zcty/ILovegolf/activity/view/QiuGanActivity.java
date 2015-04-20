package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.ClubsAdapter;
import cn.com.zcty.ILovegolf.model.Clubs;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class QiuGanActivity extends Activity{
	private ArrayList<Clubs> clubsArrayList = new ArrayList<Clubs>(); 
	private ListView listView;
	RelativeLayout qiugan_layout;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				getData();

			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_qiugan);
		initView();
		new ClubsQiu().start();
	}
	private void initView() {
		listView = (ListView) findViewById(R.id.listView1);
		qiugan_layout = (RelativeLayout) findViewById(R.id.qiugan_layout);
		qiugan_layout.getBackground().setAlpha(80);
	}
	private void getData() {
		listView.setAdapter(new ClubsAdapter(this,clubsArrayList));
	}
	
	class ClubsQiu extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
			
		}
		
		public void getData(){
			Intent intent = getIntent();
			String JsonData = intent.getStringExtra("JsonData");
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(JsonData);
				JSONObject jsonObject10 = new JSONObject(jsonObject.getString("item_10"));	
				JSONArray jsarray10 = jsonObject10.getJSONArray("clubs");
				for(int i=0;i<jsarray10.length();i++){
					JSONObject j = jsarray10.getJSONObject(i);
					Clubs clubs = new Clubs();
					clubs.setName(j.getString("name"));
					clubs.setUsers(j.getString("uses"));
					clubs.setMaximum_length(j.getString("maximum_length"));
					clubs.setMinimum_length(j.getString("minimum_length"));
					clubs.setAverage_length(j.getString("average_length"));
					clubs.setLess_than_average_length(j.getString("less_than_average_length"));
					clubs.setGreater_than_average_length(j.getString("greater_than_average_length"));
					clubsArrayList.add(clubs);
				}
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
}
