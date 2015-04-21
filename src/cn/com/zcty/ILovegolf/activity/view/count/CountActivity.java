package cn.com.zcty.ILovegolf.activity.view.count;




import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import org.json.JSONException;
import org.json.JSONObject;


import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.R.layout;
import cn.com.zcty.ILovegolf.activity.adapter.CountAdapter;
import cn.com.zcty.ILovegolf.model.ChartProp;
import cn.com.zcty.ILovegolf.tools.ChartView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class CountActivity extends Activity implements OnClickListener{
	private Button analyButton;
	private ChartView mChartView;
	private GridView gridView;
	private ImageView image;
	private TextView averageTextView;
	private TextView double_eagleTextView;
	private TextView eagleTextView;
	private TextView birdieTextView;
	private TextView parTextView;
	private TextView bogeyTextView;
	private TextView double_bogeyTextView;
	private ArrayList<String> gridArrayList = new ArrayList<String>();
	private int color[] = new int[]{Color.rgb(32, 66, 171),Color.rgb(200, 141, 25),Color.rgb(92, 132, 208),Color.rgb(230, 230, 230),Color.rgb(173, 195, 243),Color.rgb(213, 181, 58)};
	private float percent[] = new float[6];
	private String names[] = new String[]{"","","","","",""};
	private float width;
	private float double_eagle=0f;
	private float eagle=0f;
	private float birdie=0f;
	private float par=0f;
	private float bogey=0f;
	private float double_bogey=0f;
	private String total_matches_count;
	private String average="0";
	Handler handler = new Handler(){
		@SuppressLint("NewApi")
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				gridView.setAdapter(new CountAdapter(CountActivity.this, gridArrayList));
				ArrayList<ChartProp> acps = mChartView.createCharts(percent.length);
				int size = acps.size();
				for(int i = 0; i < size; i++)
				{
					ChartProp chartProp = acps.get(i);
					chartProp.setColor(color[i]);
					chartProp.setPercent(percent[i]);
					chartProp.setName(names[i]);
				}
				if(eagle==0f&&double_eagle==0f&&birdie==0f&&par==0f&&bogey==0f&&double_bogey==0f){
					image.setImageAlpha(80);
				}else{
					image.setImageAlpha(0);

				}
				averageTextView.setText(average);
				double_eagleTextView.setText(double_eagle*100+"%");
				eagleTextView.setText(eagle*100+"%");
				birdieTextView.setText(birdie*100+"%");
				parTextView.setText(par*100+"%");
				bogeyTextView.setText(bogey*100+"%");
				double_bogeyTextView.setText(double_bogey*100+"%");
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_count);
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);
		float density = displayMetrics.density; //得到密度
		width = displayMetrics.widthPixels;//得到宽度
		float height = displayMetrics.heightPixels;//得到高度
		initView();
		setListeners();
		getData();
		new Count().start();
		//mChartView.setVisibility(View.VISIBLE);
	}
	private void getData() {		
		mChartView.setAntiAlias(true);
		mChartView.setCenter(new Point(299, 240));
		mChartView.setR((int) (width/4.5));
		mChartView.setStartAngle(30);
		mChartView.setWizardLineLength(0);
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		String date = time.format(new Date());
		SharedPreferences sp = getSharedPreferences("date", MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("date", date);
		editor.putString("sgin", "0");
		editor.commit();
		
	}
	private void setListeners() {
		analyButton.setOnClickListener(this);
	}
	private void initView() {
		analyButton = (Button) findViewById(R.id.count_start);
		mChartView = (ChartView)this.findViewById(R.id.chartView);
		gridView = (GridView) findViewById(R.id.gridView1);
		image = (ImageView) findViewById(R.id.count_image);
		averageTextView = (TextView) findViewById(R.id.count_average);
		double_eagleTextView = (TextView) findViewById(R.id.double_eagle_percentage);
		birdieTextView = (TextView) findViewById(R.id.birdie);
		parTextView = (TextView) findViewById(R.id.par);
		eagleTextView = (TextView) findViewById(R.id.eagle);
		bogeyTextView = (TextView) findViewById(R.id.bogey);
		double_bogeyTextView = (TextView) findViewById(R.id.double_bogey);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.count_start:
			Intent intent = new Intent(CountActivity.this,AnalyzeActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	class Count extends Thread{
	@Override
	public void run() {
		super.run();
		getData();
	}
	public void getData(){
		SharedPreferences sp = getSharedPreferences("register", Context.MODE_PRIVATE);
		String token = sp.getString("token", "token");
		String path = APIService.COUNTS+"token="+token;
		Log.i("pathss", path);
		String JsonData = HttpUtils.HttpClientGet(path);
		try {
			JSONObject jsonObject = new JSONObject(JsonData);
			gridArrayList.add(jsonObject.getString("handicap"));
			gridArrayList.add(jsonObject.getString("rank"));
			gridArrayList.add(jsonObject.getString("best_score"));
			gridArrayList.add(jsonObject.getString("finished_matches_count")+"/"+jsonObject.getString("total_matches_count"));
			if(!jsonObject.getString("average_score").equals("null")){				
				average = jsonObject.getString("average_score");
			}
			if(!jsonObject.getString("double_eagle").equals("null")){
				double_eagle = Float.parseFloat(jsonObject.getString("double_eagle"));
			}
			if(!jsonObject.getString("eagle").equals("null")){
				eagle = Float.parseFloat(jsonObject.getString("eagle"));
			}
			if(!jsonObject.getString("birdie").equals("null")){
				birdie = Float.parseFloat(jsonObject.getString("birdie"));
			}
			if(!jsonObject.getString("par").equals("null")){
				par = Float.parseFloat(jsonObject.getString("par"));
			}
			if(!jsonObject.getString("bogey").equals("null")){
				bogey = Float.parseFloat(jsonObject.getString("bogey"));
			}
			if(!jsonObject.getString("double_bogey").equals("null")){
				double_bogey = Float.parseFloat(jsonObject.getString("double_bogey"));
			}
			percent[0] = 0.5f;
			percent[1] = 0.5f;
			percent[2] = eagle;
			percent[3] = par;
			percent[4] = birdie;
			percent[5] = bogey;
			
			Message msg = handler.obtainMessage();
			msg.what=1;
			handler.sendMessage(msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	}
}
