package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.Setcard;
import cn.com.zcty.ILovegolf.tools.ArrayWheelAdapter;
import cn.com.zcty.ILovegolf.tools.NumericWheelAdapter;
import cn.com.zcty.ILovegolf.tools.OnWheelChangedListener;
import cn.com.zcty.ILovegolf.tools.WheelView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

/**
 * ---记分卡修改页
 * @author deii
 *
 */
public class ScoreCardUpDateActivity extends Activity{
	private Button cacelButton;
	private Button saveButton;
	private TextView ballHoleTextView;
	private TextView dataTextView;
	private TextView putTextView;
	private TextView penaltiesTextView;
	private WheelView distanceWheelView;
	private WheelView coolWheelView;
	private int count;
	private int putcount = 2;
	private int penaltiescount = 0;
	private NumericWheelAdapter adapter;
	private String driving_distance = "0";//距离
	private String direction = "pure"; //开球方向
	private String[] cool = {"命中","右侧","左侧"};
	private String result;
	private Setcard setcard = new Setcard();
	private String position;
	private TextView distance_scorecard;
	private TextView hit_scorecard;
	private ImageView scorecard_image_up;
	private LinearLayout wheelview_layout;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				final String result = (String) msg.obj;
				Log.i("result", result);
				
					AlertDialog.Builder dialog = new Builder(ScoreCardUpDateActivity.this)
					.setTitle("提示")
					.setMessage("是否保存")
					.setPositiveButton("保存", new DialogInterface.OnClickListener() {//添加确定按钮   


						@Override  
						public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件   
							if(result.equals("success")){
								Intent intent = new Intent();
								intent.putExtra("scard", setcard);
								intent.putExtra("position", position);
								setResult(0, intent);
								finish();
							}else{
								//当没有网的时候弹出框提醒
								AlertDialog.Builder dialogl = new Builder(ScoreCardUpDateActivity.this)
								.setMessage("请检查网络")
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加确定按钮   


									public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件   

										finish();

									}});		
								dialogl.show();
							}
							
						}}  

							).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加确定按钮   


								@Override  
								public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件   



								}});				
					dialog.show();
					
			}
			if(msg.what==2){
				String result = (String) msg.obj;
				if(result.equals("success")){
					Intent intent = new Intent();
					intent.putExtra("scard", setcard);
					intent.putExtra("position", position);
					setResult(0, intent);
					
					finish();
				}else{
					//当没有网的时候弹出框提醒
					AlertDialog.Builder dialog = new Builder(ScoreCardUpDateActivity.this)
					.setMessage("请检查网络")
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加确定按钮   


						public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件   

							finish();

						}});		
					dialog.show();
				}
			}
		};
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.scorecardupdat);
		initView();
		setListener();
		getData();
	}

	private void getData() {
		Intent intent = getIntent();
		ballHoleTextView.setText("球洞"+intent.getStringExtra("number"));
		String par = intent.getStringExtra("par");
		position = intent.getStringExtra("position");
		dataTextView.setText(par);
		putTextView.setText(putcount+"");
		penaltiesTextView.setText(penaltiescount+"");
		count = Integer.parseInt(par);
		adapter = 	new NumericWheelAdapter(0, 400);
		distanceWheelView.setAdapter(adapter);	
		coolWheelView.setAdapter(new ArrayWheelAdapter<String>(cool));
	}

	private void setListener() {
		cacelButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				new MyTask().start();
			
			}
		});
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new MyTaskt().start();
				
			}
		});
		OnWheelChangedListener changed =  new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				distance_scorecard.setText(newValue+"码");
				driving_distance = 	adapter.getItem(newValue);
				driving_distance = driving_distance.substring(0,driving_distance.length()-1);
			}
		};
		distanceWheelView.addChangingListener(changed);
		coolWheelView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				hit_scorecard.setText(cool[newValue]);
				switch (newValue) {
				case 0:
					direction = "pure";
					break;
				case 1:
					direction = "slice";
					break;
				case 2:
					direction = "hook";
					break;

				}
			}
		});
	}

	private void initView() {
		cacelButton = (Button) findViewById(R.id.scorecard_cancel);
		saveButton = (Button) findViewById(R.id.scorecard_save);
		ballHoleTextView = (TextView) findViewById(R.id.ball_hole);
		dataTextView = (TextView) findViewById(R.id.tv_update_one);
		putTextView = (TextView) findViewById(R.id.tv_update_two);
		penaltiesTextView = (TextView) findViewById(R.id.tv_update_three);
		distanceWheelView = (WheelView) findViewById(R.id.passw_1);
		coolWheelView = (WheelView) findViewById(R.id.passw_2);
		distance_scorecard = (TextView) findViewById(R.id.distance_scorecard);
		hit_scorecard = (TextView) findViewById(R.id.hit_scorecard);
		scorecard_image_up = (ImageView) findViewById(R.id.scorecard_image_up);
		wheelview_layout = (LinearLayout) findViewById(R.id.wheelview_layout);
		
	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.but_add_one:
			count++;
			dataTextView.setText(count+"");
			break;
		case R.id.but_jian_one:
			if(count>0){
				count--;
				dataTextView.setText(count+"");
			}
			if(penaltiescount>0){
				penaltiescount--;
				penaltiesTextView.setText(penaltiescount+"");
			}else if(putcount>0){
				putcount--;
				putTextView.setText(putcount+"");
			}
			break;
		case R.id.but_add_two:
			int c = putcount;
			putcount++;
			putTextView.setText(putcount+"");
			if(count<(c-putcount)+(penaltiescount*2)+1){
				count = (c-putcount)+(penaltiescount*2)+1;
				dataTextView.setText(count+"");
			}
			break;
		case R.id.but_jian_two:
			if(putcount>0){
				putcount--;
				putTextView.setText(putcount+"");
			}
			break;
		case R.id.but_add_three:
			int j = penaltiescount;
			penaltiescount++;
			penaltiesTextView.setText(penaltiescount+"");
			if(count<(putcount+((j-penaltiescount)*2)+1)){
				count = putcount+((j-penaltiescount)*2)+1;
				dataTextView.setText(count+"");
			}
			break;
		case R.id.but_jian_three:
			if(penaltiescount>0){
				penaltiescount--;
				penaltiesTextView.setText(penaltiescount+"");
			}
			break;
		case R.id.layout_chooise:
			wheelview_layout.setVisibility(View.VISIBLE);
			break;
		}
	}
	class MyTask extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){

			SharedPreferences sp = getSharedPreferences("register", Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			Intent intent = getIntent();
			String uuid = intent.getStringExtra("uuid");
			String score = dataTextView.getText().toString();
			String putts = putTextView.getText().toString();
			String penalties = penaltiesTextView.getText().toString();	
			setcard.setRodNum(score);
			setcard.setPutts(putts);
			setcard.setPenalties(penalties);
			setcard.setTe(driving_distance);
			if(direction.equals("hook")){
				setcard.setPar("左侧");
			}else if(direction.equals("slice")){
				setcard.setPar("右侧");
			}else{
				setcard.setPar("命中");
			}
			
			String path = APIService.MODIFYINTEGRAL+"uuid="+uuid+"&score="+score+"&putts="+putts+
					"&penalties="+penalties+
					"&driving_distance="+driving_distance+
					"&direction="+direction+
					"&token="+token;
			Log.i("pathd", path);
			String jsonData = HttpUtils.HttpClientPut(path);
			Log.i("jsondata", jsonData);
			try {
				JSONObject jsonaObject = new JSONObject(jsonData);
				result = jsonaObject.getString("result");
				Log.i("result", result+"1");
				Message msg = handler.obtainMessage();
				msg.what = 1;
				msg.obj = result;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	class MyTaskt extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){

			SharedPreferences sp = getSharedPreferences("register", Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			Intent intent = getIntent();
			String uuid = intent.getStringExtra("uuid");
			String score = dataTextView.getText().toString();
			String putts = putTextView.getText().toString();
			String penalties = penaltiesTextView.getText().toString();	
			setcard.setRodNum(score);
			setcard.setPutts(putts);
			setcard.setPenalties(penalties);
			setcard.setTe(driving_distance);
			if(direction.equals("hook")){
				setcard.setPar("左侧");
			}else if(direction.equals("slice")){
				setcard.setPar("右侧");
			}else{
				setcard.setPar("命中");
			}
			
			String path = APIService.MODIFYINTEGRAL+"uuid="+uuid+"&score="+score+"&putts="+putts+
					"&penalties="+penalties+
					"&driving_distance="+driving_distance+
					"&direction="+direction+
					"&token="+token;
			Log.i("pathd", path);
			String jsonData = HttpUtils.HttpClientPut(path);
			Log.i("jsondata", jsonData);
			try {
				JSONObject jsonaObject = new JSONObject(jsonData);
				result = jsonaObject.getString("result");
				Log.i("result", result+"1");
				Message msg = handler.obtainMessage();
				msg.what = 2;
				msg.obj = result;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}


}


