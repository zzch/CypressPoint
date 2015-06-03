package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.MajorScoreAverageAdapter;

public class GreenActivity extends Activity{
	private ArrayList<String> statisticsModels = new ArrayList<String>();
	private ArrayList<String> counts = new ArrayList<String>();
	private ArrayList<String> counts_1 = new ArrayList<String>();
	private GridView listView;
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
	private Button fanhuiBut;
	private TextView countTextView;
	private String count;
	private String count_2;
	private String mingzhong;
	private String mingzhong_2;
	private TextView mingzhongTextView;
	private TextView count_2TextView;
	private TextView mingzhong2TextView;
	private String[] name = {"命中","未命中"};
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
		setContentView(R.layout.activity_green);
		initView();
		setListeners();
		new Green().start();
	}
	private void setListeners() {
		fanhuiBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});		
	}
	private void initView() {
		fanhuiBut = (Button) findViewById(R.id.green_back);
		listView = (GridView) findViewById(R.id.major_green_qiudong);
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
		for(int i=0;i<18;i++){
			counts.add("");
			counts_1.add("");
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
		countTextView.setText(count);
		count_2TextView.setText(count_2);
		mingzhongTextView.setText(mingzhong);
		mingzhong2TextView.setText(mingzhong_2);
		
		bgdp(count1);
		bgdp(count2);
		bgdp(count3);
		bgdp(count4);
		bgdp(count5);
		bgdp(count6);
		bgdp(count7);
		bgdp(count8);
		bgdp(count9);
		bgdp(count10);
		bgdp(count11);
		bgdp(count12);
		bgdp(count13);
		bgdp(count14);
		bgdp(count15);
		bgdp(count16);
		bgdp(count17);
		bgdp(count18);
		
		bgdp(count1_2);
		bgdp(count2_2);
		bgdp(count3_2);
		bgdp(count4_2);
		bgdp(count5_2);
		bgdp(count6_2);
		bgdp(count7_2);
		bgdp(count8_2);
		bgdp(count9_2);
		bgdp(count10_2);
		bgdp(count11_2);
		bgdp(count12_2);
		bgdp(count13_2);
		bgdp(count14_2);
		bgdp(count15_2);
		bgdp(count16_2);
		bgdp(count17_2);
		bgdp(count18_2);
	}
	class Green extends Thread{
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
				JSONObject jsonObject06 = new JSONObject(jsonObject.getString("item_06"));
				statisticsModels.add(jsonObject06.getString("gir_percentage"));
				statisticsModels.add(jsonObject06.getString("non_gir_percentage"));	
				count = jsonObject06.getString("gir");
				mingzhong = jsonObject06.getString("gir_percentage");
				count_2 = jsonObject06.getString("gir_to_within_5");
				mingzhong_2 = jsonObject06.getString("gir_to_within_5_percentage");
				JSONArray jsarray = jsonObject06.getJSONArray("holes_of_gir");
				for(int i=0;i<jsarray.length();i++){
					counts.set(i, jsarray.getString(i));
				}
				JSONArray jsarray_2 = jsonObject06.getJSONArray("holes_of_gir_to_within_5");
				for(int i=0;i<jsarray_2.length();i++){
					counts_1.set(i, jsarray_2.getString(i));
				}
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
	@SuppressLint("NewApi")
	public void bgdp(TextView t){
		if(t.getText().toString().equals("")){
			t.setBackground(null);
		}
	}
}
