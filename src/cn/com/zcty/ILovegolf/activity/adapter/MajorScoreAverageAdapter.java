package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.MajorStatisticsModel;

public class MajorScoreAverageAdapter extends BaseAdapter{
	private ArrayList<String> statisticsModels;
	private Context context;
	private String[] name;
	private LayoutInflater inflater;
	public MajorScoreAverageAdapter(Context context, ArrayList<String> statisticsModels,String[] name) {
		this.context = context;
		this.statisticsModels = statisticsModels;
		this.name = name;
		inflater = inflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return statisticsModels.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return statisticsModels.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Hold hold;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.average_item_1, null);
			hold = new Hold();
			hold.parScoreTextView = (TextView) convertView.findViewById(R.id.average_par_score);
			hold.parTextView = (TextView) convertView.findViewById(R.id.average_par);
			convertView.setTag(hold);
		}else{
			hold = (Hold) convertView.getTag();
		}
		hold.parScoreTextView.setText(statisticsModels.get(position));
	
		hold.parTextView.setText(name[position]);
	
		return convertView;
	}
	class Hold{
		TextView parScoreTextView;
		TextView parTextView;
	}
}
