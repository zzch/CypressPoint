package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SelectSession1Adapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> nameArrayList = new ArrayList<String>();
	private LayoutInflater inflater;
	public SelectSession1Adapter(Context context,ArrayList<String> nameArrayList) {
		this.context = context;
		this.nameArrayList = nameArrayList;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return nameArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return nameArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.selection_session_item, null);
			holder = new Holder();
			holder.nameTextView = (TextView) convertView.findViewById(R.id.qiudong);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.nameTextView.setText(nameArrayList.get(position));
		return convertView;
	}
	class Holder{
		TextView nameTextView;
	}
}
