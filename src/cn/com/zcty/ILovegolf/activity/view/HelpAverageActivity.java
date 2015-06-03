package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class HelpAverageActivity extends Activity {
	
	private Intent intent;
	private Button help_back;
	private TextView title_name;
	private TextView text_avg_1;
	private TextView text_putt_1;
	private TextView text_putt_2;
	private TextView text_putt_3;
	private TextView text_putt_4;
	private TextView text_putt_5;
	private TextView text_putt_6;
	private TextView text_putt_7;
	private TextView text_putt_8;
	private TextView text_putt_9;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		initView();
		getData();

	}
	
	public void initView(){
		help_back = (Button) findViewById(R.id.help_back);
		title_name = (TextView) findViewById(R.id.title_name);
		text_avg_1 = (TextView) findViewById(R.id.text_avg_1);
		text_putt_1 = (TextView) findViewById(R.id.text_putt_1);
		text_putt_2 = (TextView) findViewById(R.id.text_putt_2);
		text_putt_3 = (TextView) findViewById(R.id.text_putt_3);
		text_putt_4 = (TextView) findViewById(R.id.text_putt_4);
		text_putt_5 = (TextView) findViewById(R.id.text_putt_5);
		text_putt_6 = (TextView) findViewById(R.id.text_putt_6);
		text_putt_7 = (TextView) findViewById(R.id.text_putt_7);
		text_putt_8 = (TextView) findViewById(R.id.text_putt_8);
		text_putt_9 = (TextView) findViewById(R.id.text_putt_9);
		help_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	public void getData(){
		intent =getIntent();
		
		if(intent.getStringExtra("avg")!=null){
			title_name.setText("平均杆数");	
			text_avg_1.setVisibility(View.VISIBLE);
			
		}
		if(intent.getStringExtra("putt1")!=null){
			title_name.setText("平均/洞");	
			text_putt_1.setVisibility(View.VISIBLE);
		}
		if(intent.getStringExtra("putt2")!=null){
			title_name.setText("标准杆");	
			text_putt_2.setVisibility(View.VISIBLE);
		}
		if(intent.getStringExtra("putt3")!=null){
			title_name.setText("大于标准杆");	
			text_putt_3.setVisibility(View.VISIBLE);
		}
        if(intent.getStringExtra("putt4")!=null){
			title_name.setText("推杆4");	
			text_putt_4.setVisibility(View.VISIBLE);
		}
        
        if(intent.getStringExtra("putt5")!=null){
			title_name.setText("推杆5");	
			text_putt_5.setVisibility(View.VISIBLE);
		}
        
        if(intent.getStringExtra("putt6")!=null){
			title_name.setText("推杆6");	
			text_putt_6.setVisibility(View.VISIBLE);
		}
        if(intent.getStringExtra("putt7")!=null){
			title_name.setText("推杆7");	
			text_putt_7.setVisibility(View.VISIBLE);
		}
        
        if(intent.getStringExtra("putt8")!=null){
			title_name.setText("推杆8");	
			text_putt_8.setVisibility(View.VISIBLE);
		}
        if(intent.getStringExtra("putt9")!=null){
			title_name.setText("推杆9");	
			text_putt_9.setVisibility(View.VISIBLE);
		}
	}

}
