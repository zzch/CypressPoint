package cn.com.zcty.ILovegolf.activity.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.CountCoolAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.SectionScoreAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.StatisticAdapter;
import cn.com.zcty.ILovegolf.activity.view.StatisticsAvtivity.MyFragmentPagerAdapter;
import cn.com.zcty.ILovegolf.activity.view.StatisticsAvtivity.MyTask;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentOne;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentTwo;
import cn.com.zcty.ILovegolf.model.MajorStatisticsModel;
import cn.com.zcty.ILovegolf.model.SectionScore;
import cn.com.zcty.ILovegolf.tools.ScrollViewWithListView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MajorStatisticsActivityScord extends FragmentActivity{
	private Button backButton;
	private ViewPager tablePager;
	private TextView dateText;
	private RadioGroup radioGroup;
	private RadioButton radioButton_qian;
	private RadioButton radioButton_hou;
	private TextView golfnameTextView;
	private GridView gridView;
	private ScrollViewWithListView countListView;
	private ArrayList<String> scoregrid = new ArrayList<String>();
	private ArrayList<Fragment> arrayFragment = new ArrayList<Fragment>();
	private ArrayList<String> parArrayList = new ArrayList<String>();
	private ArrayList<String> scoreArrayList = new ArrayList<String>();
	private ArrayList<String> statusArrayList = new ArrayList<String>();
	private ArrayList<String> scoresArrayList = new ArrayList<String>();
	private ArrayList<String> countCool = new ArrayList<String>();
	private ArrayList<String> countCoolResult = new ArrayList<String>();
	private ArrayList<String> qiuDong = new ArrayList<String>();
	private ArrayList<String> qiuDongResult = new ArrayList<String>();
	private ArrayList<String> qiuType = new ArrayList<String>();
	private ArrayList<String> qiuTypeResult = new ArrayList<String>();
	private SectionScore sectionScore = new SectionScore();
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
		int a = this.getWindowManager().getDefaultDisplay().getRotation();
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_majorstatistics);
		initView();
		setListener();	
		new JsonTask().start();
		
	}
	@Override
	protected void onStart() {
		super.onStart();
		Log.i("zhouhe", "zhouhe");
	}

@Override
public void onConfigurationChanged(Configuration newConfig) {
	super.onConfigurationChanged(newConfig);
	if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) { 
		Log.i("zhouhe", "zhouhea");		
		Intent intent = new Intent(this,StatisticsActivityLand.class);
		startActivity(intent);
		finish();
		
	} 

			
	else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { 

	}


}
	private void getData() {
		arrayFragment.add(new StaticsFragmentOne(parArrayList,scoreArrayList,statusArrayList));
		arrayFragment.add(new StaticsFragmentTwo(parArrayList,scoreArrayList,statusArrayList));
		tablePager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
		StatisticAdapter sadapter = new StatisticAdapter(this, scoresArrayList,scoregrid);
		gridView.setAdapter(sadapter);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String date = simpleDateFormat.format(new Date());
		dateText.setText(date);
		countListView.setAdapter(new SectionScoreAdapter(this, sectionScore));
		
	}

	private void setListener() {
		backButton.setOnClickListener(new OnClickListener() {
     
			@Override
			public void onClick(View v) {
			
				finish();
			}
		});
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@SuppressLint("NewApi")
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (group.getCheckedRadioButtonId()) {
				case R.id.mainTabs_radio_qian:
					radioGroup.setBackgroundResource(R.drawable.qianjiu);
					//radioButton_qian.setBackgroundResource(R.drawable.group_1);
					//radioButton_qian.setText("前九");
					//radioButton_qian.setTextColor(0xff17191c);
					//radioButton_hou.setBackgroundResource(R.drawable.group_2);
					//radioButton_hou.setText("后九");
					//radioButton_hou.setTextColor(0xffF8d57a);
					tablePager.setCurrentItem(0);
					
					break;

				case R.id.mainTabs_radio_hou:
					radioGroup.setBackgroundResource(R.drawable.houjiu);
					//radioButton_hou.setBackgroundResource(R.drawable.group_3);
					//radioButton_hou.setText("后九");
					//radioButton_hou.setTextColor(0xff17191c);
					//radioButton_qian.setBackgroundResource(R.drawable.group_4);
					//radioButton_qian.setText("前九");
					//radioButton_qian.setTextColor(0xffF8d57a);
					tablePager.setCurrentItem(1);
					break;
				}
			}
		});
		tablePager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					radioButton_qian.setChecked(true);
			
					break;

				case 1:
					radioButton_hou.setChecked(true);
		
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@SuppressLint("NewApi")
			@Override
			public void onPageScrollStateChanged(int position) {
				
			}
		});
	}

	private void initView() {
		backButton = (Button) findViewById(R.id.back);
		tablePager = (ViewPager) findViewById(R.id.viewpager);
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioButton_qian = (RadioButton) findViewById(R.id.mainTabs_radio_qian);
		radioButton_hou = (RadioButton) findViewById(R.id.mainTabs_radio_hou);
		radioButton_qian.setChecked(true);
		//radioButton_qian.setBackgroundResource(R.drawable.group_1);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		dateText = (TextView) findViewById(R.id.golf_date);
		golfnameTextView = (TextView) findViewById(R.id.golf_name);
		golfnameTextView.setText(getIntent().getStringExtra("name"));
		
		countListView = (ScrollViewWithListView) findViewById(R.id.count);
		
	}
	class MyFragmentPagerAdapter  extends FragmentPagerAdapter{

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return arrayFragment.get(position);
		}

		@Override
		public int getCount() {
			return arrayFragment.size();
		}

	}
	class JsonTask extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			Intent intent = getIntent();
			String jsonData = intent.getStringExtra("JsonData");
			Log.i("Major", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				JSONObject jsonObjects = new JSONObject(jsonObject.getString("item_01"));
				scoresArrayList.add(jsonObjects.getString("score"));
				scoregrid.add("成绩");
				scoresArrayList.add(jsonObjects.getString("net"));
				scoregrid.add("净杆");
				scoresArrayList.add(jsonObjects.getString("putts"));
				scoregrid.add("推杆");
				scoresArrayList.add(jsonObjects.getString("penalties"));
				scoregrid.add("罚杆");
				sectionScore.setFront_6_score(jsonObjects.getString("front_6_score"));
				sectionScore.setMiddle_6_score(jsonObjects.getString("middle_6_score"));
				sectionScore.setLast_6_score(jsonObjects.getString("last_6_score"));
				Log.i("sectionscore",jsonObjects.getString("last_6_score"));
				
				JSONObject jsonObject2 = new JSONObject(jsonObject.getString("scorecards"));
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
