package cn.com.zcty.ILovegolf.activity.view;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.CountCoolAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.QiuDongAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.QiuDongTypeAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.StatisticAdapter;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
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
	private ArrayList<String> qiuDong = new ArrayList<String>();
	private ArrayList<String> qiuDongResult = new ArrayList<String>();
	private ArrayList<String> qiuType = new ArrayList<String>();
	private ArrayList<String> qiuTypeResult = new ArrayList<String>();
	private ListView qiudongListView;
	private ListView qiudongTypeListView;
	private ListView countListView;
	private String match_uuid;
	private String name;
	private LinearLayout linear;
	private ProgressDialog progressDialog;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				hideProgressDialog();
				if(msg.obj.equals("404")||msg.obj.equals("500")){//判断是服务端问题
					Toast.makeText(StatisticsActivityLand.this, "网络异常，错误提示"+msg.obj, Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("401")){
					FileUtil.delFile();
					Intent intent = new Intent(StatisticsActivityLand.this,ShouYeActivity.class);
					startActivity(intent);
					Toast.makeText(StatisticsActivityLand.this, "帐号异地登录，请重新登录", Toast.LENGTH_LONG).show();
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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_statistics_land);
		initView();
		setListener();
		linear.setVisibility(View.INVISIBLE);
		showProgressDialog("提示", "正在加载", this);
		new MyTask().start();
	}
	private void initView() {
		match_uuid = getIntent().getStringExtra("uuid");
		
		linear = (LinearLayout) findViewById(R.id.linear);
		backButton = (Button) findViewById(R.id.back1);	
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
	private void getData() {
		StatisticAdapter sadapter = new StatisticAdapter(this, scoresArrayList,scoregrid);
		gridView.setAdapter(sadapter);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String date = simpleDateFormat.format(new Date());
		dateText.setText(date);
		countListView.setAdapter(new CountCoolAdapter(this, countCool, countCoolResult));
		qiudongListView.setAdapter(new QiuDongAdapter(this, qiuDong, qiuDongResult));
		qiudongTypeListView.setAdapter(new QiuDongTypeAdapter(this, qiuType, qiuTypeResult));
		
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
		
		if(panDuan(ttv1)){
			getBack(tv1, ttv1);
		}else{
			
		}
		if(panDuan(ttv2)){
			getBack(tv2, ttv2);
		}else{
			
			}
		if(panDuan(ttv3)){
			getBack(tv3, ttv3);
		}else{
			
			}
		if(panDuan(ttv4)){
			getBack(tv4, ttv4);
		}else{
			
			}
		if(panDuan(ttv5)){
			getBack(tv5, ttv5);
		}else{
			
			}
		if(panDuan(ttv6)){
			getBack(tv6, ttv6);
		}else{
			
			}
		if(panDuan(ttv7)){
			getBack(tv7, ttv7);
		}else{
			
			}
		if(panDuan(ttv8)){
			getBack(tv8, ttv8);
		}else{
			
			}
		if(panDuan(ttv9)){
			getBack(tv9, ttv9);
		}else{
			
			}
		panDuan(ttv10);
		panDuan(tttv1);
		panDuan(tttv2);
		panDuan(tttv3);
		panDuan(tttv4);
		panDuan(tttv5);
		panDuan(tttv6);
		panDuan(tttv7);
		panDuan(tttv8);
		panDuan(tttv9);
		panDuan(tttv10);
		
		if(panDuan(tthv1)){
			getBack(thv1, tthv1);
		}else{
			
		}
		if(panDuan(tthv2)){
			getBack(thv2, tthv2);
		}else{
			
			}
		if(panDuan(tthv3)){
			getBack(thv3, tthv3);
		}else{
			
			}
		if(panDuan(tthv4)){
			getBack(thv4, tthv4);
		}else{
			
			}
		if(panDuan(tthv5)){
			getBack(thv5, tthv5);
		}else{
			
			}
		if(panDuan(tthv6)){
			getBack(thv6, tthv6);
		}else{
			
			}
		if(panDuan(tthv7)){
			getBack(thv7, tthv7);
		}else{
			
			}
		if(panDuan(tthv8)){
			getBack(thv8, tthv8);
		}else{
			
			}
		if(panDuan(tthv9)){
			getBack(thv9, tthv9);
		}else{
			
			}
		panDuan(tthv10);
		panDuan(ttthv1);
		panDuan(ttthv2);
		panDuan(ttthv3);
		panDuan(ttthv4);
		panDuan(ttthv5);
		panDuan(ttthv6);
		panDuan(ttthv7);
		panDuan(ttthv8);
		panDuan(ttthv9);
		panDuan(ttthv10);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) { 
			Log.i("zhouhe", "zhouhea---land");


		} 


		else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { 

			
			Intent intent = new Intent(this,StatisticsAvtivity.class);
			intent.putExtra("uuid", match_uuid);
			intent.putExtra("name", name);
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
	public boolean panDuan(TextView text){
		if(text.getText().toString().equals("nul")){
			text.setText("");
			return false;
		}else{
			return true;
		}


	}
	public void getBack(TextView t,TextView tt){
		int key = Integer.parseInt(tt.getText().toString())-Integer.parseInt(t.getText().toString());
		switch (key) {
		case -3:
			//信天翁球
			tt.setBackgroundColor(Color.rgb(32, 66, 171));
			break;
		case -2:
			//老鹰球
			tt.setBackgroundColor(Color.rgb(92, 132, 208));
			break;
		case -1:
			//小鸟
			tt.setBackgroundColor(Color.rgb(173, 195, 243));
			break;
		case 0:
			//标准杆
			tt.setBackgroundColor(Color.rgb(212, 212, 212));
			break;
		case 1:
			//柏忌
			tt.setBackgroundColor(Color.rgb(213, 181, 58));
			break;
		case 2:
			//双柏忌
			tt.setBackgroundColor(Color.rgb(200, 141, 25));
			break;
		}
		if(key>2){
			//双柏忌
			tt.setBackgroundColor(Color.rgb(200, 141, 25));
		}
		if(key<-3){
			//信天翁球
			tt.setBackgroundColor(Color.rgb(32, 66, 171));
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

