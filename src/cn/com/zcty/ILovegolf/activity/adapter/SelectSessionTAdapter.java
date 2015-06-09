package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectSessionTAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> nameArrayList = new ArrayList<String>();
	private LayoutInflater inflater;
	private String tiTai[]={"红色","白色","蓝色","黑色","金色"};
	public SelectSessionTAdapter(Context context,ArrayList<String> nameArrayList) {
		this.context = context;
		this.nameArrayList = nameArrayList;
		inflater = LayoutInflater.from(context);
		Log.i("lovegolf",nameArrayList.size()+""+nameArrayList);
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
			convertView = inflater.inflate(R.layout.selection_t_item, null);
			holder = new Holder();
			holder.nameTextView = (TextView) convertView.findViewById(R.id.qiudong);
			holder.image = (ImageView) convertView.findViewById(R.id.titai);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		//holder.nameTextView.setText(nameArrayList.get(position));
		Log.i("sadfsd",nameArrayList.size()+""+nameArrayList);
		if(nameArrayList.get(position).equals("red")){
			holder.image.setBackgroundResource(R.drawable.e_red);
			holder.nameTextView.setText("红色");
		}else if(nameArrayList.get(position).equals("white")){
			holder.image.setBackgroundResource(R.drawable.e_white);
			holder.nameTextView.setText("白色");
		}else if(nameArrayList.get(position).equals("blue")){
			holder.image.setBackgroundResource(R.drawable.e_blue);
			holder.nameTextView.setText("蓝色");
		}else if(nameArrayList.get(position).equals("black")){
			holder.image.setBackgroundResource(R.drawable.e_black);
			holder.nameTextView.setText("黑色");
		}else{
			holder.image.setBackgroundResource(R.drawable.e_gold);
			holder.nameTextView.setText("金色");
		}
		return convertView;
	}
	class Holder{
		TextView nameTextView;
		ImageView image;
	}
}
