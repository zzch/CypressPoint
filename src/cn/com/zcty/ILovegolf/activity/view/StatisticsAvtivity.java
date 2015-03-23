package cn.com.zcty.ILovegolf.activity.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentOne;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentTwo;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class StatisticsAvtivity extends FragmentActivity{
	private Button backButton;
	private ViewPager tablePager;
	private TextView dateText;
	private RadioGroup radioGroup;
	private RadioButton radioButton_qian;
	private RadioButton radioButton_hou;
	private TextView golfnameTextView;
	private GridView gridView;
	private ArrayList<Fragment> arrayFragment = new ArrayList<Fragment>();
	private ArrayList<String> parArrayList = new ArrayList<String>();
	private ArrayList<String> scoreArrayList = new ArrayList<String>();
	private ArrayList<String> statusArrayList = new ArrayList<String>();
	private ArrayList<String> scoresArrayList = new ArrayList<String>();
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
		setContentView(R.layout.activity_statistics);
		Log.i("zhouhe", a+"");
		initView();
		setListener();	
		new MyTask().start();
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

		Log.i("zhouhe", "zhouheb");
	}


}
	private void getData() {
		arrayFragment.add(new StaticsFragmentOne(parArrayList,scoreArrayList,statusArrayList));
		arrayFragment.add(new StaticsFragmentTwo(parArrayList,scoreArrayList,statusArrayList));
		tablePager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
		gridView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scoresArrayList));
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String date = simpleDateFormat.format(new Date());
		dateText.setText(date);

		
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
					radioButton_qian.setBackgroundResource(R.color.grays);
					radioButton_hou.setBackground(null);
					tablePager.setCurrentItem(0);
					
					break;

				case R.id.mainTabs_radio_hou:
					radioButton_hou.setBackgroundResource(R.color.grays);
					radioButton_qian.setBackground(null);
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
		radioButton_qian.setBackgroundResource(R.color.grays);
		gridView = (GridView) findViewById(R.id.gridView1);
		dateText = (TextView) findViewById(R.id.golf_date);
		golfnameTextView = (TextView) findViewById(R.id.golf_name);
		SharedPreferences ss = getSharedPreferences("name", MODE_PRIVATE);
		golfnameTextView.setText(ss.getString("name", "name"));
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
				scoresArrayList.add(jsonObject.getString("score")+"  成绩");
				Log.i("aa", jsonObject.getString("score"));
				Log.i("aa", jsonObject.getString("net"));
				Log.i("aa", jsonObject.getString("putts"));
				scoresArrayList.add(jsonObject.getString("net")+"  净杆");
				scoresArrayList.add(jsonObject.getString("putts")+"  推杆");
				scoresArrayList.add(jsonObject.getString("penalties")+"  罚杆");
				Log.i("par",jsonArray);
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
