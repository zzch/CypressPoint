package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.MajorStatisticsModel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MajorStatisticsListViewAdapter extends BaseAdapter{
	private ArrayList<MajorStatisticsModel>  statisticsModels;
	private Context context;
	private LayoutInflater inflater;
	
	private int image[]={R.drawable.zongchengji_icon_1,R.drawable.pingjunganshu_2,R.drawable.tuigan_3,
			R.drawable.shakengjiuqiu_4,R.drawable.yiqieyitui_5,R.drawable.gongguoling_6,
			R.drawable.qiudaomingzhong_7,R.drawable.kaiqiujuli_8,R.drawable.a1111};
			              
	public MajorStatisticsListViewAdapter(Context context,ArrayList<MajorStatisticsModel>  statisticsModels) {
		this.context = context;
		this.statisticsModels = statisticsModels;
		inflater = inflater.from(context);
	}
	@Override
	public int getCount() {
		
		return image.length;
	}

	@Override
	public Object getItem(int position) {
		return statisticsModels.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VoidHolder holder;
		if(convertView==null){
			holder = new VoidHolder();
			convertView = inflater.inflate(R.layout.majorstatic_item, null);
			holder.pick01 = (TextView) convertView.findViewById(R.id.majorstatic_score);
			holder.pick02 = (TextView) convertView.findViewById(R.id.majorstatic_1);
			holder.pick03 = (TextView) convertView.findViewById(R.id.majorstatic_2);
			holder.pick04 = (TextView) convertView.findViewById(R.id.majorstatic_3);
			holder.pick05 = (TextView) convertView.findViewById(R.id.majorstatic_4);
			holder.pick06 = (TextView) convertView.findViewById(R.id.majorstatic_5);
			holder.pick07 = (TextView) convertView.findViewById(R.id.majorstatic_6);
			holder.icon = (ImageView) convertView.findViewById(R.id.majorstatic_image_item);
			holder.guide = (ImageView) convertView.findViewById(R.id.guide);
			convertView.setTag(holder);
		}else{
			holder = (VoidHolder) convertView.getTag();
		}
		holder.pick01.setText(statisticsModels.get(position).getPlace1());
		if(statisticsModels.get(position).getPlace2().equals("null")){
			holder.guide.setVisibility(View.GONE);
		}
		holder.pick02.setText(panduan(statisticsModels.get(position).getPlace2()));
		holder.pick03.setText(panduan(statisticsModels.get(position).getPlace3()));
		holder.pick04.setText(panduan(statisticsModels.get(position).getPlace4()));
		holder.pick05.setText(panduan(statisticsModels.get(position).getPlace5()));
		holder.pick06.setText(panduan(statisticsModels.get(position).getPlace6()));
		holder.pick07.setText(panduan(statisticsModels.get(position).getPlace7()));
		holder.icon.setImageResource(image[position]);
		

		return convertView;
	}
	class VoidHolder{
		TextView pick01;
		TextView pick02;
		TextView pick03;
		TextView pick04;
		TextView pick05;
		TextView pick06;
		TextView pick07;
		ImageView icon;
		ImageView guide;
	}
	public String panduan(String p){
		if(p.equals("null")){
			p = "一";
		}
		return p;
		
	}
}
