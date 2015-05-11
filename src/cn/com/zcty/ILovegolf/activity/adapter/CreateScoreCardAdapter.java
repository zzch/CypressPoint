package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.ScoreCardsMatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CreateScoreCardAdapter extends BaseAdapter{
	private ArrayList<ScoreCardsMatch> scoreCardsMatchs;
	private Context context;
	private LayoutInflater inflater;
	private String count[] = new String[18];
	public CreateScoreCardAdapter(Context context,ArrayList<ScoreCardsMatch> scoreCardsMatchs) {
		this.context = context;
		this.scoreCardsMatchs = scoreCardsMatchs;
		inflater = LayoutInflater.from(context);
		for(int i=0;i<18;i++){
			count[i] = (i+1)+"";
		}
	}

	@Override
	public int getCount() {
		return scoreCardsMatchs.size();
	}

	@Override
	public Object getItem(int position) {
		return scoreCardsMatchs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.score_item, null);
			holder = new Holder();
			holder.score_1RelativeLayout = 
					(RelativeLayout) convertView.findViewById(R.id.score_1);
			holder.scoreTextView = (TextView) convertView.findViewById(R.id.score);
			holder.scoreParTextView = (TextView) convertView.findViewById(R.id.score_par_1);
			holder.color_1 = (TextView) convertView.findViewById(R.id.score_t_1);
			holder.color_2 = (TextView) convertView.findViewById(R.id.score_t_2);
			holder.color_3 = (TextView) convertView.findViewById(R.id.score_t_3);
			holder.color_4 = (TextView) convertView.findViewById(R.id.score_t_4);
			holder.color_5 = (TextView) convertView.findViewById(R.id.score_t_5);

			holder.score_2RelativeLayout = 
					(RelativeLayout) convertView.findViewById(R.id.score_2);
			holder.score2TextView = (TextView) convertView.findViewById(R.id.score_two);
			holder.scorePar2TextView = (TextView) convertView.findViewById(R.id.score_par_2);
			holder.chengjiTextView = (TextView) convertView.findViewById(R.id.score_s_1);
			holder.distanceTextView = (TextView) convertView.findViewById(R.id.score_cool);
			holder.distanceimageView = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		if(scoreCardsMatchs.get(position).getScore().equals("null")){
			//如果score为null 则调用第一种布局否则调用第二种布局
			holder.score_1RelativeLayout.setVisibility(View.VISIBLE);
			holder.score_2RelativeLayout.setVisibility(View.GONE);
			holder.scoreTextView.setText(count[position]);
			holder.scoreParTextView
			.setText("Par"+scoreCardsMatchs.get(position).getPar());
			
			/*
			 * 根据有几个球来判断放球的位置
			 */
			switch (scoreCardsMatchs.get(position).getTeeboxs().size()) {
			case 1:
				holder.color_1.setText("");
				holder.color_2.setText("");
				holder.color_3.
				setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				holder.color_4.setText("");
				holder.color_5.setText("");
				break;
			case 2:
				holder.color_1.setText("");
				holder.color_2.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				holder.color_3.setText("");
				holder.color_4.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(1).getDistance_from_hole());
				holder.color_5.setText("");
				break;
			case 3:
				holder.color_1.setText("");
				holder.color_2.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				holder.color_3.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(1).getDistance_from_hole());
				holder.color_4.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(2).getDistance_from_hole());
				holder.color_5.setText("");
				break;
			case 4:
				holder.color_1.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				holder.color_2.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(1).getDistance_from_hole());
				holder.color_3.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(2).getDistance_from_hole());
				holder.color_4.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(3).getDistance_from_hole());
				holder.color_5.setText("");
				break;
			case 5:
				holder.color_1.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				holder.color_2.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(1).getDistance_from_hole());
				holder.color_3.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(2).getDistance_from_hole());
				holder.color_4.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(3).getDistance_from_hole());
				holder.color_5.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(4).getDistance_from_hole());
				break;

			}			
			

		}else{
			//第二种布局
			holder.score_2RelativeLayout.setVisibility(View.VISIBLE);
			holder.score_1RelativeLayout.setVisibility(View.GONE);			
			holder.score2TextView.setText(count[position]);
			holder.scorePar2TextView
			.setText("Par"+scoreCardsMatchs.get(position).getPar());
			holder.chengjiTextView.setText(
					scoreCardsMatchs.get(position).getScore());
			holder.distanceTextView.setText(
					scoreCardsMatchs.get(position).getDriving_distance());
			
		}
		return convertView;
	}
	class Holder{
		RelativeLayout score_1RelativeLayout;
		RelativeLayout score_2RelativeLayout;
		TextView scoreTextView;
		TextView scoreParTextView;
		TextView color_1;
		TextView color_2;
		TextView color_3;
		TextView color_4;
		TextView color_5;

		TextView score2TextView;
		TextView scorePar2TextView;
		TextView chengjiTextView;
		TextView distanceTextView;
		ImageView distanceimageView;

	}
}