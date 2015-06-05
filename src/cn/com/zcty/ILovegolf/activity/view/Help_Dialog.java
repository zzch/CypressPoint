package cn.com.zcty.ILovegolf.activity.view;

import cn.com.zcty.ILovegolf.activity.R;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Help_Dialog extends Dialog implements OnClickListener{
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
	private TextView text_shakeng_1;
	private TextView text_shakeng_2;
	private TextView text_shakeng_3;
	private TextView text_shakeng_4;
	private TextView text_qiegan_1;
	private TextView text_gree_1;
	private TextView text_gree_2;
	
	private TextView text_mingzhong_1;
	private TextView text_mingzhong_2;
	private TextView text_mingzhong_3;
	private TextView text_kaiqiu_1;
	
	private TextView text_xiaoniao_1;
	private TextView text_xiaoniao_2;

	public Help_Dialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.register_dialog);
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
		
		text_shakeng_1 = (TextView) findViewById(R.id.text_shakeng_1);
		text_shakeng_2 = (TextView) findViewById(R.id.text_shakeng_2);
		text_shakeng_3 = (TextView) findViewById(R.id.text_shakeng_3);
		text_shakeng_4 = (TextView) findViewById(R.id.text_shakeng_4);
		
		text_qiegan_1 = (TextView) findViewById(R.id.text_qiegan_1);
		
		text_gree_1 = (TextView) findViewById(R.id.text_gree_1);
		text_gree_2 = (TextView) findViewById(R.id.text_gree_2);
		
		text_mingzhong_1 = (TextView) findViewById(R.id.text_mingzhong_1);
		text_mingzhong_2 = (TextView) findViewById(R.id.text_mingzhong_2);
		text_mingzhong_3 = (TextView) findViewById(R.id.text_mingzhong_3);
		
		text_kaiqiu_1 = (TextView) findViewById(R.id.text_kaiqiu_1);
		
		text_xiaoniao_1 = (TextView) findViewById(R.id.text_xiaoniao_1);
		text_xiaoniao_2 = (TextView) findViewById(R.id.text_xiaoniao_2);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dismiss();
	}
	
	public void getData(){
		//intent =;
		
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
        
        if(intent.getStringExtra("shakeng1")!=null){
			title_name.setText("沙坑救球");	
			text_shakeng_1.setVisibility(View.VISIBLE);
		}
        if(intent.getStringExtra("shakeng2")!=null){
			title_name.setText("沙坑0-10");	
			text_shakeng_2.setVisibility(View.VISIBLE);
		}
        if(intent.getStringExtra("shakeng3")!=null){
			title_name.setText("沙坑10-20");	
			text_shakeng_3.setVisibility(View.VISIBLE);
		}
        if(intent.getStringExtra("shakeng4")!=null){
			title_name.setText("沙坑20-30");	
			text_shakeng_4.setVisibility(View.VISIBLE);
		}
        
        if(intent.getStringExtra("qiegan")!=null){
			title_name.setText("切杆");	
			text_qiegan_1.setVisibility(View.VISIBLE);
		}
        
        if(intent.getStringExtra("gree1")!=null){
			title_name.setText("标准上果岭");	
			text_gree_1.setVisibility(View.VISIBLE);
		}
        if(intent.getStringExtra("gree2")!=null){
			title_name.setText("小于5码");	
			text_gree_2.setVisibility(View.VISIBLE);
		}
        
        if(intent.getStringExtra("mingzhong1")!=null){
			title_name.setText("命中");	
			text_mingzhong_1.setVisibility(View.VISIBLE);
		}
        if(intent.getStringExtra("mingzhong2")!=null){
			title_name.setText("左侧");	
			text_mingzhong_2.setVisibility(View.VISIBLE);
		}
        if(intent.getStringExtra("mingzhong3")!=null){
			title_name.setText("右侧");	
			text_mingzhong_3.setVisibility(View.VISIBLE);
		}
        
        if(intent.getStringExtra("kaiqiu")!=null){
			title_name.setText("右侧");	
			text_kaiqiu_1.setVisibility(View.VISIBLE);
		}
        
        if(intent.getStringExtra("xiaoniao1")!=null){
			title_name.setText("小鸟球转化率");	
			text_xiaoniao_1.setVisibility(View.VISIBLE);
		}
        if(intent.getStringExtra("xiaoniao2")!=null){
			title_name.setText("反弹率");	
			text_xiaoniao_2.setVisibility(View.VISIBLE);
		}
        
	}
	

}
