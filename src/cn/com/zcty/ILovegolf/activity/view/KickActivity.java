package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.MajorScoreAverageAdapter;

public class KickActivity extends Activity implements OnClickListener{
	private ArrayList<String> statisticsModels = new ArrayList<String>();
	private ArrayList<String> counts = new ArrayList<String>();
	private String count;
	private String mingzhong;
	private TextView countTextView;
	private TextView mingzhongTextView;
	private TextView gree_shuju;
	
	private GridView listView;
	private TextView count1;
	private TextView count2;
	private TextView count3;
	private TextView count4;
	private TextView count5;
	private TextView count6;
	private TextView count7;
	private TextView count8;
	private TextView count9;
	private TextView count10;
	private TextView count11;
	private TextView count12;
	private TextView count13;
	private TextView count14;
	private TextView count15;
	private TextView count16;
	private TextView count17;
	private TextView count18;
	private String[] name = {"最远max","平均","max/2"};
	private Button fanhuiButton;
	private LinearLayout help_kaiqiu_1;
	
	private Button dialog_back;
	private AlertDialog dialog;
	private String title1;
	private String title2;
	private TextView textView1;
	private TextView textView2;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				getData();
			}
			
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kick);
		initView();
		setListeners();
		new Kick().start();
	}
	
	/**
     * 点击帮助图标后弹出一个Dialog
     */
    public void fristdialog(String titie){
    	AlertDialog.Builder builder = new Builder(this);
    	 View view=View.inflate(this, R.layout.register_dialog, null);
		//dialog=(AlertDialog) new Dialog(GanCountActivity.this,R.style.dialog);
		dialog_back=(Button)view.findViewById(R.id.dialog_back);
		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		textView1.setText(title1);
		textView2.setText("4/5杆洞开球后未上球道，但是该球洞标准杆上果岭。");
		dialog_back.setOnClickListener(this);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
       }
    
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}
	
	private void setListeners() {
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		help_kaiqiu_1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(KickActivity.this,HelpAverageActivity.class);
				//intent.putExtra("kaiqiu", "kaiqiu");
				//startActivity(intent);
				title1 = gree_shuju.getText().toString().trim();
				fristdialog(title1);
				
			}
		});
	}

	private void initView() {
		
		help_kaiqiu_1 = (LinearLayout) findViewById(R.id.help_kaiqiu_1);
		fanhuiButton = (Button) findViewById(R.id.scorecard_green_back);
		listView = (GridView) findViewById(R.id.major_green_qiudong);
		count1 = (TextView) findViewById(R.id.par_shuju_1);
		count2 = (TextView) findViewById(R.id.par_shuju_2);
		count3 = (TextView) findViewById(R.id.par_shuju_3);
		count4 = (TextView) findViewById(R.id.par_shuju_4);
		count5 = (TextView) findViewById(R.id.par_shuju_5);
		count6 = (TextView) findViewById(R.id.par_shuju_6);
		count7 = (TextView) findViewById(R.id.par_shuju_7);
		count8 = (TextView) findViewById(R.id.par_shuju_8);
		count9 = (TextView) findViewById(R.id.par_shuju_9);
		count10 = (TextView) findViewById(R.id.par_shuju_10);
		count11 = (TextView) findViewById(R.id.par_shuju_11);
		count12 = (TextView) findViewById(R.id.par_shuju_12);
		count13 = (TextView) findViewById(R.id.par_shuju_13);
		count14 = (TextView) findViewById(R.id.par_shuju_14);
		count15 = (TextView) findViewById(R.id.par_shuju_15);
		count16 = (TextView) findViewById(R.id.par_shuju_16);
		count17 = (TextView) findViewById(R.id.par_shuju_17);
		count18 = (TextView) findViewById(R.id.par_shuju_18);
		
		gree_shuju = (TextView) findViewById(R.id.gree_shuju);
		countTextView = (TextView) findViewById(R.id.green_shuju_scrambles);
		mingzhongTextView = (TextView) findViewById(R.id.green_shuju_percentage);
		for(int i=0;i<18;i++){
			counts.add("");
			
		}
	}

	public void getData(){
		listView.setAdapter(new MajorScoreAverageAdapter(this, statisticsModels,name));
		count1.setText(counts.get(0));
		count2.setText(counts.get(1));
		count3.setText(counts.get(2));
		count4.setText(counts.get(3));
		count5.setText(counts.get(4));
		count6.setText(counts.get(5));
		count7.setText(counts.get(6));
		count8.setText(counts.get(7));
		count9.setText(counts.get(8));
		count10.setText(counts.get(9));
		count11.setText(counts.get(10));
		count12.setText(counts.get(11));
		count13.setText(counts.get(12));
		count14.setText(counts.get(13));
		count15.setText(counts.get(14));
		count16.setText(counts.get(15));
		count17.setText(counts.get(16));
		count18.setText(counts.get(17));
		countTextView.setText(count);
		mingzhongTextView.setText(mingzhong);
		bgdp(count1);
		bgdp(count2);
		bgdp(count3);
		bgdp(count4);
		bgdp(count5);
		bgdp(count6);
		bgdp(count7);
		bgdp(count8);
		bgdp(count9);
		bgdp(count10);
		bgdp(count11);
		bgdp(count12);
		bgdp(count13);
		bgdp(count14);
		bgdp(count15);
		bgdp(count16);
		bgdp(count17);
		bgdp(count18);
	}
	class Kick extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			Intent intent = getIntent();
			String JsonData = intent.getStringExtra("JsonData");
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(JsonData);
				JSONObject jsonObject08 = new JSONObject(jsonObject.getString("item_08"));
				statisticsModels.add(jsonObject08.getString("longest_drive_length"));
				statisticsModels.add(jsonObject08.getString("average_drive_length"));
				statisticsModels.add(jsonObject08.getString("longest_2_drive_length"));
				count = jsonObject08.getString("good_drives");
				mingzhong = jsonObject08.getString("good_drives_percentage");
				gree_shuju.setText("最佳球位("+count+"/18)");
				//statisticsModels.add(mingzhong);
				Log.i("count", count);
				JSONArray jsarray = jsonObject08.getJSONArray("holes_of_good_drives");
				for(int i=0;i<jsarray.length();i++){
					counts.set(i, jsarray.getString(i));
				}
				
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
	@SuppressLint("NewApi")
	public void bgdp(TextView t){
		if(t.getText().toString().equals("")){
			t.setBackground(null);
		}
	}

	
}
