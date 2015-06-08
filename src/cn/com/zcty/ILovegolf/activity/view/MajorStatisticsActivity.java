package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.MajorStatisticsListViewAdapter;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.model.MajorStatisticsModel;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class MajorStatisticsActivity extends Activity implements OnClickListener{
	private Button  backButton;
	private LinearLayout linear;
	private ListView informationListView;
	private TextView distance1TextView;
	private TextView distance2TextView;
	private TextView distance3TextView;
	private TextView distance4TextView;
	private TextView name1TextView;
	private TextView name2TextView;
	private TextView name3TextView;
	private TextView name4TextView;
	private RelativeLayout r1;
	private RelativeLayout rrs;
	private RelativeLayout statistic_layout;
	private ImageView guide;
	private ArrayList<String> distance = new ArrayList<String>();
	private String JsonData;
	private ArrayList<String> name = new ArrayList<String>();
	private ProgressDialog progressDialog;

	private MajorStatisticsListViewAdapter adapter;
	private ArrayList<MajorStatisticsModel> statisticsModels = new ArrayList<MajorStatisticsModel>();
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					hideProgressDialog();
					Toast.makeText(MajorStatisticsActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(MajorStatisticsActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(MajorStatisticsActivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
				hideProgressDialog();
				linear.setVisibility(View.VISIBLE);
				getData();
				setListViewHeightBasedOnChildren(informationListView);
				if(statisticsModels.get(0).getPlace3().equals("")){
					rrs.setVisibility(View.GONE);
				}else{
					rrs.setVisibility(View.VISIBLE);
				}}
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
		linear.setVisibility(View.INVISIBLE);
		showProgressDialog("提示", "正在加载", this);
		new Statistics().start();
	}

	private void initView() {
		linear = (LinearLayout) findViewById(R.id.linear);
		rrs = (RelativeLayout) findViewById(R.id.rrs1);
		backButton = (Button) findViewById(R.id.major_scorecard_back);
		informationListView = (ListView) findViewById(R.id.major_count);
		distance1TextView = (TextView) findViewById(R.id.static_distance_1);
		distance2TextView = (TextView) findViewById(R.id.static_distance_2);
		distance3TextView = (TextView) findViewById(R.id.static_distance_3);
		distance4TextView = (TextView) findViewById(R.id.static_distance_4);
		name1TextView = (TextView) findViewById(R.id.static_name_1);
		name2TextView = (TextView) findViewById(R.id.static_name_2);
		name3TextView = (TextView) findViewById(R.id.static_name_3);
		name4TextView = (TextView) findViewById(R.id.static_name_4);
		r1 = (RelativeLayout) findViewById(R.id.major_qiugan_re);
		guide = (ImageView) findViewById(R.id.gd);
		guide.setVisibility(View.GONE);
		statistic_layout = (RelativeLayout) findViewById(R.id.statistic_layout);
		//statistic_layout.getBackground().setAlpha(80);
		for(int i=0;i<4;i++){
			distance.add("");
			name.add("");
		}
	}
	private void setListeners(){
		backButton.setOnClickListener(this);
		r1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Intent intent10 = new Intent(MajorStatisticsActivity.this,QiuGanActivity.class);
				intent10.putExtra("JsonData", JsonData);
				startActivity(intent10);
				
			}
		});
		informationListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(!statisticsModels.get(0).getPlace2().equals("null")){
			switch (position) {
				case 0:
					Intent intent = new Intent(MajorStatisticsActivity.this,MajorStatisticsActivityScord.class);
					intent.putExtra("JsonData", JsonData);
					intent.putExtra("name", getIntent().getStringExtra("name"));
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
			}
		});
	}
	private void getData(){
		if(distance.get(0).equals("null")){
			distance1TextView.setText("一");
		}else{
			distance1TextView.setText(distance.get(0));
		}
		if(distance.get(1).equals("null")){
			distance2TextView.setText("一");
		}else{
			distance2TextView.setText(distance.get(1));
		}
		if(distance.get(2).equals("null")){
			distance3TextView.setText("一");
		}else{
			distance3TextView.setText(distance.get(2));
		}
		if(distance.get(3).equals("null")){
			distance4TextView.setText("一");
		}else{
			distance4TextView.setText(distance.get(3));
		}
		name1TextView.setText(name.get(0));
		name2TextView.setText(name.get(1));
		name3TextView.setText(name.get(2));
		name4TextView.setText(name.get(3));
		adapter = new MajorStatisticsListViewAdapter(this, statisticsModels);
		informationListView.setAdapter(adapter);
		if(!statisticsModels.get(0).getPlace2().equals("null")){
			guide.setVisibility(View.VISIBLE);
			}else{
				guide.setVisibility(View.GONE);
			}
		
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
			/*SharedPreferences ss = getSharedPreferences("edit",Activity.MODE_PRIVATE);
			String uuid = ss.getString("match_uuid", "match_uuid");*/
			Intent intent = getIntent();
			String uuid = intent.getStringExtra("uuid");
			SharedPreferences sp = getSharedPreferences("register", Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			String path = APIService.MAJORCOUNT+"token="+token+"&match_uuid="+uuid;			
			Log.i("pathzhou", path);
			JsonData = HttpUtils.HttpClientGet(path);
			Log.i("majordata", JsonData);
			try {
				
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
				statistics04.setPlace1("沙坑(40码)");
				statistics04.setPlace2(jsonObject04.getString("sand_saves"));
				statistics04.setPlace3(jsonObject04.getString("bunker_shots"));
				statistics04.setPlace4(jsonObject04.getString("sand_saves_percentage"));
				statistics04.setPlace5("沙坑救球");
				statistics04.setPlace6("进入次数");
				statistics04.setPlace7("成功率");
				statisticsModels.add(statistics04);
				JSONObject jsonObject05 = new JSONObject(jsonObject.getString("item_05"));
				MajorStatisticsModel statistics05 = new MajorStatisticsModel();
				statistics05.setPlace1("切杆");
				statistics05.setPlace2(jsonObject05.getString("up_and_downs_count"));
				statistics05.setPlace3(jsonObject05.getString("shots_within_100"));
				statistics05.setPlace4(jsonObject05.getString("up_and_downs_percentage"));
				statistics05.setPlace5("一切一推");
				statistics05.setPlace6("次数");
				statistics05.setPlace7("成功率");
				statisticsModels.add(statistics05);
				JSONObject jsonObject06 = new JSONObject(jsonObject.getString("item_06"));
				MajorStatisticsModel statistics06 = new MajorStatisticsModel();
				statistics06.setPlace1("攻果岭");
				statistics06.setPlace2(jsonObject06.getString("gir_percentage"));
				statistics06.setPlace3("");
				statistics06.setPlace4(jsonObject06.getString("non_gir_percentage"));
				statistics06.setPlace5("命中");
				statistics06.setPlace6("");
				statistics06.setPlace7("未命中");
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
				JSONArray jsarray10 = jsonObject10.getJSONArray("frequently_used_clubs");
				for(int i=0;i<jsarray10.length();i++){
					JSONObject j = jsarray10.getJSONObject(i);
					distance.set(i, j.getString("maximum_length"));
					name.set(i, j.getString("name"));
				}
				Message msg = handler.obtainMessage();
				msg.what = 1;
				msg.obj = JsonData;
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
		int itemHeight = 136;


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
