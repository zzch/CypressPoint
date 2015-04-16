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
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;

public class GanCountActivity extends Activity{
	private ArrayList<String> statisticsModels = new ArrayList<String>();
	private String double_eagle;//信天翁
	private String eagle;//老鹰球
	private String birdie;//小鸟球
	private String bogey;//柏忌
	private String double_bogey;//双柏忌
	private String par;//标准杆
	private TextView double_eagleTextView;
	private TextView eagleTextView;
	private TextView birdileTextView;
	private TextView bogeyTextView;
	private TextView double_bogeyTextView;
	private TextView parTextView;
	private TextView green_shuju_scrambles;
	private TextView green_shuju_scrambles_1;
	private Button fanhuiButton;
	private String bounce;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				double_eagleTextView.setText(double_eagle);
				bogeyTextView.setText(bogey);
				double_bogeyTextView.setText(double_bogey);
				eagleTextView.setText(eagle);
				birdileTextView.setText(birdie);
				parTextView.setText(par);
				green_shuju_scrambles.setText(bounce);
				green_shuju_scrambles_1.setText(bounce);
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gancount);
		initView();
		setListeners();
		new GanCount().start();
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
		double_eagleTextView = (TextView) findViewById(R.id.double_eagle_percentage);
		bogeyTextView = (TextView) findViewById(R.id.bogey);
		double_bogeyTextView = (TextView) findViewById(R.id.double_bogey);
		eagleTextView = (TextView) findViewById(R.id.eagle);
		birdileTextView = (TextView) findViewById(R.id.birdie);
		parTextView = (TextView) findViewById(R.id.par);
		green_shuju_scrambles = (TextView) findViewById(R.id.green_shuju_scrambles);
		green_shuju_scrambles_1 = (TextView) findViewById(R.id.green_shuju_scrambles_1);
		fanhuiButton = (Button) findViewById(R.id.scorecard_gancount_back);
	}
	class GanCount extends Thread{
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
				JSONObject jsonObject09 = new JSONObject(jsonObject.getString("item_09"));	
				double_eagle = jsonObject09.getString("double_eagle_percentage");
				eagle = jsonObject09.getString("eagle_percentage");
				birdie = jsonObject09.getString("birdie_percentage");
				bogey = jsonObject09.getString("bogey_percentage");
				double_bogey = jsonObject09.getString("double_bogey_percentage");
				par = jsonObject09.getString("par_percentage");
				bounce = jsonObject09.getString("bounce");
				
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
}
