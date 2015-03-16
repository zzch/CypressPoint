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
	private LayoutInflater inflater;
	
	public ScoreCardGridViewAdapter(List<Scorecards> scorecarsArray,
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


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SetscardsHolder holder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.setcards, null);
			holder = new SetscardsHolder();
			holder.numberRod = (TextView) convertView.findViewById(R.id.numberRod);
			holder.penalties = (TextView) convertView.findViewById(R.id.textView2);
			holder.putts = (TextView) convertView.findViewById(R.id.textView3);
			holder.par = (TextView) convertView.findViewById(R.id.textView4);
			holder.te = (TextView) convertView.findViewById(R.id.textView5);
			convertView.setTag(holder);
		}else{
			holder = (SetscardsHolder) convertView.getTag();
		}
		if(position%2==0){
			holder.numberRod.setText(scorecarsArray.get(position/2).getNumber());
			holder.par.setText(scorecarsArray.get(position/2).getPar());
			holder.te.setText(scorecarsArray.get(position/2).getDistance_from_hole_to_tee_box());		
			holder.penalties.setVisibility(View.INVISIBLE);
			holder.putts.setVisibility(View.INVISIBLE);
		}else{
			if(setcardsArray.size()>0){
				holder.penalties.setVisibility(View.VISIBLE);
				holder.putts.setVisibility(View.VISIBLE);
				holder.numberRod.setText(setcardsArray.get(position/2).getRodNum());
				holder.penalties.setText(setcardsArray.get(position/2).getPenalties());
				holder.putts.setText(setcardsArray.get(position/2).getPutts());
				holder.par.setText(setcardsArray.get(position/2).getPar());
				holder.te.setText(setcardsArray.get(position/2).getTe());	
			}else{
				holder.numberRod.setText("");
				holder.penalties.setText("");
				holder.putts.setText("");
				holder.par.setText("");
				holder.te.setText("");
			}
		}
		return convertView;
	
	}

	class SetscardsHolder{
		TextView numberRod;
		TextView par;
		TextView te;//码
		TextView penalties;//罚
		TextView putts;//推杆
	}
}
