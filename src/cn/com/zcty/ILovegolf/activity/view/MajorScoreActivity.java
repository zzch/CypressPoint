package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.ArrayWheelAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.MajorArrayNumberWheelAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.MajorScoresAdapter;
import cn.com.zcty.ILovegolf.activity.view.ScoreCardUpDateActivity.MyTask;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.activity.view.myself.SelecPicpupExit;
import cn.com.zcty.ILovegolf.model.MajorScore;
import cn.com.zcty.ILovegolf.model.MajorScoreJiQiu;
import cn.com.zcty.ILovegolf.tools.ListViewSwipeGesture;
import cn.com.zcty.ILovegolf.tools.ListViewSwipeGesture.TouchCallbacks;
import cn.com.zcty.ILovegolf.tools.OnWheelChangedListener;
import cn.com.zcty.ILovegolf.tools.WheelView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class MajorScoreActivity extends Activity {
	private String message = "";
	private TextView titleNameText;
	private TextView parText;
	private TextView distanceText;
	private TextView teeColorText;
	private Button fanhuiText;
	public Button major_queding;
	private ListView dataList;
	private Button fanhui;
	private WheelView orderWheel;
	private WheelView distanceWheel;
	private WheelView coolWheel;
	private WheelView penaitsWheel;
	private WheelView countWheel;
	private LinearLayout wheelLinearLayout;
	private RelativeLayout addsRelativeLayout;
	private LinearLayout resultLinearLayout;
	private Button addButton;
	private Button quedingButton;
	private ProgressDialog progressDialog;
	private TextView orderText;
	private String[] coolArray = {"果岭","球道外左侧","球道","球道外右侧","沙坑","不可打"};
	private String[] coolArray_ = {""};
	private String[] pentailsArray = {"1","2","3"};
	private String[] pentailsArray_ = {""};
	private String[] countArray = {"Driver","Putter","3 Wood","5 Wood","7 Wood",
			"2 Hybrid","3 Hybrid","4 Hybrid","5 Hybrid","1 Iron","2 Iron",
			"3 Iron","4 Iron","5 Iron","6 Iron","7 Iron","8 Iron","9 Iron","PW","GW","SW","LW"};
	private String[] countArrays = {"1w","pt","3w","5w","7w",
			"2h","3h","4h","5h","1i","2i",
			"3i","4i","5i","6i","7i",
			"8i","9i","pw","gw","sw","lw"};

	private String po;
	private ArrayList<MajorScore> majorArray = new ArrayList<MajorScore>();
	private MajorArrayNumberWheelAdapter majorNumberAdapter;
	private MajorScoresAdapter adapter;
	private int count = 1;
	private String distance = "0";
	private String cool = "fairway";
	private String pentails = "0";
	private String popal = "1w";
	private String  score;
	private String putts;
	private String penalties;
	private String position;
	private RelativeLayout tianjia;
	private boolean flase = false;
	private String id;
	private int biaoshi;
	private int shanchuid;
	private ArrayList<String> idArrayList = new ArrayList<String>();
	//private ArrayList<MajorScoreJiQiu> addArrayList = new ArrayList<MajorScoreJiQiu>();
	private boolean flase_1 = false;
	private boolean flase_2 = false;
	private boolean flase_3 = false;
	private boolean flase_4 = false;
	private boolean flase_5 = false;
	private String result;
	private String maps;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			hideProgressDialog();
			if(msg.what==3){
				if(majorArray.size()>0){	
					count = Integer.parseInt(majorArray.get(majorArray.size()-1).getOrder());
				}else{
					count = 0;

				}
				/*SharedPreferences sp = MajorScoreActivity.this.getPreferences(MODE_PRIVATE);
				score = sp.getString("score", "0");
				penalties = sp.getString("penalties", "0");*/
				Intent intent = getIntent();
				score = intent.getStringExtra("score");
				penalties = intent.getStringExtra("penalties");
				
				count++;
				orderText.setText(count+"");
				adapter =	new MajorScoresAdapter(MajorScoreActivity.this, majorArray);
				dataList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				setListViewHeightBasedOnChildren(dataList);
			}
			if(msg.what==4){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(MajorScoreActivity.this, "网络异常，错误提示"+msg.obj, Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("401")){
					Toast.makeText(MajorScoreActivity.this, "帐号异地登录，请重新登录", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(MajorScoreActivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
				hideProgressDialog();
				SharedPreferences sp = MajorScoreActivity.this.getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putString("score", score);
				editor.putString("penalties", penalties);
				editor.commit();
				if(result==null){
					maps = (String) msg.obj;
					new JiqiuBianjiTask_().start();
				}else if(result.equals("success")){
					
					finish();
				}
				}
			}
			if(msg.what==5){
				if(message.equals("没有进洞击球")){
					AlertDialog.Builder builder = new Builder(MajorScoreActivity.this)
					.setTitle("提示").setMessage("没有进洞击球").setNegativeButton("确定",null);
					builder.show();
				}
				else if(message.equals("重复进洞击球")){
					AlertDialog.Builder builder = new Builder(MajorScoreActivity.this)
					.setTitle("提示").setMessage("重复进洞击球").setNegativeButton("确定", null);
					builder.show();
				}
				
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_major_score);
		initView();
		getData();
		setListeners();
		//new text().start();
		new JiQiuJiLuTask().start();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			builder("提示", "是否保存", "取消", "确定");
			
			}
		return false;
	}
	  
	private void setListeners() {
		fanhuiText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addsRelativeLayout.setVisibility(View.GONE);
				resultLinearLayout.setVisibility(View.GONE);				
				dataList.setVisibility(View.VISIBLE);
			}
		});
		
			major_queding.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(majorArray.size()>0){
					showProgressDialog("提示", "正在保存", MajorScoreActivity.this);
					new JiqiuBianjiTask().start();
					}else{
						AlertDialog.Builder builder = new Builder(MajorScoreActivity.this)
						.setTitle("提示").setMessage("请添加成绩").setNegativeButton("确定", new android.content.DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						});
						builder.show();
					}
				}
			});
		
		
		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					if(flase_1||flase_2||flase_3||flase_4||flase_5){
						
						builder("提示", "是否保存", "取消", "确定");
					}else{
						finish();
					}
					
					
			


			}
		});

		distance = "200";
		distanceWheel.addChangingListener(new OnWheelChangedListener() {
			
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				flase_1 = true;
				Log.i("zhouhezz","ddddddddd");
				distance = (String) majorNumberAdapter.getItemText(newValue);
				if(distance.equals("进球")){
					distance = "0";
					pentails = "0";
					coolWheel.setViewAdapter(new ArrayWheelAdapter<String>(MajorScoreActivity.this, coolArray_));
					penaitsWheel.setViewAdapter(new ArrayWheelAdapter<String>(MajorScoreActivity.this, pentailsArray_));
					pentails = "0";
					cool = "hole";
				}else{
					coolWheel.setViewAdapter(new ArrayWheelAdapter<String>(MajorScoreActivity.this, coolArray));	
				}
			}
		});
		coolWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				flase_3 = true;
				cool = coolArray[newValue];		
				if(coolArray[newValue].equals("不可打")){
					pentails = "1";
					penaitsWheel.setViewAdapter(new ArrayWheelAdapter<String>(MajorScoreActivity.this, pentailsArray));
					
				}else{
					penaitsWheel.setViewAdapter(new ArrayWheelAdapter<String>(MajorScoreActivity.this, pentailsArray_));
					pentails = "0";
				}
			}
		});
		penaitsWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				
				pentails = pentailsArray[newValue];
			}
		});
		countWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				flase_2 = true;
				popal = countArrays[newValue];
				po = countArray[newValue];
			}
		});
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flase = true;

				if(addsRelativeLayout.getVisibility()==View.GONE||resultLinearLayout.getVisibility()==View.GONE){
					addsRelativeLayout.setVisibility(View.VISIBLE);
					resultLinearLayout.setVisibility(View.VISIBLE);		
					dataList.setVisibility(View.GONE);
				}
			}
		});
		quedingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dataList.setVisibility(View.VISIBLE);
				tianjia.setVisibility(View.VISIBLE);
				
				MajorScore  majorScore = new MajorScore();
				/*if(distance.equals("0")){
					cool = "hole";
				}*/
				if(cool.equals("fairway")||cool.equals("球道")){
					cool = "fairway";

				}else if(cool.equals("green")||cool.equals("果岭")){
					cool = "green";
				}
				else if(cool.equals("left_rough")||cool.equals("球道外左侧")){
					cool = "left_rough";
				}
				else if(cool.equals("right_rough")||cool.equals("球道外右侧")){
					cool = "right_rough";
				}
				else if(cool.equals("bunker")||cool.equals("沙坑")){
					cool = "bunker";
				}else if(cool.equals("unplayable")||cool.equals("不可打")){
					cool = "unplayable";
				}else{
				}
				
				
				
				majorScore.setCool(cool);
				majorScore.setOrder(count+"");							
				majorScore.setDistance(distance);
				majorScore.setPentails(pentails);
				majorScore.setCount(popal);
				count++;
				orderText.setText(count+"");
				String c = "hole";
				Log.i("greens", cool);
				if(cool.equals("球道")){
					c = "fairway";
				}else if(cool.equals("果岭")){
					c = "green";
				}
				else if(cool.equals("球道外左侧")){
					c = "left_rough";
				}
				else if(cool.equals("球道外右侧")){
					c = "right_rough";
				}
				else if(cool.equals("沙坑")){
					c = "bunker";
				}else if(cool.equals("不可打")){
					c = "unplayable";
				}else{
					c = "hole";
				}
				
				/*MajorScoreJiQiu m = new MajorScoreJiQiu();
				m.setDistance_from_hole(distance);
				m.setPoint_of_fall(c);
				m.setPenalties(pentails);
				m.setClub(popal);
				addArrayList.add(m);*/
				if(flase){	
					flase_5 = true;
					majorArray.add(majorScore);					
					adapter =	new MajorScoresAdapter(MajorScoreActivity.this, majorArray);
					dataList.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					//new JiqiuTask().start();
				}else{
					majorArray.set(biaoshi, majorScore);
					//new JiqiuBianjiTask().start();
					adapter =	new MajorScoresAdapter(MajorScoreActivity.this, majorArray);
					dataList.setAdapter(adapter);
					adapter.notifyDataSetChanged();

				}
				addsRelativeLayout.setVisibility(View.GONE);
				resultLinearLayout.setVisibility(View.GONE);
				setListViewHeightBasedOnChildren(dataList);
				dataList.setAdapter(adapter);

			}
		});


	}
	ListViewSwipeGesture.TouchCallbacks swipeListener = new TouchCallbacks() {

		@Override
		public void onDismiss(ListView listView, int[] reverseSortedPositions) {

		}

		@Override
		public void OnClickListView(int position) {
			
			
			
			if(majorArray.get(position).getCount().equals("1w")){
				countWheel.setCurrentItem(0);
			}else if(majorArray.get(position).getCount().equals("pt")){
				countWheel.setCurrentItem(1);
			}else if(majorArray.get(position).getCount().equals("3w")){
				countWheel.setCurrentItem(2);
			}else if(majorArray.get(position).getCount().equals("5w")){
				countWheel.setCurrentItem(3);
			}else if(majorArray.get(position).getCount().equals("7w")){
				countWheel.setCurrentItem(4);
			}else if(majorArray.get(position).getCount().equals("2h")){
				countWheel.setCurrentItem(5);
			}else if(majorArray.get(position).getCount().equals("3h")){
				countWheel.setCurrentItem(6);
			}else if(majorArray.get(position).getCount().equals("4h")){
				countWheel.setCurrentItem(7);
			}else if(majorArray.get(position).getCount().equals("5h")){
				countWheel.setCurrentItem(8);
			}else if(majorArray.get(position).getCount().equals("1i")){
				countWheel.setCurrentItem(9);
			}else if(majorArray.get(position).getCount().equals("2i")){
				countWheel.setCurrentItem(10);
			}else if(majorArray.get(position).getCount().equals("3i")){
				countWheel.setCurrentItem(11);
			}else if(majorArray.get(position).getCount().equals("4i")){
				countWheel.setCurrentItem(12);
			}else if(majorArray.get(position).getCount().equals("5i")){
				countWheel.setCurrentItem(13);
			}else if(majorArray.get(position).getCount().equals("6i")){
				countWheel.setCurrentItem(14);
			}else if(majorArray.get(position).getCount().equals("7i")){
				countWheel.setCurrentItem(15);
			}else if(majorArray.get(position).getCount().equals("8i")){
				countWheel.setCurrentItem(16);
			}else if(majorArray.get(position).getCount().equals("9i")){
				countWheel.setCurrentItem(17);
			}else if(majorArray.get(position).getCount().equals("pw")){
				countWheel.setCurrentItem(18);
			}else if(majorArray.get(position).getCount().equals("gw")){
				countWheel.setCurrentItem(19);
			}else if(majorArray.get(position).getCount().equals("sw")){
				countWheel.setCurrentItem(20);
			}else if(majorArray.get(position).getCount().equals("lw")){
				countWheel.setCurrentItem(21);
			}
			
			if(majorArray.get(position).getCool().equals("fairway")||majorArray.get(position).getCool().equals("球道")){
				coolWheel.setCurrentItem(2);

			}else if(majorArray.get(position).getCool().equals("green")||majorArray.get(position).getCool().equals("果岭")){
				coolWheel.setCurrentItem(0);
			}
			else if(majorArray.get(position).getCool().equals("left_rough")||majorArray.get(position).getCool().equals("球道外左侧")){
				coolWheel.setCurrentItem(1);
			}
			else if(majorArray.get(position).getCool().equals("right_rough")||majorArray.get(position).getCool().equals("球道外右侧")){
				coolWheel.setCurrentItem(3);
			}
			else if(majorArray.get(position).getCool().equals("bunker")||majorArray.get(position).getCool().equals("沙坑")){
				coolWheel.setCurrentItem(4);
			}else if(majorArray.get(position).getCool().equals("unplayable")||majorArray.get(position).getCool().equals("不可打")){
				coolWheel.setCurrentItem(5);
			}
			
			if(majorArray.get(position).getPentails().equals("1")){
				penaitsWheel.setCurrentItem(0);
			}else if(majorArray.get(position).getPentails().equals("2")){
				penaitsWheel.setCurrentItem(2);
			}else if(majorArray.get(position).getPentails().equals("3")){
				penaitsWheel.setCurrentItem(3);
			}
			int p = Integer.parseInt(majorArray.get(position).getDistance());
			if(p<=50){
				distanceWheel.setCurrentItem(p);
			}else{
				distanceWheel.setCurrentItem(((p-50)/5)+50);
			}
			
			biaoshi = position;
			flase = false;
			tianjia.setVisibility(View.GONE);
			addsRelativeLayout.setVisibility(View.VISIBLE);
			resultLinearLayout.setVisibility(View.VISIBLE);				
			dataList.setVisibility(View.GONE);
			id = majorArray.get(position).getUuid();
		}

		@Override
		public void LoadDataForScroll(int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void HalfSwipeListView(int position) {
			Log.i("positionsss", position+"");
			majorArray.remove(position);
			//addArrayList.remove(position);	
			flase_4 = true;
			count--;
			orderText.setText(count+"");
			adapter =	new MajorScoresAdapter(MajorScoreActivity.this, majorArray);
			dataList.setAdapter(adapter);
			setListViewHeightBasedOnChildren_t(dataList);
			if(position!=0){
				
			}
			
		}

		@Override
		public void FullSwipeListView(int position) {
			// TODO Auto-generated method stub

		}
	};
	private void getData() {
		majorNumberAdapter = new MajorArrayNumberWheelAdapter(this);
		distanceWheel.setViewAdapter(majorNumberAdapter);
		coolWheel.setViewAdapter(new ArrayWheelAdapter<String>(MajorScoreActivity.this, coolArray));
		penaitsWheel.setViewAdapter(new ArrayWheelAdapter<String>(MajorScoreActivity.this, pentailsArray_));
		countWheel.setViewAdapter(new ArrayWheelAdapter<String>(this, countArray));
		Log.i("zhouhessss",majorNumberAdapter.toString());
		distanceWheel.setCurrentItem(80);
		coolWheel.setCurrentItem(2);
		Intent intent = getIntent();
		position = intent.getStringExtra("position");

		ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
				dataList, swipeListener, MajorScoreActivity.this);
		touchListener.SwipeType	=	ListViewSwipeGesture.Double;    //设置两个选项列表项的背景
		dataList.setOnTouchListener(touchListener);
		
	}

	private void initView() {
		titleNameText = (TextView) findViewById(R.id.major_title_name);
		parText = (TextView) findViewById(R.id.major_par);
		distanceText = (TextView) findViewById(R.id.major_distance);
		teeColorText = (TextView) findViewById(R.id.major_t);
	
		dataList = (ListView) findViewById(R.id.major_count);
		distanceWheel = (WheelView) findViewById(R.id.major_wheel_distance);
		coolWheel = (WheelView) findViewById(R.id.major_wheel_cool);
		penaitsWheel = (WheelView) findViewById(R.id.major_wheel_pentail);
		countWheel = (WheelView) findViewById(R.id.major_wheel_pole);
		addButton = (Button) findViewById(R.id.major_add);
		orderText = (TextView) findViewById(R.id.major_add_text);
		addsRelativeLayout = (RelativeLayout) findViewById(R.id.major_add_count);
		resultLinearLayout = (LinearLayout) findViewById(R.id.major_result);
		quedingButton = (Button) findViewById(R.id.major_queding);
		fanhui = (Button) findViewById(R.id.scorecard_back);
		major_queding = (Button) findViewById(R.id.major_queding_1);
		fanhuiText = (Button) findViewById(R.id.major_back);
		tianjia = (RelativeLayout) findViewById(R.id.major_rea_add);
		
		Intent intent = getIntent();
		titleNameText.setText(intent.getStringExtra("number")+"球洞");
		parText.setText(intent.getStringExtra("par")+"标准杆");
		distanceText.setText(intent.getStringExtra("diatance")+"码");
		String color;
		if(intent.getStringExtra("color").equals("white")){
			color = "白T";
		}else if(intent.getStringExtra("color").equals("black")){
			color = "黑T";
		}else if(intent.getStringExtra("color").equals("yellow")){
			color = "黄T";
		}else if(intent.getStringExtra("color").equals("red")){
			color = "红T";
		}else{
			color = "蓝T";
		}
		teeColorText.setText(color);
		distanceWheel.setCurrentItem(50);
		coolWheel.setCurrentItem(2);
	}


	//定义函数动态控制listView的高度
	public void setListViewHeightBasedOnChildren(ListView listView) {


		//获取listview的适配器
		ListAdapter listAdapter = listView.getAdapter();
		//item的高度
		int itemHeight = 50;


		if (listAdapter == null) {
			return;
		}


		int totalHeight = 0;


		for (int i = 0; i < listAdapter.getCount(); i++) {
			totalHeight += Dp2Px(getApplicationContext(),itemHeight)+listView.getDividerHeight();
		}


		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;


		listView.setLayoutParams(params);
	}
	//定义函数动态控制listView的高度
	public void setListViewHeightBasedOnChildren_t(ListView listView) {


		//获取listview的适配器
		ListAdapter listAdapter = listView.getAdapter();
		//item的高度
		int itemHeight = 50;


		if (listAdapter == null) {
			return;
		}


		int totalHeight = 0;


		for (int i = 0; i < listAdapter.getCount(); i++) {
			totalHeight += Dp2Px(getApplicationContext(),itemHeight)+listView.getDividerHeight();

		}


		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;


		listView.setLayoutParams(params);
	}
	public int Dp2Px(Context context, float dp) { 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int) (dp * scale + 0.5f); 
	}
	
	
	class JiQiuJiLuTask extends Thread {
		@Override
		public void run() {
			super.run();
			getdata();
		}
		public void getdata(){
			SharedPreferences sp = getSharedPreferences("register", Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			Intent intent = getIntent();
			String uuid = intent.getStringExtra("uuid");


			String path = APIService.JILU+"token="+token+"&scorecard_uuid="+uuid;
			try {
				String jsonData = HttpUtils.HttpClientGet(path);
				Log.i("jsondata", jsonData);
				JSONArray jsonArray = new JSONArray(jsonData);
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					MajorScore score = new MajorScore();
					idArrayList.add(jsonObject.getString("uuid"));
					score.setUuid(jsonObject.getString("uuid"));
					score.setOrder(jsonObject.getString("sequence"));
					score.setDistance(jsonObject.getString("distance_from_hole"));
					score.setCool(jsonObject.getString("point_of_fall"));
					score.setCount(jsonObject.getString("club"));
					score.setPentails(jsonObject.getString("penalties"));
					
					MajorScoreJiQiu m = new MajorScoreJiQiu();
					m.setClub(jsonObject.getString("club"));
					if(jsonObject.getString("distance_from_hole").equals("进球")){
						m.setPoint_of_fall("hole");
					}else{
						m.setPoint_of_fall(jsonObject.getString("point_of_fall"));
					}
					m.setDistance_from_hole(jsonObject.getString("distance_from_hole"));
					m.setPenalties(jsonObject.getString("penalties"));
					
					//addArrayList.add(m);
					majorArray.add(score);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			Message msg = handler.obtainMessage();
			msg.what=3;
			handler.sendMessage(msg);
		}
	}
	/**
	 * 击球编辑
	 * @author Administrator
	 *
	 */
	class JiqiuBianjiTask extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			getdata();
		}
		public void getdata(){
			SharedPreferences sp = getSharedPreferences("register", Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			HashMap<String,String[]> map = new HashMap<String, String[]>(); 
			String[] bianji = new String[adapter.list().size()];
			for(int i=0;i<bianji.length;i++){
				bianji[i] = adapter.list().get(i).toString();
				Log.i("majorscore", bianji[i]);
			}
			map.put("strokes[]", bianji);
			Intent intent = getIntent();
			String uuid = intent.getStringExtra("uuid");
			String path = APIService.SHIYAN+"token="+token+"&uuid="+uuid;	
			String jsonArray =	HttpUtils.HttpClientPost(path, map);
			Log.i("majorscore", map.get("strokes[]").toString()+"zhou");
			try {
				JSONObject jsObject = new JSONObject(jsonArray);	
				Log.i("jiqiujilu", jsObject.toString());
				result = jsObject.getString("result");			
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 4;
			msg.obj = jsonArray;		
			handler.sendMessage(msg);

		}
	}
	
	/**
	 * 击球编辑
	 * @author Administrator
	 *
	 */
	class JiqiuBianjiTask_ extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			getdata();
		}
		public void getdata(){
			
			try {
				JSONObject jsObject = new JSONObject(maps);	
				    message = jsObject.getString("message");			
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 5;
			handler.sendMessage(msg);

		}
	}
	
	/*
	 * 弹出框
	 */
	public void builder(String title,String message,String negativie,String positive){
		AlertDialog.Builder builder = new Builder(MajorScoreActivity.this)
		.setTitle(title).setMessage(message).setNegativeButton(negativie, new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		}).setPositiveButton(positive, new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(majorArray.size()>0){
					new JiqiuBianjiTask().start();
					}else{
						AlertDialog.Builder builders = new Builder(MajorScoreActivity.this)
						.setTitle("提示").setMessage("请添加成绩").setNegativeButton("确定", new android.content.DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						});
						builders.show();
					}
				
			}
		});
		builder.show();
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
