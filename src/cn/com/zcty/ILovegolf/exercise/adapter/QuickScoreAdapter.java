package cn.com.zcty.ILovegolf.exercise.adapter;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.QuickContent;
import cn.com.zcty.ILovegolf.tools.SlidingDeleteSlideView;
import cn.com.zcty.ILovegolf.tools.SlidingDeleteSlideView.OnSlideListener;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public  class QuickScoreAdapter extends BaseAdapter {
	   
	 private  List<QuickContent> quickContents;
	 private Context context;
	 private View view;
	 private OnSlideListener onSlideListener;
     private OnDeleteListener onDeleteListen;
	 private boolean isLongState;
	 private LayoutInflater mInflater;
	 private ArrayList<String> nameArrayList;
	 private HashMap<Integer, Boolean> checkedItemMap = new HashMap<Integer, Boolean>();
	 public QuickScoreAdapter(Context context,List<QuickContent> quickContents,ArrayList<String> nameArrayList, OnSlideListener onSlideListener,
				OnDeleteListener onDeleteListen){
		this.context=context;
		this.quickContents= quickContents;;
		this.onSlideListener = onSlideListener;
		this.onDeleteListen = onDeleteListen;
		this.nameArrayList = nameArrayList;
		mInflater = LayoutInflater.from(context);
	 }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return quickContents.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return quickContents.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(final int position,final View convertView, ViewGroup parent) {
		
       ViewHolder holder;
       SlidingDeleteSlideView slideView = (SlidingDeleteSlideView) convertView;
       // 如果没有设置过,初始化convertView
       if(slideView == null){
    	// 获得设置的view
    	   View itemView = mInflater.inflate(R.layout.quick_score_item, null); 
    	   slideView = new SlidingDeleteSlideView(context);
			slideView.setContentView(itemView);
			holder = new ViewHolder(slideView);
			
			slideView.setOnSlideListener(onSlideListener);
			slideView.setTag(holder);
			
       }else{
    	   
    	// 有直接获得ViewHolder
           holder = (ViewHolder) convertView.getTag();
       }
       QuickContent item = quickContents.get(position);
		item.slideView = slideView;
		item.slideView.shrinkByFast();
		 holder.kpitname.setText(nameArrayList.get(position));
		SimpleDateFormat  simpleDate = new SimpleDateFormat("yyyy年MM月dd");
		long d = (Integer.parseInt(quickContents.get(position).getStarted_at()))*1000;
		 String date =	simpleDate.format(d);		 
		Log.i("date", d+"");
		 holder.time.setText(date);
		 holder.type.setText(quickContents.get(position).getType());
		 holder.gan_number.setText(quickContents.get(position).getRecorded_scorecards_count());
		 if(quickContents.get(position).getStrokes().equals("null")){
			 
			 holder.Pole_number.setText("未开始"); 
		 }
       holder.deleteHolder.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onDeleteListen.onDelete(convertView, position);
			}
		});
         return slideView;
	 }
	
	public static class ViewHolder{
		public TextView kpitname;
		public TextView time;
		public TextView type;
		public TextView gan_number;
		public TextView Pole_number;
		public ViewGroup deleteHolder;
		
		LinearLayout xlist_item_relayout;
		
		ViewHolder(View view) {
			xlist_item_relayout = (LinearLayout) view.findViewById(R.id.xlist_item_relayout);
			 //球场名称
	           kpitname=(TextView) view.findViewById(R.id.kpitname);  
	           
	        
	  		 //日期
	           time=(TextView) view.findViewById(R.id.time);
	         
	  		 //赛事类型
	           type=(TextView) view.findViewById(R.id.practice);
	           
	  		 //记录打了几个球洞
	          gan_number=(TextView) view.findViewById(R.id.gan_number);
	         
	  		 //成绩
	           Pole_number = (TextView) view.findViewById(R.id.Pole_number);  
	         
	           deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
		}
	 }


	public interface OnDeleteListener {
		public void onDelete(View view, int position);
	}

	public void setIsLongState(boolean isLongState) {
		this.isLongState = isLongState;
	}

	public void setCheckItemMap(HashMap<Integer, Boolean> checkedItemMap) {
		this.checkedItemMap = checkedItemMap;
	}
}   