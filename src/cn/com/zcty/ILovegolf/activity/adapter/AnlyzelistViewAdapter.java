package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AnlyzelistViewAdapter extends BaseAdapter {
	
	private ArrayList<String> scoresArrayList;
	private ArrayList<String> scoregrid;
	private LayoutInflater inflater;
	private Context context;
	public AnlyzelistViewAdapter(Context context,ArrayList<String> scoresArrayList,ArrayList<String> scoregrid) {
		this.context = context;
		this.scoresArrayList = scoresArrayList;
		this.scoregrid = scoregrid;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return scoresArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return scoresArrayList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if(convertView==null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.analyze_item, null);
			holder.textView = (TextView) convertView.findViewById(R.id.text_zhi);
			holder.textViewName_1 = (TextView) convertView.findViewById(R.id.text_analyze);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.textView.setText(scoresArrayList.get(position));
		holder.textViewName_1.setText(scoregrid.get(position));
		return convertView;
	}
	class Holder {
		TextView textView;
		TextView textViewName_1;
	}
}
