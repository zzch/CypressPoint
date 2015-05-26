package cn.com.zcty.ILovegolf.activity.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.CountCoolAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.QiuDongAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.QiuDongTypeAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.StatisticAdapter;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentOne;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentTwo;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
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
	private ListView countListView;
	private ListView qiudongListView;
	private ListView qiudongTypeListView;
	private String match_uuid;
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
	private String name;
	private LinearLayout linear;
	private ProgressDialog progressDialog;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				hideProgressDialog();
				if(msg.obj.equals("404")||msg.obj.equals("500")){//判断是服务端问题
					Toast.makeText(StatisticsAvtivity.this, "网络异常，错误提示"+msg.obj, Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(StatisticsAvtivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(StatisticsAvtivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
					getData();
					linear.setVisibility(View.VISIBLE);
				}
				
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int a = this.getWindowManager().getDefaultDisplay().getRotation();
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_statistics);
		Log.i("zhouhe", a+"");
		initView();
		setListener();	
		linear.setVisibility(View.INVISIBLE);
		showProgressDialog("提示", "正在加载", this);
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
		intent.putExtra("name", name);
		intent.putExtra("uuid", match_uuid);
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
		StatisticAdapter sadapter = new StatisticAdapter(this, scoresArrayList,scoregrid);
		gridView.setAdapter(sadapter);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String date = simpleDateFormat.format(new Date());
		dateText.setText(date);
		countListView.setAdapter(new CountCoolAdapter(this, countCool, countCoolResult));
		qiudongListView.setAdapter(new QiuDongAdapter(this, qiuDong, qiuDongResult));
		qiudongTypeListView.setAdapter(new QiuDongTypeAdapter(this, qiuType, qiuTypeResult));
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
		match_uuid = getIntent().getStringExtra("uuid");
		
		backButton = (Button) findViewById(R.id.back);
		linear = (LinearLayout) findViewById(R.id.linear);
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
		name = getIntent().getStringExtra("name");
		golfnameTextView.setText(name);
		
		countListView = (ListView) findViewById(R.id.count);
		qiudongListView = (ListView) findViewById(R.id.qiudong);
		qiudongTypeListView = (ListView) findViewById(R.id.qiutype);
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
			/*SharedPreferences ss = getSharedPreferences("edit", MODE_PRIVATE);			
			String match_uuid = ss.getString("match_uuid", "match_uuid");*/
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
				countCoolResult.add(jsonObject.getString("gir"));
				countCool.add("攻果岭率");
				countCoolResult.add(jsonObject.getString("putts_per_gir"));
				countCool.add("标准杆上果岭的平均推杆");
				countCool.add("");
				qiuDongResult.add(jsonObject.getString("score_par_3"));
				qiuDong.add("3杆洞");
				qiuDongResult.add(jsonObject.getString("score_par_4"));
				qiuDong.add("4杆洞");
				qiuDongResult.add(jsonObject.getString("score_par_5"));
				qiuDong.add("5杆洞");
				qiuDong.add("");
				qiuTypeResult.add(jsonObject.getString("double_eagle"));
				qiuType.add("信天翁球");
				qiuTypeResult.add(jsonObject.getString("eagle"));
				qiuType.add("老鹰球");
				qiuTypeResult.add(jsonObject.getString("birdie"));
				qiuType.add("小鸟球");
				qiuTypeResult.add(jsonObject.getString("par"));
				qiuType.add("标准杆");
				qiuTypeResult.add(jsonObject.getString("bogey"));
				qiuType.add("柏忌球");
				qiuTypeResult.add(jsonObject.getString("double_bogey"));
				qiuType.add("双柏忌球");
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
				msg.obj = jsonData;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	/*
     * 提示加载
     */
     public   void  showProgressDialog(String title,String message,Activity context){
            if(progressDialog ==null){
                   progressDialog = ProgressDialog.show( context, title, message,true,true );

           } else if (progressDialog .isShowing()){
                   progressDialog.setTitle(title);
                   progressDialog.setMessage(message);
           }
            progressDialog.show();

    }
     /*
     * 隐藏加载
     */
     public  void hideProgressDialog(){
            if(progressDialog !=null &&progressDialog.isShowing()){
                   progressDialog.dismiss();
           }
    }
}
