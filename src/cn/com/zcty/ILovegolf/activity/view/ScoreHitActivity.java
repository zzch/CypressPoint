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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.MajorScoreAverageAdapter;

public class ScoreHitActivity extends Activity{
	private ArrayList<String> statisticsModels = new ArrayList<String>();
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
	private TextView count1_2;
	private TextView count2_2;
	private TextView count3_2;
	private TextView count4_2;
	private TextView count5_2;
	private TextView count6_2;
	private TextView count7_2;
	private TextView count8_2;
	private TextView count9_2;
	private TextView count10_2;
	private TextView count11_2;
	private TextView count12_2;
	private TextView count13_2;
	private TextView count14_2;
	private TextView count15_2;
	private TextView count16_2;
	private TextView count17_2;
	private TextView count18_2;
	private TextView count1_3;
	private TextView count2_3;
	private TextView count3_3;
	private TextView count4_3;
	private TextView count5_3;
	private TextView count6_3;
	private TextView count7_3;
	private TextView count8_3;
	private TextView count9_3;
	private TextView count10_3;
	private TextView count11_3;
	private TextView count12_3;
	private TextView count13_3;
	private TextView count14_3;
	private TextView count15_3;
	private TextView count16_3;
	private TextView count17_3;
	private TextView count18_3;
	private Button fanhuiButton;
	
	private String count;
	private String count_3;
	private String count_2;
	private String mingzhong;
	private String mingzhong_2;
	private String mingzhong_3;
	private TextView countTextView;
	private TextView mingzhongTextView;
	private TextView count_2TextView;
	private TextView mingzhong2TextView;
	private TextView count_3TextView;
	private TextView mingzhong3TextView;
	private ArrayList<String> counts = new ArrayList<String>();
	private ArrayList<String> counts_1 = new ArrayList<String>();
	private ArrayList<String> counts_2 = new ArrayList<String>();
	private String[] name = {"命中","左侧","右侧"};
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				getData();
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scorehit);
		initView();
		setListeners();
		new ScoreHit().start();
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
		fanhuiButton = (Button) findViewById(R.id.scorecard_hit_back);
		listView = (ListView) findViewById(R.id.major_green_qiudong);
		count1_3 = (TextView) findViewById(R.id.par_shuju_1_3);
		count2_3 = (TextView) findViewById(R.id.par_shuju_2_3);
		count3_3 = (TextView) findViewById(R.id.par_shuju_3_3);
		count4_3 = (TextView) findViewById(R.id.par_shuju_4_3);
		count5_3 = (TextView) findViewById(R.id.par_shuju_5_3);
		count6_3 = (TextView) findViewById(R.id.par_shuju_6_3);
		count7_3 = (TextView) findViewById(R.id.par_shuju_7_3);
		count8_3 = (TextView) findViewById(R.id.par_shuju_8_3);
		count9_3 = (TextView) findViewById(R.id.par_shuju_9_3);
		count10_3= (TextView) findViewById(R.id.par_shuju_10_3);
		count11_3 = (TextView) findViewById(R.id.par_shuju_11_3);
		count12_3 = (TextView) findViewById(R.id.par_shuju_12_3);
		count13_3 = (TextView) findViewById(R.id.par_shuju_13_3);
		count14_3 = (TextView) findViewById(R.id.par_shuju_14_3);
		count15_3 = (TextView) findViewById(R.id.par_shuju_15_3);
		count16_3 = (TextView) findViewById(R.id.par_shuju_16_3);
		count17_3 = (TextView) findViewById(R.id.par_shuju_17_3);
		count18_3 = (TextView) findViewById(R.id.par_shuju_18_3);
		
		count1_2 = (TextView) findViewById(R.id.par_shuju_1_2);
		count2_2 = (TextView) findViewById(R.id.par_shuju_2_2);
		count3_2 = (TextView) findViewById(R.id.par_shuju_3_2);
		count4_2 = (TextView) findViewById(R.id.par_shuju_4_2);
		count5_2 = (TextView) findViewById(R.id.par_shuju_5_2);
		count6_2 = (TextView) findViewById(R.id.par_shuju_6_2);
		count7_2 = (TextView) findViewById(R.id.par_shuju_7_2);
		count8_2 = (TextView) findViewById(R.id.par_shuju_8_2);
		count9_2 = (TextView) findViewById(R.id.par_shuju_9_2);
		count10_2= (TextView) findViewById(R.id.par_shuju_10_2);
		count11_2 = (TextView) findViewById(R.id.par_shuju_11_2);
		count12_2 = (TextView) findViewById(R.id.par_shuju_12_2);
		count13_2 = (TextView) findViewById(R.id.par_shuju_13_2);
		count14_2 = (TextView) findViewById(R.id.par_shuju_14_2);
		count15_2 = (TextView) findViewById(R.id.par_shuju_15_2);
		count16_2 = (TextView) findViewById(R.id.par_shuju_16_2);
		count17_2 = (TextView) findViewById(R.id.par_shuju_17_2);
		count18_2 = (TextView) findViewById(R.id.par_shuju_18_2);
		
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
		count_2TextView = (TextView) findViewById(R.id.green_shuju_scrambles_2);
		mingzhong2TextView = (TextView) findViewById(R.id.green_shuju_percentage_2);
		count_3TextView = (TextView) findViewById(R.id.green_shuju_scrambles_3);
		mingzhong3TextView = (TextView) findViewById(R.id.green_shuju_percentage_3);
		for(int i=0;i<18;i++){
			counts.add("");
			counts_1.add("");
			counts_2.add("");
		}
	}
	private void getData(){
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
		
		count1_2.setText(counts_1.get(0));
		count2_2.setText(counts_1.get(1));
		count3_2.setText(counts_1.get(2));
		count4_2.setText(counts_1.get(3));
		count5_2.setText(counts_1.get(4));
		count6_2.setText(counts_1.get(5));
		count7_2.setText(counts_1.get(6));
		count8_2.setText(counts_1.get(7));
		count9_2.setText(counts_1.get(8));
		count10_2.setText(counts_1.get(9));
		count11_2.setText(counts_1.get(10));
		count12_2.setText(counts_1.get(11));
		count13_2.setText(counts_1.get(12));
		count14_2.setText(counts_1.get(13));
		count15_2.setText(counts_1.get(14));
		count16_2.setText(counts_1.get(15));
		count17_2.setText(counts_1.get(16));
		count18_2.setText(counts_1.get(17));
		
		count1_3.setText(counts_2.get(0));
		count2_3.setText(counts_2.get(1));
		count3_3.setText(counts_2.get(2));
		count4_3.setText(counts_2.get(3));
		count5_3.setText(counts_2.get(4));
		count6_3.setText(counts_2.get(5));
		count7_3.setText(counts_2.get(6));
		count8_3.setText(counts_2.get(7));
		count9_3.setText(counts_2.get(8));
		count10_3.setText(counts_2.get(9));
		count11_3.setText(counts_2.get(10));
		count12_3.setText(counts_2.get(11));
		count13_3.setText(counts_2.get(12));
		count14_3.setText(counts_2.get(13));
		count15_3.setText(counts_2.get(14));
		count16_3.setText(counts_2.get(15));
		count17_3.setText(counts_2.get(16));
		count18_3.setText(counts_2.get(17));
		countTextView.setText(count);
		count_2TextView.setText(count_2);
		count_3TextView.setText(count_3);
		mingzhongTextView.setText(mingzhong);
		mingzhong2TextView.setText(mingzhong_2);
		mingzhong3TextView.setText(mingzhong_3);
	}
	class ScoreHit extends Thread{
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
				JSONObject jsonObject07 = new JSONObject(jsonObject.getString("item_07"));
			
				statisticsModels.add(jsonObject07.getString("drive_fairways_hit"));
				statisticsModels.add(jsonObject07.getString("drive_left_roughs_hit"));	
				statisticsModels.add(jsonObject07.getString("drive_right_roughs_hit"));	
				Log.i("tiancai", jsonObject07.getString("drive_fairways_count"));
				count = jsonObject07.getString("drive_fairways_count");
				mingzhong = jsonObject07.getString("drive_fairways_hit");
				count_2 = jsonObject07.getString("drive_left_roughs_count");
				mingzhong_2 = jsonObject07.getString("drive_left_roughs_hit");
				count_3 = jsonObject07.getString("drive_right_roughs_count");
				mingzhong_3 = jsonObject07.getString("drive_right_roughs_hit");
				JSONArray jsarray = jsonObject07.getJSONArray("holes_of_drive_fairways");
				for(int i=0;i<jsarray.length();i++){
					counts.set(i, jsarray.getString(i));
				}
				JSONArray jsarray_2 = jsonObject07.getJSONArray("holes_of_drive_left_roughs");
				for(int i=0;i<jsarray_2.length();i++){
					counts_1.set(i, jsarray_2.getString(i));
				}
				JSONArray jsarray_3 = jsonObject07.getJSONArray("holes_of_drive_right_roughs");
				for(int i=0;i<jsarray_3.length();i++){
					counts_2.set(i, jsarray_3.getString(i));
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
