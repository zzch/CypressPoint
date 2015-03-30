package cn.com.zcty.ILovegolf.activity.view;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.exercise.adapter.CountCoolAdapter;
import cn.com.zcty.ILovegolf.exercise.adapter.StatisticAdapter;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class StatisticsActivityLand extends Activity{
	private ArrayList<String> parArrayList = new ArrayList<String>();
	private ArrayList<String> scoreArrayList = new ArrayList<String>();
	private ArrayList<String> statusArrayList = new ArrayList<String>();
	private ArrayList<String> scoresArrayList = new ArrayList<String>();
	private ArrayList<String> scoregrid = new ArrayList<String>();
	private TextView dateText;
	private Button backButton;
	private GridView gridView;
	private TextView golfnameTextView;
	private ArrayList<String> countCool = new ArrayList<String>();
	private ArrayList<String> countCoolResult = new ArrayList<String>();
	private ListView countListView;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				getData();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_statistics_land);
		initView();
		setListener();
		new MyTask().start();
	}
	private void initView() {
		backButton = (Button) findViewById(R.id.back1);	
		gridView = (GridView) findViewById(R.id.gridView1);
		dateText = (TextView) findViewById(R.id.golf_date);
		golfnameTextView = (TextView) findViewById(R.id.golf_name);
		SharedPreferences ss = getSharedPreferences("name", MODE_PRIVATE);
		golfnameTextView.setText(ss.getString("name", "name"));
		countListView = (ListView) findViewById(R.id.count);
	}
	private void getData() {
		StatisticAdapter sadapter = new StatisticAdapter(this, scoresArrayList,scoregrid);
		gridView.setAdapter(sadapter);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String date = simpleDateFormat.format(new Date());
		dateText.setText(date);
		countListView.setAdapter(new CountCoolAdapter(this, countCool, countCoolResult));

		TextView tv1 = (TextView)findViewById(R.id.t1);
		tv1.setText(parArrayList.get(0));
		TextView tv2 = (TextView)findViewById(R.id.t2);
		tv2.setText(parArrayList.get(1));
		TextView tv3 = (TextView)findViewById(R.id.t3);
		tv3.setText(parArrayList.get(2));
		TextView tv4 = (TextView)findViewById(R.id.t4);
		tv4.setText(parArrayList.get(3));
		TextView tv5 = (TextView)findViewById(R.id.t5);
		tv5.setText(parArrayList.get(4));
		TextView tv6 = (TextView)findViewById(R.id.t6);
		tv6.setText(parArrayList.get(5));
		TextView tv7 = (TextView)findViewById(R.id.t7);
		tv7.setText(parArrayList.get(6));
		TextView tv8 = (TextView)findViewById(R.id.t8);
		tv8.setText(parArrayList.get(7));
		TextView tv9 = (TextView)findViewById(R.id.t9);
		tv9.setText(parArrayList.get(8));
		TextView tv10 = (TextView)findViewById(R.id.t10);
		tv10.setText(parArrayList.get(9));
		TextView ttv1 = (TextView)findViewById(R.id.tt1);
		ttv1.setText(scoreArrayList.get(0));
		TextView ttv2 = (TextView)findViewById(R.id.tt2);
		ttv2.setText(scoreArrayList.get(1));
		TextView ttv3 = (TextView)findViewById(R.id.tt3);
		ttv3.setText(scoreArrayList.get(2));
		TextView ttv4 = (TextView)findViewById(R.id.tt4);
		ttv4.setText(scoreArrayList.get(3));
		TextView ttv5 = (TextView)findViewById(R.id.tt5);
		ttv5.setText(scoreArrayList.get(4));
		TextView ttv6 = (TextView)findViewById(R.id.tt6);
		ttv6.setText(scoreArrayList.get(5));
		TextView ttv7 = (TextView)findViewById(R.id.tt7);
		ttv7.setText(scoreArrayList.get(6));
		TextView ttv8 = (TextView)findViewById(R.id.tt8);
		ttv8.setText(scoreArrayList.get(7));
		TextView ttv9 = (TextView)findViewById(R.id.tt9);
		ttv9.setText(scoreArrayList.get(8));
		TextView ttv10 = (TextView)findViewById(R.id.tt10);
		ttv10.setText(scoreArrayList.get(9));
		TextView tttv1 = (TextView)findViewById(R.id.ttt1);
		tttv1.setText(statusArrayList.get(0));
		TextView tttv2 = (TextView)findViewById(R.id.ttt2);
		tttv2.setText(statusArrayList.get(1));
		TextView tttv3 = (TextView)findViewById(R.id.ttt3);
		tttv3.setText(statusArrayList.get(2));
		TextView tttv4 = (TextView)findViewById(R.id.ttt4);
		tttv4.setText(statusArrayList.get(3));
		TextView tttv5 = (TextView)findViewById(R.id.ttt5);
		tttv5.setText(statusArrayList.get(4));
		TextView tttv6 = (TextView)findViewById(R.id.ttt6);
		tttv6.setText(statusArrayList.get(5));
		TextView tttv7 = (TextView)findViewById(R.id.ttt7);
		tttv7.setText(statusArrayList.get(6));
		TextView tttv8 = (TextView)findViewById(R.id.ttt8);
		tttv8.setText(statusArrayList.get(7));
		TextView tttv9 = (TextView)findViewById(R.id.ttt9);
		tttv9.setText(statusArrayList.get(8));
		TextView tttv10 = (TextView)findViewById(R.id.ttt10);
		tttv10.setText(statusArrayList.get(9));

		TextView thv1 = (TextView)findViewById(R.id.th1);
		thv1.setText(parArrayList.get(10));
		TextView thv2 = (TextView)findViewById(R.id.th2);
		thv2.setText(parArrayList.get(11));
		TextView thv3 = (TextView)findViewById(R.id.th3);
		thv3.setText(parArrayList.get(12));
		TextView thv4 = (TextView)findViewById(R.id.th4);
		thv4.setText(parArrayList.get(13));
		TextView thv5 = (TextView)findViewById(R.id.th5);
		thv5.setText(parArrayList.get(14));
		TextView thv6 = (TextView)findViewById(R.id.th6);
		thv6.setText(parArrayList.get(15));
		TextView thv7 = (TextView)findViewById(R.id.th7);
		thv7.setText(parArrayList.get(16));
		TextView thv8 = (TextView)findViewById(R.id.th8);
		thv8.setText(parArrayList.get(17));
		TextView thv9 = (TextView)findViewById(R.id.th9);
		thv9.setText(parArrayList.get(18));
		TextView thv10 = (TextView)findViewById(R.id.th10);
		thv10.setText(parArrayList.get(19));
		TextView thv11 = (TextView)findViewById(R.id.th11);
		thv11.setText(parArrayList.get(20));
		TextView tthv1 = (TextView)findViewById(R.id.tth1);
		tthv1.setText(scoreArrayList.get(10));
		TextView tthv2 = (TextView)findViewById(R.id.tth2);
		tthv2.setText(scoreArrayList.get(11));
		TextView tthv3 = (TextView)findViewById(R.id.tth3);
		tthv3.setText(scoreArrayList.get(12));
		TextView tthv4 = (TextView)findViewById(R.id.tth4);
		tthv4.setText(scoreArrayList.get(13));
		TextView tthv5 = (TextView)findViewById(R.id.tth5);
		tthv5.setText(scoreArrayList.get(14));
		TextView tthv6 = (TextView)findViewById(R.id.tth6);
		tthv6.setText(scoreArrayList.get(15));
		TextView tthv7 = (TextView)findViewById(R.id.tth7);
		tthv7.setText(scoreArrayList.get(16));
		TextView tthv8 = (TextView)findViewById(R.id.tth8);
		tthv8.setText(scoreArrayList.get(17));
		TextView tthv9 = (TextView)findViewById(R.id.tth9);
		tthv9.setText(scoreArrayList.get(18));
		TextView tthv10 = (TextView)findViewById(R.id.tth10);
		tthv10.setText(scoreArrayList.get(19));
		TextView tthv11 = (TextView)findViewById(R.id.tth11);
		tthv11.setText(scoreArrayList.get(20));
		TextView ttthv1 = (TextView)findViewById(R.id.ttth1);
		ttthv1.setText(statusArrayList.get(10));
		TextView ttthv2 = (TextView)findViewById(R.id.ttth2);
		ttthv2.setText(statusArrayList.get(11));
		TextView ttthv3 = (TextView)findViewById(R.id.ttth3);
		ttthv3.setText(statusArrayList.get(12));
		TextView ttthv4 = (TextView)findViewById(R.id.ttth4);
		ttthv4.setText(statusArrayList.get(13));
		TextView ttthv5 = (TextView)findViewById(R.id.ttth5);
		ttthv5.setText(statusArrayList.get(14));
		TextView ttthv6 = (TextView)findViewById(R.id.ttth6);
		ttthv6.setText(statusArrayList.get(15));
		TextView ttthv7 = (TextView)findViewById(R.id.ttth7);
		ttthv7.setText(statusArrayList.get(16));
		TextView ttthv8 = (TextView)findViewById(R.id.ttth8);
		ttthv8.setText(statusArrayList.get(17));
		TextView ttthv9 = (TextView)findViewById(R.id.ttth9);
		ttthv9.setText(statusArrayList.get(18));
		TextView ttthv10 = (TextView)findViewById(R.id.ttth10);
		ttthv10.setText(statusArrayList.get(19));
		TextView ttthv11 = (TextView)findViewById(R.id.ttth11);
		ttthv11.setText(statusArrayList.get(20));
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) { 
			Log.i("zhouhe", "zhouhea");


		} 


		else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { 

			Log.i("zhouhe", "zhouheb");

			Intent intent = new Intent(this,StatisticsAvtivity.class);
			startActivity(intent);
			finish();
		}


	}
	private void setListener() {
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				finish();
			}
		});

	}

	class MyTask extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences ss = getSharedPreferences("edit", MODE_PRIVATE);

			String match_uuid = ss.getString("match_uuid", "match_uuid");
			SharedPreferences sp = getSharedPreferences("register",  Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			String path = APIService.DATASTATISTICS+"match_uuid="+match_uuid+"&token="+token;
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("js", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				String jsonArray = jsonObject.getString("scorecards");
				scoresArrayList.add(jsonObject.getString("score"));
				scoregrid.add("成绩");
				scoresArrayList.add(jsonObject.getString("net"));
				scoregrid.add("净杆");
				scoresArrayList.add(jsonObject.getString("putts"));
				scoregrid.add("推杆");
				scoresArrayList.add(jsonObject.getString("penalties"));
				scoregrid.add("罚杆");
				
				countCoolResult.add(jsonObject.getString("longest_drive_length"));
				countCool.add("最远开球距离");
				countCoolResult.add(jsonObject.getString("average_drive_length"));
				Log.i("juli", jsonObject.getString("average_drive_length"));
				countCool.add("平均开球距离");
				countCoolResult.add(jsonObject.getString("drive_fairways_hit"));
				countCool.add("开球命中率");
				countCoolResult.add(jsonObject.getString("scrambles"));
				countCool.add("救球成功率");
				countCoolResult.add(jsonObject.getString("bounce"));
				countCool.add("反弹率");
				countCoolResult.add(jsonObject.getString("advantage_transformation"));
				countCool.add("优势转化率");
				countCoolResult.add(jsonObject.getString("greens_in_regulation"));
				countCool.add("攻果岭率");
				
				countCoolResult.add(jsonObject.getString("putts_per_gir"));
				countCool.add("标准杆上果岭的平均推杆");
				countCool.add("");
				countCoolResult.add(jsonObject.getString("score_par_3"));
				countCool.add("3杆洞");
				countCoolResult.add(jsonObject.getString("score_par_4"));
				countCool.add("4杆洞");
				countCoolResult.add(jsonObject.getString("score_par_5"));
				countCool.add("5杆洞");
				countCoolResult.add(jsonObject.getString("double_eagle"));
				countCool.add("");
				countCool.add("信天翁球");
				countCoolResult.add(jsonObject.getString("eagle"));
				countCool.add("老鹰球");
				countCoolResult.add(jsonObject.getString("birdie"));
				countCool.add("小鸟球");
				countCoolResult.add(jsonObject.getString("par"));
				countCool.add("标准杆");
				countCoolResult.add(jsonObject.getString("birdie"));
				countCool.add("小鸟球");
				countCoolResult.add(jsonObject.getString("bogey"));
				countCool.add("柏忌球");
				countCoolResult.add(jsonObject.getString("double_bogey"));
				countCool.add("双柏忌球");
				JSONObject jsonObject2 = new JSONObject(jsonArray);
				JSONArray jsonArray2 = jsonObject2.getJSONArray("par");
				for(int i=0;i<jsonArray2.length();i++){
					if(jsonArray2.getString(i).equals("null")){
						parArrayList.add("nul");
					}else{
						parArrayList.add(jsonArray2.getString(i));
					}
					Log.i("p", jsonArray2.getString(i));
				}
				JSONArray jsonArray3 = jsonObject2.getJSONArray("score");
				for(int i=0;i<jsonArray3.length();i++){
					if(jsonArray3.getString(i).equals("null")){
						scoreArrayList.add("nul");
					}else{
						scoreArrayList.add(jsonArray3.getString(i));
					}
				}
				JSONArray jsonArray4 = jsonObject2.getJSONArray("status");
				for(int i=0;i<jsonArray4.length();i++){
					if(jsonArray4.getString(i).equals("null")){
						statusArrayList.add("nul");
					}else{
						statusArrayList.add(jsonArray4.getString(i));
					}
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

