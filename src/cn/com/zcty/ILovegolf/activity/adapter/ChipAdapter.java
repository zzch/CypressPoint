package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.Chip;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChipAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Chip> chipArrayList = new ArrayList<Chip>();
	private LayoutInflater inflater;
	public ChipAdapter(Context context,ArrayList<Chip> chipArrayList) {
		this.context = context;
		this.chipArrayList = chipArrayList;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return chipArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return chipArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView==null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.child_item, null);
			holder.countTextView = (TextView) convertView.findViewById(R.id.chip_count);
			holder.distanceTextView = (TextView) convertView.findViewById(R.id.chip_distance);
			holder.puttsDistanceTextView = (TextView) convertView.findViewById(R.id.chip_putts_distance);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.countTextView.setText(position+1+"");
		holder.distanceTextView.setText(chipArrayList.get(position).getDistance_from_hole());
		holder.puttsDistanceTextView.setText(chipArrayList.get(position).getPutt_length());
		return convertView;
	}
	class Holder{
		TextView countTextView;
		TextView distanceTextView;
		TextView puttsDistanceTextView;
	}
}
