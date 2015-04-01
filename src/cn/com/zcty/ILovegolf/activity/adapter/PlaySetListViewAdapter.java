package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.List;

import cn.com.zcty.ILovegolf.activity.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlaySetListViewAdapter extends BaseAdapter {
	private List<String> listPlaySet;
	private Context context;
	private LayoutInflater inflater;
	
	public PlaySetListViewAdapter(List<String> listPlaySet, Context context) {
		this.listPlaySet = listPlaySet;
		this.context = context;
		inflater = inflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listPlaySet.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listPlaySet.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView==null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.spinner_item, null);
			holder.textView = (TextView) convertView.findViewById(R.id.textView1);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.textView.setText(listPlaySet.get(position));
		return convertView;
	}

	public String result(int position){
		return listPlaySet.get(position);
		
	}

	class Holder{
		TextView textView;
	}
}
