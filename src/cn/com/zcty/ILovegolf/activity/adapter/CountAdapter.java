package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CountAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> grildArrayList = new ArrayList<String>();
	private String[] valueName = {"差点","排名","最好成绩","完整记分/总场次"};
	private LayoutInflater inflater;
	public CountAdapter(Context context,ArrayList<String> grildArrayList) {
		this.context = context;
		this.grildArrayList = grildArrayList;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return grildArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return grildArrayList.get(position);
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
			convertView = inflater.inflate(R.layout.count_item, null);
			holder = new Holder();
			holder.valueTextView = (TextView) convertView.findViewById(R.id.count_value);
			holder.valueNameTextView = (TextView) convertView.findViewById(R.id.count_value_name);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		if(grildArrayList.get(position).equals("null")){
			holder.valueTextView.setText("一");	
		}else{
			holder.valueTextView.setText(grildArrayList.get(position));
		}
		holder.valueNameTextView.setText(valueName[position]);
		return convertView;
	}
	class Holder{
		TextView valueTextView;
		TextView valueNameTextView;
	}
}
