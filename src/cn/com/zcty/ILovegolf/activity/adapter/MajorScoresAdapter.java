package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.MajorScoreAdapter.ViewHolder;
import cn.com.zcty.ILovegolf.model.MajorScore;
import cn.com.zcty.ILovegolf.model.MajorScoreJiQiu;
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
	private ArrayList<MajorScoreJiQiu> addArrayList = new ArrayList<MajorScoreJiQiu>();
	public MajorScoresAdapter(Context context,ArrayList<MajorScore> majorArrayList) {
		this.majorArrayList = majorArrayList;
		inflater = LayoutInflater.from(context);
		for(int i=0;i<majorArrayList.size();i++){
			MajorScoreJiQiu m = new MajorScoreJiQiu();
			if(majorArrayList.get(i).getCool()==null){
				
			}else{
			if(majorArrayList.get(i).getCool().equals("unplayable")||majorArrayList.get(i).getCool().equals("不可打")){
					m.setPoint_of_fall("unplayable");
					m.setPenalties(majorArrayList.get(i).getPentails());
				}else{
					m.setPoint_of_fall(majorArrayList.get(i).getCool());	
					m.setPenalties("0");
				}
			
			m.setDistance_from_hole(majorArrayList.get(i).getDistance());
			
			
			m.setClub(majorArrayList.get(i).getCount());
			addArrayList.add(m);
		}}
		
	}
	public ArrayList<MajorScoreJiQiu> list(){
		
		
		
		return addArrayList;
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
		if(majorArrayList.get(position).getCool()==null){
			
		}else{
		if(majorArrayList.get(position).getCool().equals("fairway")||majorArrayList.get(position).getCool().equals("球道")){
			holder.coolText.setText("球道");
			holder.pentanilsText.setText("0");

		}else if(majorArrayList.get(position).getCool().equals("green")||majorArrayList.get(position).getCool().equals("果岭")){
			holder.coolText.setText("果岭");
			holder.pentanilsText.setText("0");
		}
		else if(majorArrayList.get(position).getCool().equals("left_rough")||majorArrayList.get(position).getCool().equals("球道外左侧")){
			holder.coolText.setText("球道外左侧");
			holder.pentanilsText.setText("0");
		}
		else if(majorArrayList.get(position).getCool().equals("right_rough")||majorArrayList.get(position).getCool().equals("球道外右侧")){
			holder.coolText.setText("球道外右侧");
			holder.pentanilsText.setText("0");
		}
		else if(majorArrayList.get(position).getCool().equals("bunker")||majorArrayList.get(position).getCool().equals("沙坑")){
			holder.coolText.setText("沙坑");
			holder.pentanilsText.setText("0");
		}else if(majorArrayList.get(position).getCool().equals("unplayable")||majorArrayList.get(position).getCool().equals("不可打")){
			holder.coolText.setText("不可打");
			holder.pentanilsText.setText(majorArrayList.get(position).getPentails());
		}else{
			holder.coolText.setText("");
			holder.pentanilsText.setText("0");
		}
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
			holder.countText.setText("GW");
		}else if(majorArrayList.get(position).getCount().equals("sw")){
			holder.countText.setText("SW");
		}else if(majorArrayList.get(position).getCount().equals("lw")){
			holder.countText.setText("LW");
		}
		holder.orderText.setText(position+1+"");
		Log.i("dddffff",majorArrayList.get(position).getDistance());
		if(majorArrayList.get(position).getDistance().equals("0")){
			holder.distanceText.setText("进球");
		}else{
			holder.distanceText.setText(majorArrayList.get(position).getDistance());
		}
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
