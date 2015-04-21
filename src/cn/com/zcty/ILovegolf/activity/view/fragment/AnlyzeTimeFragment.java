package cn.com.zcty.ILovegolf.activity.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.count.PicPopupWindow;


public class AnlyzeTimeFragment extends Fragment{
	private View view;
	private RelativeLayout startTimeLayout;
	private RelativeLayout endtimeLayout;
	private TextView startTextView;
	private TextView endTextView;
	private String date ;
	private String startdate;
	private String enddate;
	private boolean f = false;
	private boolean f1 = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.anlyze_time_fragment, container, false);	
		initView();
		setListeners();		
		return view;
	}
	@Override
	public void onResume() {
		super.onResume();
		getData();
	}
	private void getData() {
		SharedPreferences sp = getActivity().getSharedPreferences("date", Context.MODE_PRIVATE);
		date = sp.getString("date", "date");
		String sgin = sp.getString("sgin", "sgin");
		if(sgin.equals("0")){
			startTextView.setText(date);
			endTextView.setText(date);
			startdate = date;
			enddate  = date;
		}else if(sgin.equals("1")){
			startTextView.setText(date);
			startdate = date;
		}else if(sgin.equals("2")){
			endTextView.setText(date);
			enddate = date;
		}
	}
	private void setListeners() {
		
		startTimeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {			
				Intent intent = new Intent(getActivity(),PicPopupWindow.class);
				intent.putExtra("sgin", "1");
				intent.putExtra("dates", startdate);
				Log.i("dateqiu", date);
				startActivity(intent);
				
			}
		});
		endtimeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),PicPopupWindow.class);
				intent.putExtra("sgin", "2");
				intent.putExtra("dates", enddate);
				startActivity(intent);
			}
		});
	}
	
	private void initView() {
		startTimeLayout = (RelativeLayout) view.findViewById(R.id.anlyze_time_start_relative);
		endtimeLayout = (RelativeLayout) view.findViewById(R.id.anlyze_time_end_relative);
		startTextView = (TextView) view.findViewById(R.id.anlyze_time_start);
		endTextView = (TextView) view.findViewById(R.id.anlyze_time_end);
	}
	
}
