package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.List;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.Scorecards;
import cn.com.zcty.ILovegolf.model.Setcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MajorScoreCardGridViewAdapter extends BaseAdapter{
	private List<Scorecards> scorecarsArray;
	private List<Setcard> setcardsArray;
	private Context context;
	private LayoutInflater inflater;



	public MajorScoreCardGridViewAdapter(List<Scorecards> scorecarsArray,
			List<Setcard> setcardsArray, Context context) {
		super();
		this.scorecarsArray = scorecarsArray;
		this.setcardsArray = setcardsArray;
		this.context = context;
		inflater = LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		Log.i("name1", "1");
		return 36;
	}

	@Override
	public Object getItem(int position) {
		
		Log.i("name1", "2");

		if(position%2==0||position==0){		
			if(position==0){
				return scorecarsArray.get(position);
			}else{				
				return scorecarsArray.get(position/2);
			}
		}else{
		if(setcardsArray.get(position/2)==null||setcardsArray.get(0)==null){
			return "";
		}else{
			if(position==1){
			return setcardsArray.get(position);
			}else{
			return setcardsArray.get(position/2);
		}}
		}
	}

	@Override
	public long getItemId(int position) {
		Log.i("name1", "3");

		return position;
	}

	public Setcard getResult(int position){
		
		return setcardsArray.get(position/2);
		
	}
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SetscardsHolder holder=null;
		View view = convertView;
		if(view==null){		
			Log.i("position", position+"zhou");
			view = inflater.inflate(R.layout.setcards, null);
			holder = new SetscardsHolder();
			holder.numberRod = (TextView) view.findViewById(R.id.numberRod);
			holder.penalties = (TextView) view.findViewById(R.id.penalties);
			holder.putts = (TextView) view.findViewById(R.id.putts);
			holder.par = (TextView) view.findViewById(R.id.par);
			holder.par_p = (TextView) view.findViewById(R.id.par_p);
			holder.distance_count = (TextView) view.findViewById(R.id.distance_count);
			holder.distance_image = (ImageView) view.findViewById(R.id.distance_image);
			holder.distance = (TextView) view.findViewById(R.id.distance);
			holder.cool_image = (ImageView) view.findViewById(R.id.cool_image);
			holder.cool = (TextView) view.findViewById(R.id.cool);
			holder.image =  (ImageView) view.findViewById(R.id.imageView_1);
			holder.distance_y = (TextView) view.findViewById(R.id.distance_y);
			
			view.setTag(holder);
			
			
		}else{
			holder = (SetscardsHolder) view.getTag();
			Log.i("position", position+"");
		}
		
		if(position%2==0||position==0){
			Log.i("position", position+"f");
			holder.image.setVisibility(View.GONE);
			holder.numberRod.setText(scorecarsArray.get(position/2).getNumber());
			holder.par.setText(scorecarsArray.get(position/2).getPar());
			holder.distance_count.setText(scorecarsArray.get(position/2).getDistance_from_hole_to_tee_box());		
			holder.par_p.setText("P");
			holder.distance_y.setText("Y");
			holder.distance_image.setVisibility(View.GONE);
			Log.i("teecolor", scorecarsArray.get(position/2).getTee_box_color());
			if(scorecarsArray.get(position/2).getTee_box_color().equals("white")){
				Log.i("teecolor", "baise");
				holder.numberRod.setBackgroundResource(R.drawable.baise);
			}else if(scorecarsArray.get(position/2).getTee_box_color().equals("black")){
				holder.numberRod.setBackgroundResource(R.drawable.hei);
			}else if(scorecarsArray.get(position/2).getTee_box_color().equals("yellow")){
				holder.numberRod.setBackgroundResource(R.drawable.huang);
			}else if(scorecarsArray.get(position/2).getTee_box_color().equals("red")){
				holder.numberRod.setBackgroundResource(R.drawable.hong);
			}else{
				holder.numberRod.setBackgroundResource(R.drawable.lan);
			}
			//holder.numberRod.setBackgroundResource(R.drawable.e_card_yuan);
			holder.cool_image.setVisibility(View.GONE);
			holder.putts.setText("");
			holder.penalties.setText("");
			holder.distance.setText("");
			holder.cool.setText("");
		}else{
			
			if(setcardsArray.get(position/2).getRodNum()!=null&&!(setcardsArray.get(position/2).getRodNum().equals("null"))){
				//holder.distance_image.setVisibility(View.VISIBLE);
				holder.cool_image.setVisibility(View.GONE);
				holder.distance_image.setVisibility(View.GONE);
				holder.image.setVisibility(View.GONE);
				holder.numberRod.setBackground(null);
				//holder.numberRod.setTextSize(60);
				holder.penalties.setTextColor(Color.RED);				
				holder.numberRod.setText(setcardsArray.get(position/2).getRodNum());
				if(setcardsArray.get(position/2).getPenalties()==null){
					holder.penalties.setText("0");
				}else{
				holder.penalties.setText(setcardsArray.get(position/2).getPenalties());
				}
				if(setcardsArray.get(position/2).getPutts()==null){
					holder.putts.setText("0");

				}else{					
					holder.putts.setText(setcardsArray.get(position/2).getPutts());
				}
				holder.cool.setText("");
				holder.distance.setText("");
				holder.par_p.setText("");
				holder.distance_y.setText("");
				holder.par.setText("");
				holder.distance_count.setText("");
				//holder.cool_image.setVisibility(View.VISIBLE);
				/*if(setcardsArray.get(position/2).getPar().equals("命中")){
					holder.cool_image.setImageResource(R.drawable.juzhong);
				}else if(setcardsArray.get(position/2).getPar().equals("左侧")){
					holder.cool_image.setImageResource(R.drawable.lelf);
				}else{
					holder.cool_image.setImageResource(R.drawable.right);
				}*/
			}else if(setcardsArray.size()<36){
				holder.image.setVisibility(View.VISIBLE);
				holder.cool_image.setVisibility(View.GONE);
				holder.distance_image.setVisibility(View.GONE);
				holder.numberRod.setBackground(null);
				holder.numberRod.setText("");
				holder.penalties.setText("");
				holder.putts.setText("");
				holder.par.setText("");
				holder.distance.setText("");
				holder.par_p.setText("");
				holder.distance_y.setText("");
				holder.par.setText("");
				holder.distance_count.setText("");
				holder.cool.setText("");
			}
		}
		return view;
	
	}

	class SetscardsHolder{
		TextView numberRod;
		TextView penalties;//罚
		TextView putts;//推杆
		ImageView image;
		ImageView distance_image;
		TextView distance_count;
		ImageView cool_image;
		TextView cool;
		TextView par;
		TextView par_p;
		TextView distance;
		TextView distance_y;
	}
	
}
