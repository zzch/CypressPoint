package cn.com.zcty.ILovegolf.activity.view.count;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.ArrayDayNumberWheelAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.ArrayMonthNumberWheelAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.ArrayYearNumberWheelAdapter;
import cn.com.zcty.ILovegolf.tools.OnWheelChangedListener;
import cn.com.zcty.ILovegolf.tools.WheelView;

public class PicPopupWindow extends Activity {
	private WheelView yearWheelView;
	private WheelView monthWheelView;
	private WheelView dayWheelView;
	private LinearLayout layout;
	private ArrayYearNumberWheelAdapter yearadapter;
	private ArrayMonthNumberWheelAdapter monthdapter;
	private ArrayDayNumberWheelAdapter daydapter;
	private String year = "1930";
	private String moth = "01";
	private String day = "01";
	private String date = "1930-01-01";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_dialog_wheel);
		initView();
		wheelListeners();
		setListeners();
		getData();
		
		
	}
	private void getData() {
		yearadapter = new ArrayYearNumberWheelAdapter(this);
		monthdapter = new ArrayMonthNumberWheelAdapter(this);
		daydapter = new ArrayDayNumberWheelAdapter(this);
		yearWheelView.setViewAdapter(yearadapter);
		monthWheelView.setViewAdapter(monthdapter);
		dayWheelView.setViewAdapter(daydapter);
		
		Intent intent = getIntent();
		String dates = intent.getStringExtra("dates");
		
		String[] s = dates.split("-");
		int y = Integer.parseInt(s[0]);
		int m = Integer.parseInt(s[1]);
		int d = Integer.parseInt(s[2]);
		yearWheelView.setCurrentItem(y-1930);
		monthWheelView.setCurrentItem(m-1);
		dayWheelView.setCurrentItem(d-1);
		
		
	}
	private void setListeners() {
		// 添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
				layout.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
								Toast.LENGTH_SHORT).show();
					}
				});		
	}
	private void initView() {
		yearWheelView = (WheelView) findViewById(R.id.anlyze_year);
		monthWheelView = (WheelView) findViewById(R.id.anlyze_month);
		dayWheelView = (WheelView) findViewById(R.id.anlyze_day);
		layout = (LinearLayout) findViewById(R.id.pop_layout);
	}
	// 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Intent intent = getIntent();
		String sgin = intent.getStringExtra("sgin");
		SharedPreferences sp = getSharedPreferences("date", MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("date", date);
		editor.putString("sgin", sgin);
		editor.commit();
		finish();
		return true;
	}
	public void wheelListeners(){
		yearWheelView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				year =  (String) yearadapter.getItemText(newValue).subSequence(0, yearadapter.getItemText(newValue).length()-1);
				//years = Integer.parseInt(year);
				//SimpleDateFormat time = new SimpleDateFormat("yyyy");
				//years = Integer.parseInt(time.format(new Date()))-years;
				date = year+"-"+moth+"-"+day;
			}
		});
		monthWheelView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				moth =  (String) monthdapter.getItemText(newValue).subSequence(0, monthdapter.getItemText(newValue).length()-1);
				if(Integer.parseInt(moth)<10){
					moth = "0"+moth;
				}
				date = year+"-"+moth+"-"+day;
			}
		});
		dayWheelView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				day =  (String) daydapter.getItemText(newValue).subSequence(0, daydapter.getItemText(newValue).length()-1);
				if(Integer.parseInt(day)<10){
					day = "0"+day;
				}
				date = year+"-"+moth+"-"+day;
			}
		});
	}
	
	

}
