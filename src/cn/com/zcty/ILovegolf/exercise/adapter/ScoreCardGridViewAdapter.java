package cn.com.zcty.ILovegolf.exercise.adapter;

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
			holder.penalties = (TextView) view.findViewById(R.id.textView2);
			holder.putts = (TextView) view.findViewById(R.id.textView3);
			holder.par = (TextView) view.findViewById(R.id.textView4);
			holder.te = (TextView) view.findViewById(R.id.textView5);
			holder.imageView1 = (TextView) view.findViewById(R.id.image1);
			holder.imageView2 = (TextView) view.findViewById(R.id.image2);
			holder.p = (TextView) view.findViewById(R.id.t1);
			holder.y = (TextView) view.findViewById(R.id.t2);
			holder.image =  (ImageView) view.findViewById(R.id.imageView_1);
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
			holder.te.setText(scorecarsArray.get(position/2).getDistance_from_hole_to_tee_box());		
			holder.p.setText("P");
			holder.y.setText("Y");
			holder.imageView1.setBackground(null);
			holder.numberRod.setBackgroundResource(R.drawable.e_card_yuan);
			holder.imageView2.setBackground(null);
			holder.putts.setText("");
			holder.penalties.setText("");
	
		}else{
			
			if(setcardsArray.get(position/2).getRodNum()!=null&&!(setcardsArray.get(position/2).getRodNum().equals("null"))){
				holder.imageView1.setBackgroundResource(R.drawable.shaozi);
				holder.image.setVisibility(View.GONE);
				holder.numberRod.setBackground(null);
				holder.numberRod.setTextSize(65);
				holder.penalties.setTextColor(Color.RED);
				holder.numberRod.setText(setcardsArray.get(position/2).getRodNum());
				holder.penalties.setText(setcardsArray.get(position/2).getPenalties());
				holder.putts.setText(setcardsArray.get(position/2).getPutts());
				holder.par.setText(setcardsArray.get(position/2).getTe());
				holder.te.setText(setcardsArray.get(position/2).getPar());
				holder.imageView1.setText("");
				holder.imageView2.setText("");
				holder.p.setText("");
				holder.y.setText("");
				if(setcardsArray.get(position/2).getPar().equals("命中")){
					holder.imageView2.setBackgroundResource(R.drawable.juzhong);
				}else if(setcardsArray.get(position/2).getPar().equals("左侧")){
					holder.imageView2.setBackgroundResource(R.drawable.lelf);
				}else{
					holder.imageView2.setBackgroundResource(R.drawable.right);
				}
				//return view;
			}else if(setcardsArray.size()<36){
				holder.image.setVisibility(View.VISIBLE);
				holder.imageView1.setBackground(null);
				holder.numberRod.setBackground(null);
				holder.imageView2.setBackground(null);
				holder.numberRod.setText("");
				holder.penalties.setText("");
				holder.putts.setText("");
				holder.par.setText("");
				holder.te.setText("");
				holder.p.setText("");
				holder.y.setText("");
				//return view;
			}
		}
		return view;
	
	}

	class SetscardsHolder{
		TextView numberRod;
		TextView par;
		TextView te;//码
		TextView penalties;//罚
		TextView putts;//推杆
		TextView imageView1;
		TextView imageView2;
		TextView p;
		TextView y;
		ImageView image;
	}
	
}
