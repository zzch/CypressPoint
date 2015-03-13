package cn.com.zcty.ILovegolf.exercise.adapter;

import java.util.List;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.Scorecards;
import cn.com.zcty.ILovegolf.model.Setcard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScoreCardGridViewAdapter extends BaseAdapter{
	private List<Scorecards> scorecarsArray;
	private List<Setcard> setcardsArray;
	private Context context;
	private LayoutInflater inflater_scorecars;
	private LayoutInflater inflater_setscorecard;
	private int count = 0;
	public ScoreCardGridViewAdapter(List<Scorecards> scorecarsArray,
			List<Setcard> setcardsArray, Context context) {
		super();
		this.scorecarsArray = scorecarsArray;
		this.setcardsArray = setcardsArray;
		this.context = context;
		inflater_scorecars = LayoutInflater.from(context);
		inflater_setscorecard = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		Log.i("name1", "1");
		return 18;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		SetscardsHolder setHolder;		
		Log.i("name1", "4");

			if(position%2==0||position==0){		
				ScardsHolder sHolder = null;
				if(convertView==null){
					sHolder = new ScardsHolder();
					convertView = inflater_scorecars.inflate(R.layout.scards, null);
					sHolder.numberRod = (TextView) convertView.findViewById(R.id.textView1);
					sHolder.par = (TextView) convertView.findViewById(R.id.textView2);
					sHolder.te = (TextView) convertView.findViewById(R.id.textView3);
					convertView.setTag(sHolder);
				}else{
					
				}				
				if(position==0){	
					sHolder = (ScardsHolder) convertView.getTag();
					if(scorecarsArray.get(position).getNumber()!=null){
						sHolder.numberRod.setText(scorecarsArray.get(position).getNumber());
						sHolder.par.setText(scorecarsArray.get(position).getPar());
						sHolder.te.setText(scorecarsArray.get(position).getDistance_from_hole_to_tee_box());
					}
					
					
				}else{
					//sHolder = (ScardsHolder) convertView.getTag();
					sHolder = new ScardsHolder();
					convertView = inflater_scorecars.inflate(R.layout.scards, null);
					sHolder.numberRod = (TextView) convertView.findViewById(R.id.textView1);
					sHolder.par = (TextView) convertView.findViewById(R.id.textView2);
					sHolder.te = (TextView) convertView.findViewById(R.id.textView3);
					sHolder.numberRod.setText(scorecarsArray.get(position/2).getNumber());
					sHolder.par.setText(scorecarsArray.get(position/2).getPar());
					sHolder.te.setText(scorecarsArray.get(position/2).getDistance_from_hole_to_tee_box());
				}
				return convertView;
			}else{
				
					setHolder = new SetscardsHolder();
					convertView = inflater_setscorecard.inflate(R.layout.setcards, null);
					setHolder.numberRod = (TextView) convertView.findViewById(R.id.textView1);
					setHolder.penalties = (TextView) convertView.findViewById(R.id.textView2);
					setHolder.putts = (TextView) convertView.findViewById(R.id.textView3);
					setHolder.te = (TextView) convertView.findViewById(R.id.textView4);
					setHolder.par = (TextView) convertView.findViewById(R.id.textView5);
					convertView.setTag(setHolder);
				
					//setHolder = (SetscardsHolder) convertView.getTag();
				
			if(setcardsArray.size()==0){
				
//				setHolder.numberRod.setText("");
//				setHolder.penalties.setText("");
//				setHolder.putts.setText("");
//				setHolder.te.setText("");
//				setHolder.par.setText("");
				
			}else{
				if(position==1){
					setHolder.numberRod.setText(setcardsArray.get(position).getRodNum());
					setHolder.penalties.setText(setcardsArray.get(position).getPenalties());
					setHolder.putts.setText(setcardsArray.get(position).getPutts());
					setHolder.te.setText(setcardsArray.get(position).getTe());
					setHolder.par.setText(setcardsArray.get(position).getPar());
				}else{
					setHolder.numberRod.setText(setcardsArray.get(position/2).getRodNum());
					setHolder.penalties.setText(setcardsArray.get(position/2).getPenalties());
					setHolder.putts.setText(setcardsArray.get(position/2).getPutts());
					setHolder.te.setText(setcardsArray.get(position/2).getTe());
					setHolder.par.setText(setcardsArray.get(position/2).getPar());
			}}
			return convertView;
			}
		
		
		//return convertView;
	}
	class ScardsHolder{
		TextView numberRod;
		TextView par;
		TextView te;
	}
	class SetscardsHolder{
		TextView numberRod;
		TextView par;
		TextView te;//Âë
		TextView penalties;//·£
		TextView putts;//ÍÆ¸Ë
	}
}
