package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.MajorScoreAverageAdapter;

public class AverageAvtivity extends Activity{
	private ListView qiudongListView;
	private ArrayList<String> statisticsModels = new ArrayList<String>();
	private String scrambles;
	private String scrambles_percentage;
	private TextView scramblesTextView;
	private TextView percentageTextView;
	private TextView shujuTextView;
	private Button fanhuiButton;
	private String[] name = {"3杆洞","4杆洞","5杆洞"};
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				getData();
				scramblesTextView.setText(scrambles);
				percentageTextView.setText(scrambles_percentage);
				shujuTextView.setText("救平标准杆率 "+scrambles+"/18("+scrambles_percentage+")");
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_average);
		initView();
		setListeners();
		new avaeage().start();
	}
	private void setListeners() {
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});		
	}
	private void initView() {
		qiudongListView = (ListView) findViewById(R.id.major_average_qiudong);
		scramblesTextView = (TextView) findViewById(R.id.avaerage_shuju_scrambles);
		percentageTextView = (TextView) findViewById(R.id.avaerage_shuju_percentage);
		shujuTextView = (TextView) findViewById(R.id.avaerage_shuju);
		fanhuiButton = (Button) findViewById(R.id.scorecard_average_back);
	}
	public void getData(){
		qiudongListView.setAdapter(new MajorScoreAverageAdapter(this, statisticsModels,name));
	}
	class avaeage extends Thread{
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
				JSONObject jsonObject02 = new JSONObject(jsonObject.getString("item_02"));
				statisticsModels.add(jsonObject02.getString("average_score_par_3"));
				statisticsModels.add(jsonObject02.getString("average_score_par_4"));
				statisticsModels.add(jsonObject02.getString("average_score_par_5"));
				scrambles = jsonObject02.getString("scrambles");
				scrambles_percentage = jsonObject02.getString("scrambles_percentage");
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
}
