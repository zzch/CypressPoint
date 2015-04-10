package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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
import cn.com.zcty.ILovegolf.model.MajorScore;
import cn.com.zcty.ILovegolf.tools.ListViewSwipeGesture;
import cn.com.zcty.ILovegolf.tools.ListViewSwipeGesture.TouchCallbacks;
import cn.com.zcty.ILovegolf.tools.OnWheelChangedListener;
import cn.com.zcty.ILovegolf.tools.WheelView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class MajorScoreActivity extends Activity {
	private TextView titleNameText;
	private TextView parText;
	private TextView distanceText;
	private TextView teeColorText;
	private TextView scoreText;
	private TextView scorePentaisText;
	private Button fanhuiText;
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
	private TextView orderText;
	private String[] coolArray = {"果岭","球道外左侧","球道","球道外右侧","沙坑","不可打"};;
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
	private String cool = "果岭";
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
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				SharedPreferences sp = MajorScoreActivity.this.getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putString("score", score);
				editor.putString("penalties", penalties);
				editor.commit();
				scoreText.setText(score);
				scorePentaisText.setText(penalties);
				count++;
				orderText.setText(count+1+"");
			}
			if(msg.what==2){
				SharedPreferences sp = MajorScoreActivity.this.getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putString("score", score);
				editor.putString("penalties", penalties);
				editor.commit();
				Toast.makeText(MajorScoreActivity.this, "删除成功", Toast.LENGTH_LONG).show();
				scoreText.setText(score);
				scorePentaisText.setText(penalties);
				orderText.setText(count+"");
			}
			if(msg.what==3){
				if(majorArray.size()>0){	
					count = Integer.parseInt(majorArray.get(majorArray.size()-1).getOrder());
				}else{
					count = 0;
					
				}
				SharedPreferences sp = MajorScoreActivity.this.getPreferences(MODE_PRIVATE);
				score = sp.getString("score", "score");
				penalties = sp.getString("penalties", "penalties");
				scoreText.setText(score);
				scorePentaisText.setText(penalties);
				orderText.setText(count+1+"");
				adapter =	new MajorScoresAdapter(MajorScoreActivity.this, majorArray);
				dataList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				setListViewHeightBasedOnChildren(dataList);
			}
			if(msg.what==4){
				SharedPreferences sp = MajorScoreActivity.this.getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putString("score", score);
				editor.putString("penalties", penalties);
				editor.commit();
				scoreText.setText(score);
				scorePentaisText.setText(penalties);
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
		new JiQiuJiLuTask().start();
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
		
		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(score!=null){
					Intent intent = new Intent();
					intent.putExtra("score", score);
					intent.putExtra("putts", putts);
					intent.putExtra("position", position);
					intent.putExtra("penalties", penalties);
					setResult(1,intent);
					finish();
				}else{
					
					Intent intent = new Intent();		
					setResult(30,intent);
					finish();
				}
				
			}
		});
		coolWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				cool = coolArray[newValue];
				if(coolArray[newValue].equals("不可打")){
					pentails = "1";
					penaitsWheel.setViewAdapter(new ArrayWheelAdapter<String>(MajorScoreActivity.this, pentailsArray));
					penaitsWheel.addChangingListener(new OnWheelChangedListener() {

						@Override
						public void onChanged(WheelView wheel, int oldValue, int newValue) {
							pentails = pentailsArray[newValue];
						}
					});
				}else{
					penaitsWheel.setViewAdapter(new ArrayWheelAdapter<String>(MajorScoreActivity.this, pentailsArray_));
					pentails = "0";
				}
			}
		});
		distanceWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				distance = (String) majorNumberAdapter.getItemText(newValue);
			}
		});
		countWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
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
				majorScore.setCool(cool);
				majorScore.setOrder(count+"");							
				majorScore.setDistance(distance);
				majorScore.setPentails(pentails);
				majorScore.setCount(popal);
				Log.i("guofen", po+"aaa");
				if(flase){	
					majorArray.add(majorScore);					
					adapter =	new MajorScoresAdapter(MajorScoreActivity.this, majorArray);
					dataList.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					new JiqiuTask().start();
				}else{
					majorArray.set(biaoshi, majorScore);
					new JiqiuBianjiTask().start();
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
			Log.i("zhouhehehe", position+"");
			String uid;
			shanchuid = position;
			uid = idArrayList.get(shanchuid);
			majorArray.remove(position);
		//	count--;
			new DeleTask(uid).start();
			orderText.setText(count+"");
			//new JiqiuTask().start();
			adapter.notifyDataSetChanged();
			setListViewHeightBasedOnChildren_t(dataList);
			
		}

		@Override
		public void FullSwipeListView(int position) {
			// TODO Auto-generated method stub

		}
	};
	private void getData() {
		majorNumberAdapter = new MajorArrayNumberWheelAdapter(this);
		distanceWheel.setViewAdapter(majorNumberAdapter);
		coolWheel.setViewAdapter(new ArrayWheelAdapter<String>(this, coolArray));		
		countWheel.setViewAdapter(new ArrayWheelAdapter<String>(this, countArray));
		
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
		scoreText = (TextView) findViewById(R.id.major_score);
		scorePentaisText = (TextView) findViewById(R.id.major_score_penaits);
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
	class JiqiuTask extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			getdata();
		}
		public void getdata(){
			SharedPreferences sp = getSharedPreferences("register", Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			Intent intent = getIntent();
			String uuid = intent.getStringExtra("uuid");
			String c;
			if(majorArray.get(majorArray.size()-1).getCool().equals("球道")){
				c = "fairway";
			}else if(majorArray.get(majorArray.size()-1).getCool().equals("果岭")){
				c = "green";
			}
			else if(majorArray.get(majorArray.size()-1).getCool().equals("球道外左侧")){
				c = "left_rough";
			}
			else if(majorArray.get(majorArray.size()-1).getCool().equals("球道外右侧")){
				c = "right_rough";
			}
			else if(majorArray.get(majorArray.size()-1).getCool().equals("沙坑")){
				c = "bunker";
			}else{
				c = "unplayable";
			}
		
			String path = APIService.JILU+"token="+token+"&scorecard_uuid="+uuid
					+"&distance_from_hole="+majorArray.get(majorArray.size()-1).getDistance()+"&point_of_fall="+c+
					"&penalties="+majorArray.get(majorArray.size()-1).getPentails()+"&club="+popal;
			try {
				String jsonArray = HttpUtils.HttpClientPost(path);
				Log.i("dashujuzhouhe", jsonArray);
				JSONObject jsObject = new JSONObject(jsonArray);
				String stroke =	jsObject.getString("stroke");
				JSONObject jsObjectuid = new JSONObject(stroke);
				String uid = jsObjectuid.getString("uuid");
				Log.i("woshitiancai", uid);
				String scorecard =	jsObject.getString("scorecard");
				JSONObject jsObjectscorecard = new JSONObject(scorecard);
				score = jsObjectscorecard.getString("score");
				Log.i("woshitiancai", score);
				putts = jsObjectscorecard.getString("putts");
				penalties = jsObjectscorecard.getString("penalties");
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 1;
			handler.sendMessage(msg);
			
		}
	}
	class DeleTask extends Thread {
		private String	duid;
		public DeleTask(String duid) {
			DeleTask.this.duid = duid;
		}
		@Override
		public void run() {
			super.run();
			getdata();
		}
		public void getdata(){
			SharedPreferences sp = getSharedPreferences("register", Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");

			String path = APIService.JILU+"token="+token+"&uuid="+duid;
			String jsonData = HttpUtils.HttpClientDelete(path);
			JSONObject jsObject;
			try {
				jsObject = new JSONObject(jsonData);
				score = jsObject.getString("score");	
				putts = jsObject.getString("putts");
				penalties = jsObject.getString("penalties");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
			jsonData = "chenggong";
			Message msg = handler.obtainMessage();
			msg.what=2;
			handler.sendMessage(msg);
		}
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
					Log.i("zhouhel", jsonObject.getString("point_of_fall"));
					score.setCount(jsonObject.getString("club"));
					score.setPentails(jsonObject.getString("penalties"));
					
	
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
			
			String c;
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
			}else{
				c = "unplayable";
			}
		
			String path = APIService.JILU+"token="+token+"&uuid="+id
					+"&distance_from_hole="+distance+"&point_of_fall="+c+
					"&penalties="+pentails+"&club="+popal;
			try {
				String jsonArray = HttpUtils.HttpClientPut(path);
				JSONObject jsObject = new JSONObject(jsonArray);	
				score = jsObject.getString("score");	
				putts = jsObject.getString("putts");
				penalties = jsObject.getString("penalties");
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 4;
			handler.sendMessage(msg);
			
		}
	}
}
