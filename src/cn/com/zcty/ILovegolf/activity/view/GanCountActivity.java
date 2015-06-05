package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;

public class GanCountActivity extends Activity implements OnClickListener{
	private ArrayList<String> statisticsModels = new ArrayList<String>();
	private String double_eagle;//信天翁
	private String eagle;//老鹰球
	private String birdie;//小鸟球
	private String bogey;//柏忌
	private String double_bogey;//双柏忌
	private String par;//标准杆
	private TextView double_eagleTextView;
	private TextView eagleTextView;
	private TextView birdileTextView;
	private TextView bogeyTextView;
	private TextView double_bogeyTextView;
	private TextView parTextView;
	private TextView green_shuju_scrambles;
	private TextView green_shuju_scrambles_1;
	private TextView textView2;
	private TextView text_xiaoniao_2;
	private Button fanhuiButton;
	private String bounce;
	private Button dialog_back;
	private AlertDialog dialog;
	private TextView gree_shuju;
	private TextView gree_shuju_fantanlv;
	private String title1;
	private String title2;
	private TextView textView1;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				double_eagleTextView.setText(double_eagle);
				bogeyTextView.setText(bogey);
				double_bogeyTextView.setText(double_bogey);
				eagleTextView.setText(eagle);
				birdileTextView.setText(birdie);
				parTextView.setText(par);
				green_shuju_scrambles.setText(bounce);
				green_shuju_scrambles_1.setText(bounce);
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gancount);
		initView();
		setListeners();
		new GanCount().start();
	}
	private void setListeners() {
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});		
	}
	/**
     * 点击帮助图标后弹出一个Dialog
     */
    public void fristdialog(String titie,Boolean isa){
    	AlertDialog.Builder builder = new Builder(this);
    	 View view=View.inflate(this, R.layout.register_dialog, null);
		//dialog=(AlertDialog) new Dialog(GanCountActivity.this,R.style.dialog);
		dialog_back=(Button)view.findViewById(R.id.dialog_back);
		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		if(isa==true){
			textView1.setText(title1);
			textView2.setText("3/4/5杆洞小于等于标准杆上果岭后并且该洞成绩小于标准杆与当前所完成洞的比例。");
		}else{
			textView1.setText(title2);
			textView2.setText("相邻的2个球洞之间成绩的反弹，上一个球洞大于标准杆，下一个球洞小于标准杆为反弹。");
		}
		
		dialog_back.setOnClickListener(this);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
       }
    
	public void onClick(View v){
		Intent intent;
		switch(v.getId()){
		case R.id.help_xiaoniao_1:
			//intent = new Intent(GanCountActivity.this,HelpAverageActivity.class);
			//intent.putExtra("xiaoniao1", "xiaoniao1");
			//startActivity(intent);
			title1=gree_shuju.getText().toString().trim();
		
			fristdialog(title1,true);
			break;
		case R.id.help_xiaoniao_2:
			
			title2 = gree_shuju_fantanlv.getText().toString().trim();
			fristdialog(title2,false);
			//intent = new Intent(GanCountActivity.this,HelpAverageActivity.class);
			//intent.putExtra("xiaoniao2", "xiaoniao2");
			//startActivity(intent);
			break;
			
		case R.id.dialog_back:
			dialog.dismiss();
			break;
		}
	}
	
	
   
	  
	private void initView() {
		
		textView1 = (TextView) findViewById(R.id.textView1);
		gree_shuju = (TextView) findViewById(R.id.gree_shuju);
		gree_shuju_fantanlv =(TextView) findViewById(R.id.gree_shuju_fantanlv); 
		dialog_back = (Button) findViewById(R.id.dialog_back);
		double_eagleTextView = (TextView) findViewById(R.id.double_eagle_percentage);
		bogeyTextView = (TextView) findViewById(R.id.bogey);
		double_bogeyTextView = (TextView) findViewById(R.id.double_bogey);
		eagleTextView = (TextView) findViewById(R.id.eagle);
		birdileTextView = (TextView) findViewById(R.id.birdie);
		parTextView = (TextView) findViewById(R.id.par);
		green_shuju_scrambles = (TextView) findViewById(R.id.green_shuju_scrambles);
		green_shuju_scrambles_1 = (TextView) findViewById(R.id.green_shuju_scrambles_1);
		fanhuiButton = (Button) findViewById(R.id.scorecard_gancount_back);
	}
	class GanCount extends Thread{
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
				JSONObject jsonObject09 = new JSONObject(jsonObject.getString("item_09"));	
				double_eagle = jsonObject09.getString("double_eagle_percentage");
				eagle = jsonObject09.getString("eagle_percentage");
				birdie = jsonObject09.getString("birdie_percentage");
				bogey = jsonObject09.getString("bogey_percentage");
				double_bogey = jsonObject09.getString("double_bogey_percentage");
				par = jsonObject09.getString("par_percentage");
				bounce = jsonObject09.getString("bounce");
				
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
}
