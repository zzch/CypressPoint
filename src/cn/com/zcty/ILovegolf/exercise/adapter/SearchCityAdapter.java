package cn.com.zcty.ILovegolf.exercise.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.exercise.adapter.StatisticAdapter.Holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchCityAdapter extends BaseAdapter {
	
	private ArrayList<String> citys_name;
	private List<String> citys_address;
	private Context context;
	private LayoutInflater inflater;
	
	

	public SearchCityAdapter(ArrayList<String> citys_name,List<String> citys_address,Context context) {
		this.citys_address = citys_address;
		this.citys_name = citys_name;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return citys_name.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return citys_name.get(position);
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
			convertView = inflater.inflate(R.layout.simple_city, null);
			holder.pitchname = (TextView) convertView.findViewById(R.id.pitchname);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			convertView.setTag(holder);
	   }else{
		   holder = (Holder) convertView.getTag();
	   }
		holder.pitchname.setText(citys_name.get(position));
		holder.address.setText(citys_address.get(position));
		return convertView;

		
	}
	
   class Holder{
		TextView pitchname;
		TextView address;
	}	
	 
	
}
	
