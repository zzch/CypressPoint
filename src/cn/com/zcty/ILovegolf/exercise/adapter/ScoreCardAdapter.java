package cn.com.zcty.ILovegolf.exercise.adapter;


import java.util.List;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.Scorecards;
import cn.com.zcty.ILovegolf.model.TypeScorecard;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ScoreCardAdapter extends BaseAdapter  
 {
    private  Context context;
    private List<Scorecards> scorecards;
    		
	public ScoreCardAdapter(Context context,List<Scorecards> scorecards){
		
		this.context=context;
		this.scorecards=scorecards;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return scorecards.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return scorecards.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=View.inflate(context, R.layout.scorecard_item, null);
		TextView pol_number = (TextView) view.findViewById(R.id.pol_number);
		
		TextView pars = (TextView) view.findViewById(R.id.par);
		TextView distance_from_hole_to_tee_box = (TextView) view.findViewById(R.id.distance_from_hole_to_tee_box);
		TextView penalties = (TextView) view.findViewById(R.id.penalties);
		TextView scorecard_scr = (TextView) view.findViewById(R.id.scorecard_scr);
		TextView putt = (TextView) view.findViewById(R.id.putt);
		TextView yardage = (TextView) view.findViewById(R.id.yardage);
		TextView mingzhonglv = (TextView) view.findViewById(R.id.mingzhonglv);
		
		pol_number.setText(scorecards.get(position).getNumber()+"");
		Log.i("============history", "==========球洞"+scorecards.get(position).getNumber());
		pars.setText(scorecards.get(position).getPar()+"");
		distance_from_hole_to_tee_box.setText(scorecards.get(position).getDistance_from_hole_to_tee_box()+"");
		penalties.setText(scorecards.get(position).getPenalties()+"");
		scorecard_scr.setText(scorecards.get(position).getScore()+"");
		putt.setText(scorecards.get(position).getPutts()+"");
		yardage.setText(scorecards.get(position).getDriving_distance()+"");
		mingzhonglv.setText(scorecards.get(position).getDirection()+"");
		return view;	
	}
  
}
