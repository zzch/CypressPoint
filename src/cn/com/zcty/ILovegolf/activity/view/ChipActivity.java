package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.ChipAdapter;
import cn.com.zcty.ILovegolf.model.Chip;
import cn.com.zcty.ILovegolf.model.Distance;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChipActivity extends Activity{
	private String up_and_downs_count;//成功
	private String shots_within_100;//总计
	private String up_and_downs_percentage;//成功率
	private String chip_ins;
	private String longest_chip_ins_length;
	private Button scorecard_back;
	private TextView up_and_downs_countTextView;
	private TextView shots_within_100TextView;
	private TextView up_and_downs_percentageTextView;
	private TextView chip_insTextView;
	private TextView longestTextView;
	private ListView listView;
	private LinearLayout help_giegan_1;
	private ArrayList<Chip> chipArrayList = new ArrayList<Chip>();
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what == 1){
				up_and_downs_countTextView.setText(up_and_downs_count);
				shots_within_100TextView.setText(shots_within_100);
				up_and_downs_percentageTextView.setText(up_and_downs_percentage);
				chip_insTextView.setText(chip_ins);
				longestTextView.setText(longest_chip_ins_length);
				if(up_and_downs_count.equals("0")){
					listView.setVisibility(View.GONE);
				}else{
					listView.setVisibility(View.VISIBLE);
					listView.setAdapter(new ChipAdapter(ChipActivity.this, chipArrayList));
				}
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chip);
		initView();
		setLister();
		new ChipPitTask().start();
	}
	private void initView() {
		up_and_downs_countTextView = (TextView) findViewById(R.id.chip_chenggong);
		shots_within_100TextView = (TextView) findViewById(R.id.chip_count);
		up_and_downs_percentageTextView = (TextView) findViewById(R.id.chip_chenggonglv);
		chip_insTextView = (TextView) findViewById(R.id.chip_chenggong_2);
		longestTextView = (TextView) findViewById(R.id.chip_distance);
		scorecard_back = (Button) findViewById(R.id.scorecard_back);
		listView = (ListView) findViewById(R.id.listView1);
		help_giegan_1 = (LinearLayout) findViewById(R.id.help_giegan_1);
	}
	public void setLister(){
		scorecard_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		help_giegan_1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ChipActivity.this,HelpAverageActivity.class);
				intent.putExtra("qiegan", "qiegan");
				startActivity(intent);
				
			}
		});
	}
	
	class ChipPitTask extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}

		public void getData(){
			Intent intent = getIntent();
			String JsonData = intent.getStringExtra("JsonData");
			try {
				JSONObject jsonObject = new JSONObject(JsonData);
				JSONObject js05 = new JSONObject(jsonObject.getString("item_05"));
				up_and_downs_count = js05.getString("up_and_downs_count");
				shots_within_100 = js05.getString("shots_within_100");
				up_and_downs_percentage = js05.getString("up_and_downs_percentage");
				chip_ins = js05.getString("chip_ins");
				longest_chip_ins_length = js05.getString("longest_chip_ins_length");
				
				JSONArray jsonArray = js05.getJSONArray("up_and_downs");
				for(int i=0;i<jsonArray.length();i++){
					JSONObject j = jsonArray.getJSONObject(i);
					Chip chip = new Chip();
					chip.setDistance_from_hole(j.getString("distance_from_hole"));
					chip.setPutt_length(j.getString("putt_length"));
					chipArrayList.add(chip);
					Log.i("xianshijiemian", ""+chipArrayList.size());
				}
				
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
	}
	
	
}
