package cn.com.zcty.ILovegolf.activity.view.count;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.StatisticAdapter;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class AnalyzeResultActivity extends Activity{
	private ScrollView s1;
	private TextView t1;
	private Button backButton;
	private Button pkButton;
	private ListView listView1;
	private ListView listView2;
	private ListView listView3;
	private TextView latelyTextView;
	private TextView fullTextView;
	private TextView averageTextView;
	private TextView chadianTextView;
	private String score_par_3;//3杆洞平均得分
	private String score_par_4;
	private String score_par_5;
	private String score;//平均得分
	private String putts;//推杆数
	private String gir;//攻果岭
	private String putts_per_gir;//标准杆上果岭的平均推数
	private String average_drive_length;//开球距离
	private String double_eagle;//信天翁球
	private String eagle;//老鹰球
	private String birdie;//小鸟球
	private String par;//标准杆
	private String bogey;//柏忌球
	private String double_bogey;//双柏忌球
	private String handicap;//差点
	private String finished_count;//完整场次
	private String advantage_transformation;//优势转换率
	private String bounce;//反弹率
	private String drive_fairways_hit;//开球命中率
	private String	matches_count;
	
	private String fs;
	private String date_begins;
	private String date_ends;
	private ArrayList<String> arrayList1 = new ArrayList<String>(); 
	private ArrayList<String> name1ArrayList = new ArrayList<String>();
	private ArrayList<String> arrayList2 = new ArrayList<String>(); 
	private ArrayList<String> name2ArrayList = new ArrayList<String>();
	private ArrayList<String> arrayList3 = new ArrayList<String>(); 
	private ArrayList<String> name3ArrayList = new ArrayList<String>();
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
		setContentView(R.layout.activity_analyze_result);
		initView();
		setListeners();
		new Result().start();
	}

	private void setListeners() {
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initView() {
		listView1 = (ListView) findViewById(R.id.listView1);
		listView2 = (ListView) findViewById(R.id.listView2);
		listView3 = (ListView) findViewById(R.id.listView3);

		latelyTextView = (TextView) findViewById(R.id.analyze_result_lately);
		fullTextView = (TextView) findViewById(R.id.analyze_result_full);
		averageTextView = (TextView) findViewById(R.id.analyze_result_avager);
		chadianTextView = (TextView) findViewById(R.id.analyze_result_chadian);

		backButton = (Button) findViewById(R.id.analyze_result_back);
		pkButton = (Button) findViewById(R.id.pk);
		
		s1 = (ScrollView) findViewById(R.id.s1);
		t1 = (TextView) findViewById(R.id.tishi);
	}

	public void getData(){
		if(fs.equals("1")){
			if(matches_count.equals("all")){
				latelyTextView.setText("最近所有场次");	
			}else{
				latelyTextView.setText("最近"+matches_count+"场");
			}
		}else if(fs.equals("2")){
			latelyTextView.setText(date_begins+"至"+date_ends);	
		}else{
			latelyTextView.setText("");
		}
		
		fullTextView.setText("完整场次"+finished_count+"场");
		averageTextView.setText(score);
		chadianTextView.setText(handicap);
		listView1.setAdapter(new StatisticAdapter(this,arrayList1,name1ArrayList));
		listView2.setAdapter(new StatisticAdapter(this,arrayList2,name2ArrayList));
		listView3.setAdapter(new StatisticAdapter(this,arrayList3,name3ArrayList));
		int count = Integer.parseInt(finished_count);
		Log.i("ssdzd", finished_count);
		if(count<1){
			s1.setVisibility(View.GONE);
			t1.setVisibility(View.VISIBLE);
		}else{
			s1.setVisibility(View.VISIBLE);
			t1.setVisibility(View.GONE);
		}
		pkButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AnalyzeResultActivity.this,AnalyzePKActivity.class);
				startActivity(intent);
			}
		});
	}
	class Result extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp = getSharedPreferences("register", Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			Intent intent = getIntent();
			fs = intent.getStringExtra("fs");
			String path = null;
			
			try {
				if(fs.equals("1")){
					matches_count = intent.getStringExtra("value");
					path = APIService.GEXINGCOUNT+"token="+token+"&matches_count="+matches_count;
				}else if(fs.equals("2")){
					date_begins  = intent.getStringExtra("start");
					date_ends  = intent.getStringExtra("end");
					SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
					Date start = s.parse(date_begins);
					Date end = s.parse(date_ends);
					String date_begin = start.getTime()/1000+"";
					String date_end = end.getTime()/1000+"";
					path = APIService.GEXINGCOUNT+"token="+token+"&date_begin="+date_begin+"&date_end="+date_end;
				}else if(fs.equals("3")){
					String venue_uuid = intent.getStringExtra("uuid");
					path = APIService.GEXINGCOUNT+"token="+token+"&venue_uuid="+venue_uuid;
				}
				
				Log.i("ssdzd", path);
				String jsonData = HttpUtils.HttpClientPost(path);
				
				JSONObject jsonObject = new JSONObject(jsonData);
				finished_count =  jsonObject.getString("finished_count");
				score = jsonObject.getString("score");
				handicap = jsonObject.getString("handicap");

				average_drive_length = jsonObject.getString("average_drive_length");
				arrayList1.add(average_drive_length);
				name1ArrayList.add("开球距离");
				drive_fairways_hit = jsonObject.getString("drive_fairways_hit");
				arrayList1.add(drive_fairways_hit);
				name1ArrayList.add("开球命中率");
				gir = jsonObject.getString("gir");
				arrayList1.add(gir);
				name1ArrayList.add("攻果岭率");
				bounce = jsonObject.getString("bounce");
				arrayList1.add(bounce);
				name1ArrayList.add("反弹率");
				advantage_transformation = jsonObject.getString("advantage_transformation");
				arrayList1.add(advantage_transformation);
				name1ArrayList.add("优势转化率");
				putts_per_gir = jsonObject.getString("putts_per_gir");
				arrayList1.add(putts_per_gir);
				name1ArrayList.add("标准杆上果岭的平均推杆数");
				putts = jsonObject.getString("putts");
				arrayList1.add(putts);
				name1ArrayList.add("没洞平均推杆数");

				score_par_3 = jsonObject.getString("score_par_3");
				arrayList2.add(score_par_3);
				name2ArrayList.add("3杆洞");
				score_par_4 = jsonObject.getString("score_par_4");
				arrayList2.add(score_par_4);
				name2ArrayList.add("4杆洞");
				score_par_5 = jsonObject.getString("score_par_5");
				arrayList2.add(score_par_5);
				name2ArrayList.add("5杆洞");

				double_eagle = jsonObject.getString("double_eagle");	
				arrayList3.add(double_eagle);
				name3ArrayList.add("信天翁球");
				eagle = jsonObject.getString("eagle");
				arrayList3.add(eagle);
				name3ArrayList.add("老鹰球");
				birdie = jsonObject.getString("birdie");
				arrayList3.add(birdie);
				name3ArrayList.add("小鸟球");
				par = jsonObject.getString("par");
				arrayList3.add(par);
				name3ArrayList.add("标准杆数");
				bogey = jsonObject.getString("bogey");			
				arrayList3.add(bogey);
				name3ArrayList.add("柏忌数");
				double_bogey = jsonObject.getString("double_bogey");
				arrayList3.add(double_bogey);
				name3ArrayList.add("双柏忌数+");

				Message msg = handler.obtainMessage();
				msg.what=1;
				handler.sendMessage(msg);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
