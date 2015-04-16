package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
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

public class KickActivity extends Activity{
	private ArrayList<String> statisticsModels = new ArrayList<String>();
	private ArrayList<String> counts = new ArrayList<String>();
	private String count;
	private String mingzhong;
	private TextView countTextView;
	private TextView mingzhongTextView;
	
	
	private ListView listView;
	private TextView count1;
	private TextView count2;
	private TextView count3;
	private TextView count4;
	private TextView count5;
	private TextView count6;
	private TextView count7;
	private TextView count8;
	private TextView count9;
	private TextView count10;
	private TextView count11;
	private TextView count12;
	private TextView count13;
	private TextView count14;
	private TextView count15;
	private TextView count16;
	private TextView count17;
	private TextView count18;
	private String[] name = {"最远max","平均"};
	private Button fanhuiButton;
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
		setContentView(R.layout.activity_kick);
		initView();
		setListeners();
		new Kick().start();
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
		fanhuiButton = (Button) findViewById(R.id.scorecard_green_back);
		listView = (ListView) findViewById(R.id.major_green_qiudong);
		count1 = (TextView) findViewById(R.id.par_shuju_1);
		count2 = (TextView) findViewById(R.id.par_shuju_2);
		count3 = (TextView) findViewById(R.id.par_shuju_3);
		count4 = (TextView) findViewById(R.id.par_shuju_4);
		count5 = (TextView) findViewById(R.id.par_shuju_5);
		count6 = (TextView) findViewById(R.id.par_shuju_6);
		count7 = (TextView) findViewById(R.id.par_shuju_7);
		count8 = (TextView) findViewById(R.id.par_shuju_8);
		count9 = (TextView) findViewById(R.id.par_shuju_9);
		count10 = (TextView) findViewById(R.id.par_shuju_10);
		count11 = (TextView) findViewById(R.id.par_shuju_11);
		count12 = (TextView) findViewById(R.id.par_shuju_12);
		count13 = (TextView) findViewById(R.id.par_shuju_13);
		count14 = (TextView) findViewById(R.id.par_shuju_14);
		count15 = (TextView) findViewById(R.id.par_shuju_15);
		count16 = (TextView) findViewById(R.id.par_shuju_16);
		count17 = (TextView) findViewById(R.id.par_shuju_17);
		count18 = (TextView) findViewById(R.id.par_shuju_18);
		countTextView = (TextView) findViewById(R.id.green_shuju_scrambles);
		mingzhongTextView = (TextView) findViewById(R.id.green_shuju_percentage);
		for(int i=0;i<18;i++){
			counts.add("");
			
		}
	}

	public void getData(){
		listView.setAdapter(new MajorScoreAverageAdapter(this, statisticsModels,name));
		count1.setText(counts.get(0));
		count2.setText(counts.get(1));
		count3.setText(counts.get(2));
		count4.setText(counts.get(3));
		count5.setText(counts.get(4));
		count6.setText(counts.get(5));
		count7.setText(counts.get(6));
		count8.setText(counts.get(7));
		count9.setText(counts.get(8));
		count10.setText(counts.get(9));
		count11.setText(counts.get(10));
		count12.setText(counts.get(11));
		count13.setText(counts.get(12));
		count14.setText(counts.get(13));
		count15.setText(counts.get(14));
		count16.setText(counts.get(15));
		count17.setText(counts.get(16));
		count18.setText(counts.get(17));
		countTextView.setText(count);
		mingzhongTextView.setText(mingzhong);
	}
	class Kick extends Thread{
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
				JSONObject jsonObject08 = new JSONObject(jsonObject.getString("item_08"));
				statisticsModels.add(jsonObject08.getString("longest_drive_length"));
				statisticsModels.add(jsonObject08.getString("average_drive_length"));	
				count = jsonObject08.getString("good_drives");
				mingzhong = jsonObject08.getString("good_drives_percentage");
				JSONArray jsarray = jsonObject08.getJSONArray("holes_of_good_drives");
				for(int i=0;i<jsarray.length();i++){
					counts.set(i, jsarray.getString(i));
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
