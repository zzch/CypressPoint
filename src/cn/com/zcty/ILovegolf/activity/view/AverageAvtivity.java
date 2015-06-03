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
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.MajorScoreAverageAdapter;

public class AverageAvtivity extends Activity{
	private GridView qiudongListView;
	private ArrayList<String> statisticsModels = new ArrayList<String>();
	private String scrambles;
	private String scrambles_percentage;
	private TextView scramblesTextView;
	private TextView percentageTextView;
	private TextView shujuTextView;
	private Button fanhuiButton;
	private LinearLayout help_layout;
	private String[] name = {"3杆洞","4杆洞","5杆洞"};
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				getData();
				if(scrambles.equals("null")){
					scrambles = "一";
				}
				if(scrambles_percentage.equals("null")){
					scrambles_percentage = "一";
				}
				scramblesTextView.setText(scrambles);
				percentageTextView.setText(scrambles_percentage);			
				shujuTextView.setText("救平标准杆率 "+scrambles+"/18("+scrambles_percentage+")");
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
		
		help_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AverageAvtivity.this,HelpAverageActivity.class);
				intent.putExtra("avg", "avg");
				startActivity(intent);
				
			}
		});
	}
	private void initView() {
		qiudongListView = (GridView) findViewById(R.id.major_average_qiudong);
		scramblesTextView = (TextView) findViewById(R.id.avaerage_shuju_scrambles);
		percentageTextView = (TextView) findViewById(R.id.avaerage_shuju_percentage);
		shujuTextView = (TextView) findViewById(R.id.avaerage_shuju);
		fanhuiButton = (Button) findViewById(R.id.scorecard_average_back);
		help_layout = (LinearLayout) findViewById(R.id.help_layout);
		
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
