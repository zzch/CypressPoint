package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QiuDongTypeAdapter extends BaseAdapter{
	private ArrayList<String> qiuType;
	private ArrayList<String> qiuTypeResult;
	private Context context;
	private LayoutInflater inflater;
	public	QiuDongTypeAdapter(Context context,ArrayList<String> countCool,ArrayList<String> countCoolResult){
		this.qiuType = countCool;
		this.qiuTypeResult = countCoolResult;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return qiuType.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return qiuType.get(position);
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
		holder.countCoolTextView.setText(qiuType.get(position));
		if(holder.countCoolTextView.getText().toString().equals("")){
			holder.countCoolResultTextView.setText("");
		}else{
			if(qiuType.get(position).equals("信天翁球")){
				holder.countCoolResultTextView.setText(qiuTypeResult.get(position)+"个");
			}else if(qiuType.get(position).equals("老鹰球")){
				holder.countCoolResultTextView.setText(qiuTypeResult.get(position)+"个");
			}else if(qiuType.get(position).equals("小鸟球")){
				holder.countCoolResultTextView.setText(qiuTypeResult.get(position)+"个");
			}else if(qiuType.get(position).equals("标准杆")){
				holder.countCoolResultTextView.setText(qiuTypeResult.get(position)+"个");
			}else if(qiuType.get(position).equals("柏忌球")){
				holder.countCoolResultTextView.setText(qiuTypeResult.get(position)+"个");
			}else if(qiuType.get(position).equals("双柏忌球")){
				holder.countCoolResultTextView.setText(qiuTypeResult.get(position)+"个");
			}
		}
		
		return convertView;
	}
	class Holder{
		TextView countCoolTextView;
		TextView countCoolResultTextView;
	}
}
