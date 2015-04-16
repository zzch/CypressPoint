package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.MajorStatisticsListViewAdapter;
import cn.com.zcty.ILovegolf.model.MajorStatisticsModel;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class MajorStatisticsActivity extends Activity implements OnClickListener{
	private Button  backButton;
	private ListView informationListView;
	private TextView distance1TextView;
	private TextView distance2TextView;
	private TextView distance3TextView;
	private TextView distance4TextView;
	private String distance1;
	private String distance2;
	private String distance3;
	private String distance4;
	private String JsonData;
	private MajorStatisticsListViewAdapter adapter;
	private ArrayList<MajorStatisticsModel> statisticsModels = new ArrayList<MajorStatisticsModel>();
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				getData();
				setListViewHeightBasedOnChildren(informationListView);
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_majorstatic);
		initView();
		setListeners();
		new Statistics().start();
	}

	private void initView() {
		backButton = (Button) findViewById(R.id.major_scorecard_back);
		informationListView = (ListView) findViewById(R.id.major_count);
		distance1TextView = (TextView) findViewById(R.id.static_distance_1);
		distance2TextView = (TextView) findViewById(R.id.static_distance_2);
		distance3TextView = (TextView) findViewById(R.id.static_distance_3);
		distance4TextView = (TextView) findViewById(R.id.static_distance_4);
	}
	private void setListeners(){
		backButton.setOnClickListener(this);
		informationListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				switch (position) {
				case 0:
					Intent intent = new Intent(MajorStatisticsActivity.this,MajorStatisticsActivityScord.class);
					intent.putExtra("JsonData", JsonData);
					startActivity(intent);
					break;
				case 1:
					Intent intent2 = new Intent(MajorStatisticsActivity.this,AverageAvtivity.class);
					intent2.putExtra("JsonData", JsonData);
					startActivity(intent2);
					break;
				case 2:
					Intent intent3 = new Intent(MajorStatisticsActivity.this,PuttsActivity.class);
					intent3.putExtra("JsonData", JsonData);
					startActivity(intent3);
					break;
				case 3:
					Intent intent4 = new Intent(MajorStatisticsActivity.this,SandpickActivity.class);
					intent4.putExtra("JsonData", JsonData);
					startActivity(intent4);
					break;
				case 4:
					Intent intent5 = new Intent(MajorStatisticsActivity.this,ChipActivity.class);
					intent5.putExtra("JsonData", JsonData);
					startActivity(intent5);
					break;
				case 5:
					Intent intent6 = new Intent(MajorStatisticsActivity.this,GreenActivity.class);
					intent6.putExtra("JsonData", JsonData);
					startActivity(intent6);
					break;
				case 6:
					Intent intent7 = new Intent(MajorStatisticsActivity.this,ScoreHitActivity.class);
					intent7.putExtra("JsonData", JsonData);
					startActivity(intent7);
					break;
				case 7:
					Intent intent8 = new Intent(MajorStatisticsActivity.this,KickActivity.class);
					intent8.putExtra("JsonData", JsonData);
					startActivity(intent8);
					break;
				case 8:
					Intent intent9 = new Intent(MajorStatisticsActivity.this,GanCountActivity.class);
					intent9.putExtra("JsonData", JsonData);
					startActivity(intent9);
					break;
				case 9:
					
					break;
				}
			}
		});
	}
	private void getData(){
		distance1TextView.setText(distance1);
		distance2TextView.setText(distance2);
		distance3TextView.setText(distance3);
		distance4TextView.setText(distance4);
		adapter = new MajorStatisticsListViewAdapter(this, statisticsModels);
		informationListView.setAdapter(adapter);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.major_scorecard_back:
			finish();
			break;

		}
	}
	class Statistics extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences ss = getSharedPreferences("edit",Activity.MODE_PRIVATE);
			String uuid = ss.getString("match_uuid", "match_uuid");
			SharedPreferences sp = getSharedPreferences("register", Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			String path = APIService.MAJORCOUNT+"token="+token+"&match_uuid="+uuid;			
			Log.i("pathzhou", path);

			try {
				JsonData = HttpUtils.HttpClientGet(path);
				Log.i("majordata", JsonData);
				JSONObject jsonObject = new JSONObject(JsonData);
				JSONObject jsonObjects = new JSONObject(jsonObject.getString("item_01"));
				MajorStatisticsModel statistics = new MajorStatisticsModel();
				statistics.setPlace1("总成绩");
				statistics.setPlace2(jsonObjects.getString("score"));
				statistics.setPlace3(jsonObjects.getString("putts"));
				statistics.setPlace4(jsonObjects.getString("net"));
				statistics.setPlace5("总杆");
				statistics.setPlace6("推杆");
				statistics.setPlace7("净杆");
				statisticsModels.add(statistics);
				JSONObject jsonObject02 = new JSONObject(jsonObject.getString("item_02"));
				MajorStatisticsModel statistics02 = new MajorStatisticsModel();
				statistics02.setPlace1("平均杆数");
				statistics02.setPlace2(jsonObject02.getString("average_score_par_3"));
				statistics02.setPlace3(jsonObject02.getString("average_score_par_4"));
				statistics02.setPlace4(jsonObject02.getString("average_score_par_5"));
				statistics02.setPlace5("3杆洞");
				statistics02.setPlace6("4杆洞");
				statistics02.setPlace7("5杆洞");
				statisticsModels.add(statistics02);
				JSONObject jsonObject03 = new JSONObject(jsonObject.getString("item_03"));
				MajorStatisticsModel statistics03 = new MajorStatisticsModel();
				statistics03.setPlace1("推杆");
				statistics03.setPlace2(jsonObject03.getString("average_putts"));
				statistics03.setPlace3(jsonObject03.getString("putts_per_gir"));
				statistics03.setPlace4(jsonObject03.getString("putts_per_non_gir"));
				statistics03.setPlace5("平均/洞");
				statistics03.setPlace6("标准杆");
				statistics03.setPlace7("大于标准杆");
				statisticsModels.add(statistics03);
				JSONObject jsonObject04 = new JSONObject(jsonObject.getString("item_04"));
				MajorStatisticsModel statistics04 = new MajorStatisticsModel();
				statistics04.setPlace1("沙坑");
				statistics04.setPlace2(jsonObject04.getString("sand_saves"));
				statistics04.setPlace3("");
				statistics04.setPlace4("");
				statistics04.setPlace5("沙坑救球(40)");
				statistics04.setPlace6("");
				statistics04.setPlace7("");
				statisticsModels.add(statistics04);
				JSONObject jsonObject05 = new JSONObject(jsonObject.getString("item_05"));
				MajorStatisticsModel statistics05 = new MajorStatisticsModel();
				statistics05.setPlace1("切杆");
				statistics05.setPlace2(jsonObject05.getString("up_and_downs_count"));
				statistics05.setPlace3("");
				statistics05.setPlace4(jsonObject05.getString("chip_ins"));
				statistics05.setPlace5("一切一推");
				statistics05.setPlace6("");
				statistics05.setPlace7("切杆进洞");
				statisticsModels.add(statistics05);
				JSONObject jsonObject06 = new JSONObject(jsonObject.getString("item_06"));
				MajorStatisticsModel statistics06 = new MajorStatisticsModel();
				statistics06.setPlace1("攻果岭");
				statistics06.setPlace2(jsonObject06.getString("gir_percentage"));
				statistics06.setPlace3(jsonObject06.getString("non_gir_percentage"));
				statistics06.setPlace4("");
				statistics06.setPlace5("命中");
				statistics06.setPlace6("未命中");
				statistics06.setPlace7("");
				statisticsModels.add(statistics06);
				JSONObject jsonObject07 = new JSONObject(jsonObject.getString("item_07"));
				MajorStatisticsModel statistics07 = new MajorStatisticsModel();
				statistics07.setPlace1("球道命中");
				Log.i("tiancaishiwo", jsonObject07.getString("drive_fairways_hit"));
				statistics07.setPlace2(jsonObject07.getString("drive_fairways_hit"));
				
				statistics07.setPlace3(jsonObject07.getString("drive_left_roughs_hit"));
				statistics07.setPlace4(jsonObject07.getString("drive_right_roughs_hit"));
				statistics07.setPlace5("命中");
				statistics07.setPlace6("左侧");
				statistics07.setPlace7("右侧");
				statisticsModels.add(statistics07);
				JSONObject jsonObject08 = new JSONObject(jsonObject.getString("item_08"));
				MajorStatisticsModel statistics08 = new MajorStatisticsModel();
				statistics08.setPlace1("开球");
				statistics08.setPlace2(jsonObject08.getString("longest_drive_length"));
				statistics08.setPlace3(jsonObject08.getString("average_drive_length"));
				statistics08.setPlace4(jsonObject08.getString("longest_2_drive_length"));
				statistics08.setPlace5("最远max");
				statistics08.setPlace6("平均");
				statistics08.setPlace7("max/2");
				statisticsModels.add(statistics08);
				JSONObject jsonObject09 = new JSONObject(jsonObject.getString("item_09"));
				MajorStatisticsModel statistics09 = new MajorStatisticsModel();
				statistics09.setPlace1("杆数");
				statistics09.setPlace2(jsonObject09.getString("eagle"));
				statistics09.setPlace3(jsonObject09.getString("par"));
				statistics09.setPlace4(jsonObject09.getString("bogey"));
				statistics09.setPlace5("小鸟");
				statistics09.setPlace6("标准杆");
				statistics09.setPlace7("柏忌");
				statisticsModels.add(statistics09);
				JSONObject jsonObject10 = new JSONObject(jsonObject.getString("item_10"));
				JSONObject j1 = new JSONObject(jsonObject10.getString("club_1w"));
				distance1 = j1.getString("maximum_length");
				JSONObject j2 = new JSONObject(jsonObject10.getString("club_3w"));
				distance2 = j2.getString("maximum_length");
				JSONObject j3 = new JSONObject(jsonObject10.getString("club_5w"));
				distance3 = j3.getString("maximum_length");
				JSONObject j4 = new JSONObject(jsonObject10.getString("club_7w"));
				distance4 = j4.getString("maximum_length");
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	//定义函数动态控制listView的高度
	public void setListViewHeightBasedOnChildren(ListView listView) {


		//获取listview的适配器
		ListAdapter listAdapter = listView.getAdapter();
		//item的高度
		int itemHeight = 120;


		if (listAdapter == null) {
			return;
		}


		int totalHeight = 0;


		for (int i = 0; i < listAdapter.getCount(); i++) {
			totalHeight += Dp2Px(getApplicationContext(),itemHeight)+listView.getDividerHeight();
		}


		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params. height = totalHeight;


		listView.setLayoutParams(params);
	}
	//dp转化为px
	public int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int ) (dp * scale + 0.5f);
	}

}
