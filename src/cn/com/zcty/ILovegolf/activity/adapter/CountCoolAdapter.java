package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CountCoolAdapter extends BaseAdapter{
	private ArrayList<String> countCool;
	private ArrayList<String> countCoolResult;
	private Context context;
	private LayoutInflater inflater;
	public	CountCoolAdapter(Context context,ArrayList<String> countCool,ArrayList<String> countCoolResult){
		this.countCool = countCool;
		this.countCoolResult = countCoolResult;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return countCool.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return countCool.get(position);
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
			convertView = inflater.inflate(R.layout.countcool_item, null);
			holder.countCoolTextView = (TextView) convertView.findViewById(R.id.count_cool);
			holder.countCoolResultTextView = (TextView) convertView.findViewById(R.id.count_cool_result);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.countCoolTextView.setText(countCool.get(position));
		if(holder.countCoolTextView.getText().toString().equals("")){
			holder.countCoolResultTextView.setText("");
		}else{
			holder.countCoolResultTextView.setText(countCoolResult.get(position));
		}
		
		return convertView;
	}
	class Holder{
		TextView countCoolTextView;
		TextView countCoolResultTextView;
	}
}
