package cn.com.zcty.ILovegolf.activity.view.fragment;


import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class StaticsFragmentOne extends Fragment{
	private ArrayList<String> parArrayList;
	private ArrayList<String> scoreArrayList;
	private ArrayList<String> statusArrayList;
	private TableRow t2;
	private TableRow t3;
	private TableRow t4;
	private View view;
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tv5;
	private TextView tv6;
	private TextView tv7;
	private TextView tv8;
	private TextView tv9;
	private TextView tv10;
	private TextView ttv1;
	private TextView ttv2;
	private TextView ttv3;
	private TextView ttv4;
	private TextView ttv5;
	private TextView ttv6;
	private TextView ttv7;
	private TextView ttv8;
	private TextView ttv9;
	private TextView ttv10;
	private TextView tttv1;
	private TextView tttv2;
	private TextView tttv3;
	private TextView tttv4;
	private TextView tttv5;
	private TextView tttv7;
	private TextView tttv6;
	private TextView tttv8;
	private TextView tttv9;
	private TextView tttv10;
	public StaticsFragmentOne(ArrayList<String> parArrayList,ArrayList<String> scoreArrayList,ArrayList<String> statusArrayList){
		this.parArrayList = parArrayList;
		this.scoreArrayList = scoreArrayList;
		this.statusArrayList = statusArrayList;

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.statics_fragment, container, false);	
		initView();
		Log.i("aa", parArrayList.get(1)+"d");
		return view;
	}
	@SuppressLint("NewApi")
	private void initView() {
		t2 = (TableRow) view.findViewById(R.id.tableRow2);
		t3 = (TableRow) view.findViewById(R.id.tableRow3);
		t4 = (TableRow) view.findViewById(R.id.tableRow4);
		tv1 = (TextView) view.findViewById(R.id.t1);
		tv1.setText(parArrayList.get(0));
		tv2 = (TextView) view.findViewById(R.id.t2);
		tv2.setText(parArrayList.get(1));
		tv3 = (TextView) view.findViewById(R.id.t3);
		tv3.setText(parArrayList.get(2));
		tv4 = (TextView) view.findViewById(R.id.t4);
		tv4.setText(parArrayList.get(3));
		tv5 = (TextView) view.findViewById(R.id.t5);
		tv5.setText(parArrayList.get(4));
		tv6 = (TextView) view.findViewById(R.id.t6);
		tv6.setText(parArrayList.get(5));
		tv7 = (TextView) view.findViewById(R.id.t7);
		tv7.setText(parArrayList.get(6));
		tv8 = (TextView) view.findViewById(R.id.t8);
		tv8.setText(parArrayList.get(7));
		tv9 = (TextView) view.findViewById(R.id.t9);
		tv9.setText(parArrayList.get(8));
		tv10 = (TextView) view.findViewById(R.id.t10);
		tv10.setText(parArrayList.get(9));
		ttv1 = (TextView) view.findViewById(R.id.tt1);
		ttv1.setText(scoreArrayList.get(0));
		ttv2 = (TextView) view.findViewById(R.id.tt2);
		ttv2.setText(scoreArrayList.get(1));
		ttv3 = (TextView) view.findViewById(R.id.tt3);
		ttv3.setText(scoreArrayList.get(2));
		ttv4 = (TextView) view.findViewById(R.id.tt4);
		ttv4.setText(scoreArrayList.get(3));
		ttv5 = (TextView) view.findViewById(R.id.tt5);
		ttv5.setText(scoreArrayList.get(4));
		ttv6 = (TextView) view.findViewById(R.id.tt6);
		ttv6.setText(scoreArrayList.get(5));
		ttv7 = (TextView) view.findViewById(R.id.tt7);
		ttv7.setText(scoreArrayList.get(6));
		ttv8 = (TextView) view.findViewById(R.id.tt8);
		ttv8.setText(scoreArrayList.get(7));
		ttv9 = (TextView) view.findViewById(R.id.tt9);
		ttv9.setText(scoreArrayList.get(8));
		ttv10 = (TextView) view.findViewById(R.id.tt10);
		ttv10.setText(scoreArrayList.get(9));
		tttv1 = (TextView) view.findViewById(R.id.ttt1);
		tttv1.setText(statusArrayList.get(0));
		tttv2 = (TextView) view.findViewById(R.id.ttt2);
		tttv2.setText(statusArrayList.get(1));
		tttv3 = (TextView) view.findViewById(R.id.ttt3);
		tttv3.setText(statusArrayList.get(2));
		tttv4 = (TextView) view.findViewById(R.id.ttt4);
		tttv4.setText(statusArrayList.get(3));
		tttv5 = (TextView) view.findViewById(R.id.ttt5);
		tttv5.setText(statusArrayList.get(4));
		tttv6 = (TextView) view.findViewById(R.id.ttt6);
		tttv6.setText(statusArrayList.get(5));
		tttv7 = (TextView) view.findViewById(R.id.ttt7);
		tttv7.setText(statusArrayList.get(6));
		tttv8 = (TextView) view.findViewById(R.id.ttt8);
		tttv8.setText(statusArrayList.get(7));
		tttv9 = (TextView) view.findViewById(R.id.ttt9);
		tttv9.setText(statusArrayList.get(8));
		tttv10 = (TextView) view.findViewById(R.id.ttt10);
		tttv10.setText(statusArrayList.get(9));
		if(panDuan(ttv1)){
			getBack(tv1, ttv1);
		}else{
			
		}
		if(panDuan(ttv2)){
			getBack(tv2, ttv2);
		}else{
			
			}
		if(panDuan(ttv3)){
			getBack(tv3, ttv3);
		}else{
			
			}
		if(panDuan(ttv4)){
			getBack(tv4, ttv4);
		}else{
			
			}
		if(panDuan(ttv5)){
			getBack(tv5, ttv5);
		}else{
			
			}
		if(panDuan(ttv6)){
			getBack(tv6, ttv6);
		}else{
			
			}
		if(panDuan(ttv7)){
			getBack(tv7, ttv7);
		}else{
			
			}
		if(panDuan(ttv8)){
			getBack(tv8, ttv8);
		}else{
			
			}
		if(panDuan(ttv9)){
			getBack(tv9, ttv9);
		}else{
			
			}
		panDuan(ttv10);
		panDuan(tttv1);
		panDuan(tttv2);
		panDuan(tttv3);
		panDuan(tttv4);
		panDuan(tttv5);
		panDuan(tttv6);
		panDuan(tttv7);
		panDuan(tttv8);
		panDuan(tttv9);
		panDuan(tttv10);

	}
	public boolean panDuan(TextView text){
		if(text.getText().toString().equals("nul")){
			text.setText("");
			return false;
		}else{
			return true;
		}


	}
	public void getBack(TextView t,TextView tt){
		int key = Integer.parseInt(tt.getText().toString())-Integer.parseInt(t.getText().toString());
		switch (key) {
		case -3:
			//信天翁球
			tt.setBackgroundColor(Color.rgb(32, 66, 171));
			break;
		case -2:
			//老鹰球
			tt.setBackgroundColor(Color.rgb(92, 132, 208));
			break;
		case -1:
			//小鸟
			tt.setBackgroundColor(Color.rgb(173, 195, 243));
			break;
		case 0:
			//标准杆
			tt.setBackgroundColor(Color.rgb(212, 212, 212));
			break;
		case 1:
			//柏忌
			tt.setBackgroundColor(Color.rgb(213, 181, 58));
			break;
		case 2:
			//双柏忌
			tt.setBackgroundColor(Color.rgb(200, 141, 25));
			break;
		}
		if(key>2){
			//双柏忌
			tt.setBackgroundColor(Color.rgb(200, 141, 25));
		}
		if(key<-3){
			//信天翁球
			tt.setBackgroundColor(Color.rgb(32, 66, 171));
		}

	}
}
