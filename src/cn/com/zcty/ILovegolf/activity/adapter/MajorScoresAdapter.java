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
	
		if(majorArrayList.get(position).getCool().equals("fairway")||majorArrayList.get(position).getCool().equals("球道")){
			holder.coolText.setText("球道");

		}else if(majorArrayList.get(position).getCool().equals("green")||majorArrayList.get(position).getCool().equals("果岭")){
			holder.coolText.setText("果岭");
		}
		else if(majorArrayList.get(position).getCool().equals("left_rough")||majorArrayList.get(position).getCool().equals("球道外左侧")){
			holder.coolText.setText("球道外左侧");
		}
		else if(majorArrayList.get(position).getCool().equals("right_rough")||majorArrayList.get(position).getCool().equals("球道外右侧")){
			holder.coolText.setText("球道外右侧");
		}
		else if(majorArrayList.get(position).getCool().equals("bunker")||majorArrayList.get(position).getCool().equals("沙坑")){
			holder.coolText.setText("沙坑");
		}else if(majorArrayList.get(position).getCool().equals("unplayable")||majorArrayList.get(position).getCool().equals("不可打")){
			holder.coolText.setText("不可打");
		}else{
			holder.coolText.setText("");
		}
		
		if(majorArrayList.get(position).getCount().equals("1w")){
			holder.countText.setText("Driver");
		}else if(majorArrayList.get(position).getCount().equals("pt")){
			holder.countText.setText("Putter");
		}else if(majorArrayList.get(position).getCount().equals("3w")){
			holder.countText.setText("3 Wood");
		}else if(majorArrayList.get(position).getCount().equals("5w")){
			holder.countText.setText("5 Wood");
		}else if(majorArrayList.get(position).getCount().equals("7w")){
			holder.countText.setText("7 Wood");
		}else if(majorArrayList.get(position).getCount().equals("2h")){
			holder.countText.setText("2 Hybrid");
		}else if(majorArrayList.get(position).getCount().equals("3h")){
			holder.countText.setText("3 Hybrid");
		}else if(majorArrayList.get(position).getCount().equals("4h")){
			holder.countText.setText("4 Hybrid");
		}else if(majorArrayList.get(position).getCount().equals("5h")){
			holder.countText.setText("5 Hybrid");
		}else if(majorArrayList.get(position).getCount().equals("1i")){
			holder.countText.setText("1 Iron");
		}else if(majorArrayList.get(position).getCount().equals("2i")){
			holder.countText.setText("2 Iron");
		}else if(majorArrayList.get(position).getCount().equals("3i")){
			holder.countText.setText("3 Iron");
		}else if(majorArrayList.get(position).getCount().equals("4i")){
			holder.countText.setText("4 Iron");
		}else if(majorArrayList.get(position).getCount().equals("5i")){
			holder.countText.setText("5 Iron");
		}else if(majorArrayList.get(position).getCount().equals("6i")){
			holder.countText.setText("6 Iron");
		}else if(majorArrayList.get(position).getCount().equals("7i")){
			holder.countText.setText("7 Iron");
		}else if(majorArrayList.get(position).getCount().equals("8i")){
			holder.countText.setText("8 Iron");
		}else if(majorArrayList.get(position).getCount().equals("9i")){
			holder.countText.setText("9 Iron");
		}else if(majorArrayList.get(position).getCount().equals("pw")){
			holder.countText.setText("PW");
		}else if(majorArrayList.get(position).getCount().equals("gw")){
			holder.countText.setText(majorArrayList.get(position).getCount());
		}else if(majorArrayList.get(position).getCount().equals("sw")){
			holder.countText.setText("GW");
		}else if(majorArrayList.get(position).getCount().equals("lw")){
			holder.countText.setText("LW");
		}
		holder.orderText.setText(position+1+"");
		holder.distanceText.setText(majorArrayList.get(position).getDistance());
		holder.pentanilsText.setText(majorArrayList.get(position).getPentails());
		


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
