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
import cn.com.zcty.ILovegolf.activity.view.HomePageActivity;
import cn.com.zcty.ILovegolf.activity.view.QuickScoreActivity;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.activity.view.myself.Myself;
import cn.com.zcty.ILovegolf.model.ChartProp;
import cn.com.zcty.ILovegolf.tools.ChartView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
	private int color[] = new int[]{Color.rgb(32, 66, 171),Color.rgb(200, 141, 25),Color.rgb(92, 132, 208),Color.rgb(212, 212, 212),Color.rgb(173, 195, 243),Color.rgb(213, 181, 58)};
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
	private ProgressDialog progressDialog;
	private LinearLayout linear;
	Handler handler = new Handler(){
		@SuppressLint("NewApi")
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				hideProgressDialog();
				if(msg.obj.equals("404")||msg.obj.equals("500")){//判断是服务端问题
					Toast.makeText(CountActivity.this, "网络异常，错误提示"+msg.obj, Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("401")){
					Toast.makeText(CountActivity.this, "帐号异地登录，请重新登录", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(CountActivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
				linear.setVisibility(View.VISIBLE);					
				gridView.setAdapter(new CountAdapter(CountActivity.this, gridArrayList));
				ArrayList<ChartProp> acps = mChartView.createCharts(percent.length);
				int size = acps.size();
				for(int i = 0; i < color.length; i++)
				{
					ChartProp chartProp = acps.get(i);
					chartProp.setColor(color[i]);
					chartProp.setPercent(percent[i]);
					chartProp.setName(names[i]);
				}
				if(eagle==0f&&double_eagle==0f&&birdie==0f&&par==0f&&bogey==0f&&double_bogey==0f){
					image.setImageAlpha(80);
				}else{
					
					image.setVisibility(View.VISIBLE);

				}
				
				averageTextView.setText(average);
				int a = (int) (double_eagle*100);
				int b = (int) (eagle*100);
				int c = (int) (birdie*100);
				int d = (int) (par*100);
				int e = (int) (bogey*100);
				int f = (int) (double_bogey*100);
				
				double_eagleTextView.setText(a+"%");
				eagleTextView.setText(b+"%");
				birdieTextView.setText(c+"%");
				parTextView.setText(d+"%");
				bogeyTextView.setText(e+"%");
				double_bogeyTextView.setText(f+"%");
			}}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_count);
		ShouYeActivity.getInstance().addActivity(this);
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);
		float density = displayMetrics.density; //得到密度
		width = displayMetrics.widthPixels;//得到宽度
		float height = displayMetrics.heightPixels;//得到高度
		initView();
		setListeners();
		getData();
		linear.setVisibility(View.INVISIBLE);
		showProgressDialog("提示", "正在加载", this);
		new Count().start();
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
		linear = (LinearLayout) findViewById(R.id.linear);
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
		Intent intent;
		switch (v.getId()) {
		case R.id.count_start:
			intent = new Intent(CountActivity.this,AnalyzeActivity.class);
			startActivity(intent);
			finish();
			break;

		case R.id.k_back:
			intent=new Intent(CountActivity.this,HomePageActivity.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			finish();
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent;
		intent=new Intent(CountActivity.this,HomePageActivity.class);
		startActivity(intent);
		finish();
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
			percent[0] = double_eagle;
			percent[1] = double_bogey;
			percent[2] = eagle;
			percent[3] = par;
			percent[4] = birdie;
			percent[5] = bogey;
			
			Message msg = handler.obtainMessage();
			msg.what=1;
			msg.obj = JsonData;
			handler.sendMessage(msg);
		} catch (JSONException e) {
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
