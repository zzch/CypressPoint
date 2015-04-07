package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.MajorScoreAdapter.ViewHolder;
import cn.com.zcty.ILovegolf.model.MajorScore;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MajorScoresAdapter extends BaseAdapter{
	private ArrayList<MajorScore> majorArrayList;
	private Context context;
	private LayoutInflater inflater;
	public MajorScoresAdapter(Context context,ArrayList<MajorScore> majorArrayList) {
		this.majorArrayList = majorArrayList;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		Log.i("ceshijieguozhi", majorArrayList.size()+"zhou");
		return majorArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return majorArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		Log.i("ceshijieguozhi", majorArrayList.get(position).getOrder()+"zhou");

		if(convertView==null){
			convertView = inflater.inflate(R.layout.manager_group_list_item_parent, parent, false);
			holder = new Holder();
			holder.orderText = (TextView) convertView.findViewById(R.id.major_order_item);
			holder.distanceText = (TextView) convertView.findViewById(R.id.major_distance_item);
			holder.coolText = (TextView) convertView.findViewById(R.id.major_cool_item);
			holder.pentanilsText = (TextView) convertView.findViewById(R.id.major_pentails_item);
			holder.countText = (TextView) convertView.findViewById(R.id.major_count_item);
	
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.orderText.setText(position+1+"");
		holder.distanceText.setText(majorArrayList.get(position).getDistance());
		holder.coolText.setText(majorArrayList.get(position).getCool());
		holder.pentanilsText.setText(majorArrayList.get(position).getPentails());
		holder.countText.setText(majorArrayList.get(position).getCount());
		
	
		return convertView;
	}
	class Holder{
		TextView orderText;
		TextView distanceText;
		TextView coolText;
		TextView pentanilsText;
		TextView countText;
		
	}
}
