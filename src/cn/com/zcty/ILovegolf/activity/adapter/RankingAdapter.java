package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import com.baidu.location.aq.c;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.RankingActivity;
import cn.com.zcty.ILovegolf.activity.view.RankingStatics;
import cn.com.zcty.ILovegolf.model.Ranking;
import cn.com.zcty.ILovegolf.tools.CircleImageView;
import cn.com.zcty.ILovegolf.utils.ImageService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RankingAdapter extends BaseAdapter{
	private ArrayList<Ranking> rankings = new ArrayList<Ranking>();
	private Context context;
	private LayoutInflater inflater;
	
	public RankingAdapter(Context context,ArrayList<Ranking> rankings) {
		this.context = context;
		this.rankings = rankings;
		inflater = LayoutInflater.from(context);
		}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rankings.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rankings.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RankHolder holder = null;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.ranking_item, null);
			holder = new RankHolder();
			holder.positionTextView = (TextView) convertView.findViewById(R.id.ranking_count);
			holder.nameCircleImageView = (CircleImageView) convertView.findViewById(R.id.ranking_head_image);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.ranking_username);
			holder.totalTextView = (TextView) convertView.findViewById(R.id.score);
			holder.scheduleTextView = (TextView) convertView.findViewById(R.id.count);
			holder.image_rank = (ImageView) convertView.findViewById(R.id.image_rank);
			holder.color_myself = (RelativeLayout) convertView.findViewById(R.id.color_myself);
			convertView.setTag(holder);
		}else{
			holder = (RankHolder) convertView.getTag();
			
		}
		   Log.i("rankings",position+"");
		   
		 	if(rankings.get(position).getPosition().equals("null")){
		 		
		 		holder.positionTextView.setText("一");
		 	}else{
		 		
		 		//holder.image_rank.setVisibility(View.GONE);
		 		holder.positionTextView.setText(rankings.get(position).getPosition());
		 	}
		 	ImageService imageService = new ImageService();
		 	if(rankings.size()>0){
		 	
		 	imageService.setBitmapByURL
		 	(rankings.get(position).getPortrait(),
		 			holder.nameCircleImageView,
		 			BitmapFactory.decodeResource(inflater.getContext().getResources(), R.drawable.hugh), true);
		 	holder.nameTextView.setText(rankings.get(position).getNickname());
		 	if(rankings.get(position).getTotal().equals("null")){
		 		holder.totalTextView.setText("一");
		 		holder.totalTextView.setTextSize(20);
		 	}else{
		 		holder.totalTextView.setText(rankings.get(position).getTotal());
		 	}
		 	holder.scheduleTextView.setText(rankings.get(position).getRecorded_scorecards_count()+"/18");
		 	if(rankings.get(position).getSelf().equals("true")){
		 		holder.color_myself.setBackgroundColor(0xffffffff);
		 		holder.totalTextView.setTextColor(0xffff961d);
		 		holder.nameTextView.setTextColor(0xffff961d);
		 		holder.scheduleTextView.setTextColor(0xffff961d);
		 		holder.positionTextView.setTextColor(0xffff961d);
			}else{
				holder.color_myself.setBackgroundColor(0xffffff);
				holder.nameTextView.setTextColor(0xff222222);
				holder.totalTextView.setTextColor(0xff222222);
		 		holder.scheduleTextView.setTextColor(0xff222222);
		 		holder.positionTextView.setTextColor(0xff222222);
			}
			
		 	}
		 	return convertView;
	}
	class RankHolder{
		TextView positionTextView;
		CircleImageView nameCircleImageView;
		TextView nameTextView;
		TextView scheduleTextView;
		TextView totalTextView;
		ImageView image_rank;
		RelativeLayout color_myself;
		
	}
}
