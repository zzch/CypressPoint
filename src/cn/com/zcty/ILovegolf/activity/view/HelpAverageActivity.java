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
	private Button dialog_back;
	private TextView title_name;
	
	private TextView textView2;
	
	
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
		dialog_back = (Button) findViewById(R.id.dialog_back);
		title_name = (TextView) findViewById(R.id.title_name);
		textView2 = (TextView) findViewById(R.id.textView2);
		
		dialog_back.setOnClickListener(new OnClickListener() {
			
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
			title_name.setText("救平标准杆率");	
			textView2.setText("在(3/4/5)杆洞大于标准杆上果岭并且打出小于等于标准杆的成绩。");
			
		}
		if(intent.getStringExtra("putt1")!=null){
			title_name.setText("平均/洞");	
			textView2.setText("当前推杆数总和与当前完成球洞的平均数。");
		}
		if(intent.getStringExtra("putt2")!=null){
			title_name.setText("标准杆");	
			textView2.setText("当前标准杆上果岭后推杆数的总和与当前完成标准杆上果岭球洞的平均数。");
		}
		if(intent.getStringExtra("putt3")!=null){
			title_name.setText("大于标准杆");	
			textView2.setText("当前大于标准杆上果岭后推杆数的总和与当前完成大于标准杆上果岭球洞的平均数。");
		}
		
        if(intent.getStringExtra("putt4")!=null){
			title_name.setText("第一次推杆后距离球洞码数的平均数");	
			textView2.setText("当前完成球洞的第一次推杆后剩余距离的平均数。");
		}
        
        if(intent.getStringExtra("putt5")!=null){
			title_name.setText("最后一推码数的平均数");	
			textView2.setText("当前完成所有球洞的最后一次推杆距离的平均数。");
		}
        
        if(intent.getStringExtra("putt6")!=null){
			title_name.setText("距离球洞   0 - 1 码");	
			textView2.setText("该项目计算在球洞0-1码时尝试推杆的次数，该距离推进洞的平均杆数，该距离一杆进洞的几率，以及该距离第一推后平均剩余码数。");
		}
        if(intent.getStringExtra("putt7")!=null){
			title_name.setText("距离球洞   1 - 2 码");	
			textView2.setText("该项目计算在球位于球洞大于1码小于2码时尝试推杆的次数，该距离推进洞的平均杆数，该距离一杆进洞的几率，以及该距离第一推后平均剩余码数。");
		}
        
        if(intent.getStringExtra("putt8")!=null){
			title_name.setText("距离球洞   2 - 3 码");	
			textView2.setText("该项目计算在球位于球洞大于2码小于3码时尝试推杆的次数，该距离推进洞的平均杆数，该距离一杆进洞的几率，以及该距离第一推后平均剩余码数。");
		}
        if(intent.getStringExtra("putt9")!=null){
			title_name.setText("距离球洞   3 - 5 码");	
			textView2.setText("该项目计算在球位于球洞大于3码小于5码时尝试推杆的次数，该距离推进洞的平均杆数，该距离一杆进洞的几率，以及该距离第一推后平均剩余码数。");
		}
        if(intent.getStringExtra("putt10")!=null){
			title_name.setText("距离球洞   5 - 8 码");	
			textView2.setText("该项目计算在球位于球洞大于5码小于8码时尝试推杆的次数，该距离推进洞的平均杆数，该距离一杆进洞的几率，以及该距离第一推后平均剩余码数。");
		}
        if(intent.getStringExtra("putt11")!=null){
			title_name.setText("距离球洞   8 - 13 码");	
			textView2.setText("该项目计算在球位于球洞大于8码小于13码时尝试推杆的次数，该距离推进洞的平均杆数，该距离一杆进洞的几率，以及该距离第一推后平均剩余码数。");
		}
        if(intent.getStringExtra("putt12")!=null){
			title_name.setText("距离球洞   13 - 33 码");	
			textView2.setText("该项目计算在球位于球洞大于13码小于33码时尝试推杆的次数，该距离推进洞的平均杆数，该距离一杆进洞的几率，以及该距离第一推后平均剩余码数。");
		}
        
        
        
        if(intent.getStringExtra("shakeng1")!=null){
			title_name.setText("沙坑救球");	
			textView2.setText("球进入距离果岭40码的沙坑后一切一推进洞。");
		}
        if(intent.getStringExtra("shakeng2")!=null){
			title_name.setText("距离球洞  0 - 10码");	
			textView2.setText("该项目计算球进入距离果岭0-10码的沙坑后一切一推进洞。");
		}
        if(intent.getStringExtra("shakeng3")!=null){
			title_name.setText("距离球洞  10 - 20码");	
			textView2.setText("该项目计算球进入距离果岭大于10小于20码的沙坑后一切一推进洞。");
		}
        if(intent.getStringExtra("shakeng4")!=null){
			title_name.setText("距离球洞  20 - 50码");	
			textView2.setText("该项目计算球进入距离果岭大于20小于50码的沙坑后一切一推进洞。");
		}
        
        if(intent.getStringExtra("qiegan")!=null){
			title_name.setText("切杆进洞");	
			textView2.setText("果岭外一杆进洞。");
		}
        
        if(intent.getStringExtra("gree1")!=null){
			title_name.setText("标准上果岭");	
			textView2.setText("3杆洞1杆上果岭，4杆洞2杆上果岭，5杆洞3杆上果岭。");
		}
        if(intent.getStringExtra("gree2")!=null){
			title_name.setText("标准杆上果岭距离球洞小于5码");	
			textView2.setText("3/4/5杆洞的标准杆上果岭后第一次推杆前距离果岭小于等于5码。");
		}
        
        if(intent.getStringExtra("mingzhong1")!=null){
			title_name.setText("命中");	
			textView2.setText("");
		}
        if(intent.getStringExtra("mingzhong2")!=null){
			title_name.setText("左侧");	
			textView2.setText("");
		}
        if(intent.getStringExtra("mingzhong3")!=null){
			title_name.setText("右侧");	
			textView2.setText("");
		}
        
        if(intent.getStringExtra("kaiqiu")!=null){
			title_name.setText("最佳球位");	
			textView2.setText("4/5杆洞开球后未上球道，但是该球洞标准杆上果岭。");
		}
        
        if(intent.getStringExtra("xiaoniao1")!=null){
			title_name.setText("小鸟球转化率");	
			textView2.setText("3/4/5杆洞小于等于标准杆上果岭后并且该洞成绩小于标准杆与当前所完成洞的比例。");
		}
        if(intent.getStringExtra("xiaoniao2")!=null){
			title_name.setText("反弹率");	
			textView2.setText("相邻的2个球洞之间成绩的反弹，上一个球洞大于标准杆，下一个球洞小于标准杆为反弹。");
		}
        
	}
	
	
}
