package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.ScoreCardsMatch;

public class CreateScoreCardAdapter extends BaseAdapter{
	private ArrayList<ScoreCardsMatch> scoreCardsMatchs;
	private ArrayList<String> scoreCardsMatchs_2;
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<String> color;
	private String count[] = new String[18];
	public CreateScoreCardAdapter(Context context,ArrayList<ScoreCardsMatch> scoreCardsMatchs,ArrayList<String> color,ArrayList<String> scoreCardsMatchs_2) {
		this.context = context;
		this.color = color;
		this.scoreCardsMatchs = scoreCardsMatchs;
		this.scoreCardsMatchs_2 = scoreCardsMatchs_2;
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


	@SuppressLint("NewApi")
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
			holder.score_distance_1 = (TextView) convertView.findViewById(R.id.score_distance_1);
			holder.color_1 = (TextView) convertView.findViewById(R.id.score_t_1);
			holder.color_2 = (TextView) convertView.findViewById(R.id.score_t_2);
			holder.color_3 = (TextView) convertView.findViewById(R.id.score_t_3);
			holder.color_4 = (TextView) convertView.findViewById(R.id.score_t_4);
			holder.color_5 = (TextView) convertView.findViewById(R.id.score_t_5);
            holder.t_score_1= (ImageView) convertView.findViewById(R.id.t_score_1);
			holder.score_2RelativeLayout = 
					(RelativeLayout) convertView.findViewById(R.id.score_2);
			holder.score2TextView = (TextView) convertView.findViewById(R.id.score_two);
			holder.scorePar2TextView = (TextView) convertView.findViewById(R.id.score_par_2);
			holder.score_distance_2 = (TextView) convertView.findViewById(R.id.score_distance_2);
			holder.t_score_2= (ImageView) convertView.findViewById(R.id.t_score_2);
			holder.chengjiTextView = (TextView) convertView.findViewById(R.id.score_s_1);
			holder.distanceTextView = (TextView) convertView.findViewById(R.id.score_cool);
			holder.distanceimageView = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		if(!scoreCardsMatchs_2.get(position).equals("null")){
			
			holder.score_distance_2.setText(scoreCardsMatchs_2.get(position));
		}
		if(scoreCardsMatchs.get(position).getScore().equals("null")){
			//如果score为null 则调用第一种布局否则调用第二种布局
			holder.score_1RelativeLayout.setVisibility(View.VISIBLE);
			holder.score_2RelativeLayout.setVisibility(View.GONE);
			holder.scoreTextView.setText(count[position]);
			holder.scoreParTextView
			.setText(scoreCardsMatchs.get(position).getPar());
			
			/*
			 * 根据有几个球来判断放球的位置
			 */
			

			switch (scoreCardsMatchs.get(position).getCount()) {
			case 1:
				holder.color_1.setText("");
				holder.color_2.setText("");
				holder.color_3.
				setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				
				holder.score_distance_1.
				setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				color(position, 0, holder.color_3,holder.score_distance_1,holder.t_score_1);	
				
				holder.color_4.setText("");
				holder.color_5.setText("");
				
				break;
			case 2:
				holder.color_1.setText("");
				holder.color_2.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				color(position, 0, holder.color_2,holder.score_distance_1,holder.t_score_1);	
				holder.color_3.setText("");
				holder.color_4.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(1).getDistance_from_hole());
				
				color(position, 1, holder.color_4,holder.score_distance_1,holder.t_score_1);	
				holder.color_5.setText("");
				break;
			case 3:
				holder.color_1.setText("");
				holder.color_2.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				color(position, 0, holder.color_2,holder.score_distance_1,holder.t_score_1);	
				
				holder.color_3.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(1).getDistance_from_hole());
				color(position, 1, holder.color_3,holder.score_distance_1,holder.t_score_1);	
				holder.color_4.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(2).getDistance_from_hole());
				color(position, 2, holder.color_4,holder.score_distance_1,holder.t_score_1);	
				holder.color_5.setText("");
				break;
			case 4:
				holder.color_1.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				color(position, 0, holder.color_1,holder.score_distance_1,holder.t_score_1);	
				holder.color_2.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(1).getDistance_from_hole());
				color(position, 1, holder.color_2,holder.score_distance_1,holder.t_score_1);	
				holder.color_3.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(2).getDistance_from_hole());
				color(position, 2, holder.color_3,holder.score_distance_1,holder.t_score_1);	
				holder.color_4.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(3).getDistance_from_hole());
				color(position, 3, holder.color_4,holder.score_distance_1,holder.t_score_1);	
				holder.color_5.setText("");
				break;
			case 5:
				holder.color_1.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				color(position, 0, holder.color_1,holder.score_distance_1,holder.t_score_1);	
				holder.color_2.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(1).getDistance_from_hole());
				color(position, 1, holder.color_2,holder.score_distance_1,holder.t_score_1);	
				holder.color_3.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(2).getDistance_from_hole());
				color(position, 2, holder.color_3,holder.score_distance_1,holder.t_score_1);	
				holder.color_4.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(3).getDistance_from_hole());
				color(position, 3, holder.color_4,holder.score_distance_1,holder.t_score_1);	
				holder.color_5.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(4).getDistance_from_hole());
				color(position, 4, holder.color_5,holder.score_distance_1,holder.t_score_1);	
				break;

			}
			
		}else{
			//第二种布局
			holder.score_2RelativeLayout.setVisibility(View.VISIBLE);
			holder.score_1RelativeLayout.setVisibility(View.GONE);		
			color(position,holder.t_score_2);
	        
			holder.score2TextView.setText(count[position]);
			holder.scorePar2TextView
			.setText(scoreCardsMatchs.get(position).getPar());
			holder.chengjiTextView.setText(
					scoreCardsMatchs.get(position).getScore());
			int par = Integer.parseInt(scoreCardsMatchs.get(position).getPar());
			int score = Integer.parseInt(scoreCardsMatchs.get(position).getScore());
			if(score-par>=2){
				holder.chengjiTextView.setBackgroundResource(R.drawable.jfk_dayu2);
			}else if(score-par==1){
				holder.chengjiTextView.setBackgroundResource(R.drawable.jfk_dayu1);
			}else if(score-par==0){
				holder.chengjiTextView.setBackground(null);
			}else if(score-par==-1){
				holder.chengjiTextView.setBackgroundResource(R.drawable.jfk_xiaoyu);
			}else if(score-par<-1){
				holder.chengjiTextView.setBackgroundResource(R.drawable.xiaoyu2);
			}
			if(scoreCardsMatchs.get(position).getDirection().equals("pure")){
				holder.distanceimageView.setImageResource(R.drawable.jfk_zhong_icon);
			}else if(scoreCardsMatchs.get(position).getDirection().equals("hook")){
				holder.distanceimageView.setImageResource(R.drawable.jfk_zuo_icon);
			}else{
				holder.distanceimageView.setImageResource(R.drawable.jfk_you_icon);
			}
			
			//holder.distanceTextView.setTextColor(0x80ff961d);
			holder.distanceTextView.setText(
					scoreCardsMatchs.get(position).getDriving_distance()+"码");
			
		}
		return convertView;
	}
	/*
	 * 添加球的颜色
	 */
	public void color(int position,int color,TextView textView,TextView y,ImageView t_score1){
		Log.i("sdfasdf",scoreCardsMatchs.get(position).getTeeboxs().get(color).getColor()+"1");
		textView.setTextColor(Color.rgb(122, 122, 122));
		if(scoreCardsMatchs.get(position).getTeeboxs().get(color).getColor().equals("red")){
			if(scoreCardsMatchs.get(position).getTeeboxs().get(color).getUsed().equals("true")){
				textView.setBackgroundResource(R.drawable.jfk_hong);
				textView.setTextColor(Color.WHITE);
				
				y.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(0).getDistance_from_hole());
				//d.setText(scoreCardsMatchs.get(position).getTeeboxs().get(0).getDistance_from_hole());
				t_score1.setBackgroundResource(R.drawable.xiao_red);
				
				
			}else{
				textView.setBackgroundResource(R.drawable.jfk_hong_zhihui);
				textView.setTextColor(Color.WHITE);
			}
		}else if(scoreCardsMatchs.get(position).getTeeboxs().get(color).getColor().equals("white")){
			if(scoreCardsMatchs.get(position).getTeeboxs().get(color).getUsed().equals("true")){
				textView.setBackgroundResource(R.drawable.jfk_bai);
				textView.setTextColor(Color.BLACK);
				
				y.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(1).getDistance_from_hole());
				//d.setText(scoreCardsMatchs.get(position).getTeeboxs().get(1).getDistance_from_hole());
				t_score1.setBackgroundResource(R.drawable.xiao_bai);
				
			}else{
				textView.setBackgroundResource(R.drawable.jfk_bai_zhihui);
				textView.setTextColor(Color.BLACK);
			}
			
		}else if(scoreCardsMatchs.get(position).getTeeboxs().get(color).getColor().equals("blue")){
			if(scoreCardsMatchs.get(position).getTeeboxs().get(color).getUsed().equals("true")){
				textView.setBackgroundResource(R.drawable.jfk_lan);
				textView.setTextColor(Color.WHITE);
				
				y.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(2).getDistance_from_hole());
				//d.setText(scoreCardsMatchs.get(position).getTeeboxs().get(2).getDistance_from_hole());
				t_score1.setBackgroundResource(R.drawable.xiao_lan);
				
			}else{
				textView.setBackgroundResource(R.drawable.jfk_lan_zhihui);
				textView.setTextColor(Color.WHITE);
			}
			
		}else if(scoreCardsMatchs.get(position).getTeeboxs().get(color).getColor().equals("black")){
			if(scoreCardsMatchs.get(position).getTeeboxs().get(color).getUsed().equals("true")){
				textView.setBackgroundResource(R.drawable.jfk_hei);
				textView.setTextColor(Color.WHITE);
				
				y.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(3).getDistance_from_hole());
				//d.setText(scoreCardsMatchs.get(position).getTeeboxs().get(3).getDistance_from_hole());
				t_score1.setBackgroundResource(R.drawable.xiao_hei);
			
			}else{
				textView.setBackgroundResource(R.drawable.jfk_hei_zhihui);
				textView.setTextColor(Color.WHITE);
			}
			
		}else if(scoreCardsMatchs.get(position).getTeeboxs().get(color).getColor().equals("gold")){
			if(scoreCardsMatchs.get(position).getTeeboxs().get(color).getUsed().equals("true")){
				textView.setBackgroundResource(R.drawable.jinsess);
				textView.setTextColor(Color.WHITE);
				y.setText(scoreCardsMatchs.get(position).getTeeboxs()
						.get(4).getDistance_from_hole());
				//d.setText(scoreCardsMatchs.get(position).getTeeboxs().get(4).getDistance_from_hole());
				t_score1.setBackgroundResource(R.drawable.xiao_jin);
				
				
			}else{
				textView.setBackgroundResource(R.drawable.jfk_jin_zhihui);
				textView.setTextColor(Color.WHITE);
			}
			
		}
	}
	
	
	/*
	 * 添加球的颜色
	 */
	public void color(int position,ImageView t_score2){
		
		if(color.get(position).equals("red")){		
			t_score2.setBackgroundResource(R.drawable.xiao_red);
			
		}else if(color.get(position).equals("white")){
			t_score2.setBackgroundResource(R.drawable.xiao_bai);
			
		}else if(color.get(position).equals("blue")){
			t_score2.setBackgroundResource(R.drawable.xiao_lan);
			
		}else if(color.get(position).equals("black")){
			t_score2.setBackgroundResource(R.drawable.xiao_hei);
			
		}else if(color.get(position).equals("gold")){
			t_score2.setBackgroundResource(R.drawable.xiao_jin);
			
		}
	}
	class Holder{
		RelativeLayout score_1RelativeLayout;
		RelativeLayout score_2RelativeLayout;
		TextView scoreTextView;
		TextView scoreParTextView;
		TextView score_distance_1;
		TextView color_1;
		TextView color_2;
		TextView color_3;
		TextView color_4;
		TextView color_5;
		ImageView t_score_1;
		ImageView t_score_2;

		TextView score2TextView;
		TextView score_distance_2;
		TextView scorePar2TextView;
		TextView chengjiTextView;
		TextView distanceTextView;
		ImageView distanceimageView;
		

	}
}
