package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QiuDongAdapter extends BaseAdapter{
	private ArrayList<String> qiuDong;
	private ArrayList<String> qiuDongResult;
	private Context context;
	private LayoutInflater inflater;
	public	QiuDongAdapter(Context context,ArrayList<String> countCool,ArrayList<String> countCoolResult){
		this.qiuDong = countCool;
		this.qiuDongResult = countCoolResult;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return qiuDong.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return qiuDong.get(position);
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
		holder.countCoolTextView.setText(qiuDong.get(position));
		if(holder.countCoolTextView.getText().toString().equals("")){
			holder.countCoolResultTextView.setText("");
		}else{
			if(qiuDong.get(position).equals("3杆洞")){
				holder.countCoolResultTextView.setText(qiuDongResult.get(position)+"个");
			}else if(qiuDong.get(position).equals("4杆洞")){
				holder.countCoolResultTextView.setText(qiuDongResult.get(position)+"个");
			}else if(qiuDong.get(position).equals("5杆")){
				holder.countCoolResultTextView.setText(qiuDongResult.get(position)+"个");
			}
		}
		
		return convertView;
	}
	class Holder{
		TextView countCoolTextView;
		TextView countCoolResultTextView;
	}
}
