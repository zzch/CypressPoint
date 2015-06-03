package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import com.baidu.location.r;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.Clubs;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClubsAdapter extends BaseAdapter{
	private ArrayList<Clubs> clubsArrayList = new ArrayList<Clubs>();
	private Context context;
	private LayoutInflater inflater;
	public ClubsAdapter(Context context,ArrayList<Clubs> clubsArrayList) {
		this.context = context;
		this.clubsArrayList = clubsArrayList;
		inflater = inflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return clubsArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return clubsArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.qiugan_item, null);
			holder = new Holder();
			holder.pingjun_text = (TextView) convertView.findViewById(R.id.pingjun_text);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.qiugan_cool);
			holder.userTextView = (TextView) convertView.findViewById(R.id.qiugan_jiqiu_cishu);
			holder.average_lengthTextView = (TextView) convertView.findViewById(R.id.qiugan_jiqiu_distance);
			holder.minimum_lengthTextView = (TextView) convertView.findViewById(R.id.qiugan_jiqiu_min);
			holder.maximum_lengthTextView = (TextView) convertView.findViewById(R.id.qiugan_jiqiu_max);
			holder.less_than_average_lengthTextView = (TextView) convertView.findViewById(R.id.qiugan_jiqiu_ci);
			holder.greater_than_average_lengthTextView = (TextView) convertView.findViewById(R.id.qiugan_jiqiu_ci_2);
			holder.averageTextView = (TextView) convertView.findViewById(R.id.qiugan_jiqiu_average);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.nameTextView.setText(clubsArrayList.get(position).getName());
		holder.userTextView.setText("击球"+clubsArrayList.get(position).getUsers()+"次");
		if(clubsArrayList.get(position).getAverage_length().equals("0")){
			holder.averageTextView.setText("");
			holder.averageTextView.setBackgroundColor(0x00000000);
			holder.less_than_average_lengthTextView.setText("");
			holder.greater_than_average_lengthTextView.setText("");
			holder.pingjun_text.setText("");
		}else{
			holder.averageTextView.setBackgroundColor(0xff222222);
			holder.averageTextView.setText(clubsArrayList.get(position).getAverage_length());
			holder.less_than_average_lengthTextView.setText(clubsArrayList.get(position).getLess_than_average_length()+"次");
			holder.greater_than_average_lengthTextView.setText(clubsArrayList.get(position).getGreater_than_average_length()+"次");
			holder.pingjun_text.setText("平均");
		}
		holder.average_lengthTextView.setText("平均距离   "+clubsArrayList.get(position).getAverage_length());
		
		if(clubsArrayList.get(position).getMinimum_length().equals("null")){
			holder.minimum_lengthTextView.setText("一");	
		}else{
			holder.minimum_lengthTextView.setText(clubsArrayList.get(position).getMinimum_length());
		}
		if(clubsArrayList.get(position).getMaximum_length().equals("null")){
			holder.maximum_lengthTextView.setText("一");
		}else{	
			holder.maximum_lengthTextView.setText(clubsArrayList.get(position).getMaximum_length());
		}
		
		
		return convertView;
	}
	class Holder{
		TextView nameTextView;
		TextView userTextView;
		TextView average_lengthTextView;
		TextView minimum_lengthTextView;
		TextView maximum_lengthTextView;
		TextView less_than_average_lengthTextView;
		TextView greater_than_average_lengthTextView;
		TextView averageTextView;
		TextView pingjun_text;
		
	}
}
