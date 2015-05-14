package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentOne;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentTwo;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RankingStatics extends FragmentActivity{
	private ArrayList<Fragment> arrayFragment = new ArrayList<Fragment>();
	private ArrayList<String> scoreArrayList = new ArrayList<String>();
	private ArrayList<String> statusArrayList = new ArrayList<String>();
	private ArrayList<String> parArrayList = new ArrayList<String>();
	private String match_uuid;
	private ViewPager tablePager;;
	private RadioGroup radioGroup;
	private RadioButton radioButton_qian;
	private RadioButton radioButton_hou;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rankingstatics);
		initView();
		setListeners();
		getData();
		new MyTask().start();
	}
	/*
	 * 监听器
	 */
	private void setListeners() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@SuppressLint("NewApi")
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (group.getCheckedRadioButtonId()) {
				case R.id.mainTabs_radio_qian:
					radioButton_qian.setBackgroundResource(R.drawable.group_1);
					radioButton_qian.setText("前九");
					radioButton_qian.setTextColor(0xff17191c);
					radioButton_hou.setBackgroundResource(R.drawable.group_2);
					radioButton_hou.setText("后九");
					radioButton_hou.setTextColor(0xffF8d57a);
					tablePager.setCurrentItem(0);
					break;

				case R.id.mainTabs_radio_hou:
					radioButton_hou.setBackgroundResource(R.drawable.group_3);
					radioButton_hou.setText("后九");
					radioButton_hou.setTextColor(0xff17191c);
					radioButton_qian.setBackgroundResource(R.drawable.group_4);
					radioButton_qian.setText("前九");
					radioButton_qian.setTextColor(0xffF8d57a);
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
	/*
	 * 处理数据
	 */
	private void getData() {
		arrayFragment.add(new StaticsFragmentOne(parArrayList,scoreArrayList,statusArrayList));
		arrayFragment.add(new StaticsFragmentTwo(parArrayList,scoreArrayList,statusArrayList));
		tablePager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
	}
	/*
	 * 初始化
	 */
	private void initView() {
		match_uuid = getIntent().getStringExtra("uuid");
		tablePager = (ViewPager) findViewById(R.id.viewpager);
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioButton_qian = (RadioButton) findViewById(R.id.mainTabs_radio_qian);
		radioButton_hou = (RadioButton) findViewById(R.id.mainTabs_radio_hou);
		radioButton_qian.setChecked(true);
		radioButton_qian.setBackgroundResource(R.drawable.group_1);
	}
	/**
	 * fragment的适配器
	 * @author Administrator
	 *
	 */
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
	/**
	 * 拿到统计数据
	 * @author Administrator
	 *
	 */
	class MyTask extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			
			SharedPreferences sp = getSharedPreferences("register",  Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			String path = APIService.DATASTATISTICS+"match_uuid="+match_uuid+"&token="+token;
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("js", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				String jsonArray = jsonObject.getString("scorecards");
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
				/*Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);*/
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
