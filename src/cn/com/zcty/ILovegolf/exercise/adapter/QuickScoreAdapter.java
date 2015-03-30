package cn.com.zcty.ILovegolf.exercise.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.QuickContent;
import cn.com.zcty.ILovegolf.utils.TimeUtil;

public  class QuickScoreAdapter extends BaseAdapter {

	private  List<QuickContent> quickContents;
	private Context context;
	private LayoutInflater mInflater;
	private ArrayList<String> nameArrayList;
	public QuickScoreAdapter(Context context,List<QuickContent> quickContents,ArrayList<String> nameArrayList){
		this.context=context;
		this.quickContents= quickContents;;
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
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.quick_score_item, null); 
			holder = new ViewHolder();
			//holder.xlist_item_relayout = (LinearLayout) view.findViewById(R.id.xlist_item_relayout);
			//球场名称
			holder.kpitname=(TextView) convertView.findViewById(R.id.kpitname);     
			//日期
			holder.time=(TextView) convertView.findViewById(R.id.time);    
			//赛事类型
			holder.type=(TextView) convertView.findViewById(R.id.practice);	           
			//记录打了几个球洞
			holder.gan_number=(TextView) convertView.findViewById(R.id.gan_number);	         
			//成绩
			holder.Pole_number = (TextView) convertView.findViewById(R.id.Pole_number);  
			holder.coating = (ImageView) convertView.findViewById(R.id.tv_coating);
			holder.functions = (TextView) convertView.findViewById(R.id.tv_functions);
			holder.image_1 = (ImageView) convertView.findViewById(R.id.image_1);
			convertView.setTag(holder);

		}else{

			// 有直接获得ViewHolder
			holder = (ViewHolder) convertView.getTag();
		}
		holder.kpitname.setText(nameArrayList.get(position));
		SimpleDateFormat  simpleDate = new SimpleDateFormat("yyyy年MM月dd");
		long d = (Integer.parseInt(quickContents.get(position).getStarted_at()));
		String date =	TimeUtil.utc2Local(TimeUtil.secondTurnMs(d), TimeUtil.LOCAL_TIME_PATTERN);		 
		holder.time.setText(date);
		holder.type.setText(quickContents.get(position).getType());
		holder.gan_number.setText(quickContents.get(position).getRecorded_scorecards_count());
		Log.i("chengji", "chengji---"+quickContents.get(position).getRecorded_scorecards_count());
		if(quickContents.get(position).getStrokes().equals("null")){
			holder.image_1.setVisibility(View.GONE);
			holder.Pole_number.setTextSize(24);
			holder.Pole_number.setText("未开始"); 
		}
		holder.coating.setVisibility(View.VISIBLE);
		holder.functions.setClickable(false);
		return convertView;
	}

	public static class ViewHolder{
		public TextView kpitname;
		public TextView time;
		public TextView type;
		public TextView gan_number;
		public TextView Pole_number;
		public ViewGroup deleteHolder;
		public ImageView coating;
		public TextView functions;
		public ImageView image_1;
		//LinearLayout xlist_item_relayout;

	}



}   