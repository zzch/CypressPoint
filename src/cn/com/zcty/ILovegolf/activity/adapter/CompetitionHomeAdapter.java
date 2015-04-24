package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.CompetitionHome;
import cn.com.zcty.ILovegolf.utils.ImageService;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CompetitionHomeAdapter extends BaseAdapter{
	private ArrayList<CompetitionHome> competitionHomes = new ArrayList<CompetitionHome>();
	private Context context;
	private LayoutInflater inflater;
	private Bitmap bitmap;
	private Boolean mBusy = false;
	public CompetitionHomeAdapter(Context context,ArrayList<CompetitionHome> competitionHomes) {
		this.context = context;
		this.competitionHomes = competitionHomes;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return competitionHomes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return competitionHomes.get(position);
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
			convertView = inflater.inflate(R.layout.competition_home_item, null);
			holder = new Holder();
			holder.userNameTextView = (TextView) convertView.findViewById(R.id.home_username);
			holder.matchNameTextView = (TextView) convertView.findViewById(R.id.home_name);
			holder.matchMoldTextView = (TextView) convertView.findViewById(R.id.home_mold);
			holder.numberPeopleTextView = (TextView) convertView.findViewById(R.id.home_pople_number);
			holder.image = (ImageView) convertView.findViewById(R.id.myself_head);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		//if(!competitionHomes.get(position).getBitmap().equals("null")){
			ImageService imageService = new ImageService();
			imageService.setBitmapByURL(competitionHomes.get(position).getBitmap(),holder.image,
					BitmapFactory.decodeResource(inflater.getContext().getResources(), R.drawable.lan), mBusy);
		//}
		holder.userNameTextView.setText(competitionHomes.get(position).getNickname()); 
		holder.matchNameTextView.setText(competitionHomes.get(position).getName()); 
		holder.matchMoldTextView.setText(competitionHomes.get(position).getRule()); 
		holder.numberPeopleTextView.setText(competitionHomes.get(position).getPlayers_count()); 
		return convertView;
	}
	class Holder{
		ImageView image;
		TextView userNameTextView;
		TextView matchNameTextView;
		TextView matchMoldTextView;
		TextView numberPeopleTextView;
	}
}
