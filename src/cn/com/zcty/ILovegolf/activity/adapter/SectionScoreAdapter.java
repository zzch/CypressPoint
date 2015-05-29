package cn.com.zcty.ILovegolf.activity.adapter;

import org.apache.http.client.fluent.Content;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.SectionScore;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SectionScoreAdapter extends BaseAdapter{
	private SectionScore sectionScore;
	private Context context;
	private LayoutInflater inflater;
	public SectionScoreAdapter(Context context,SectionScore sectionScore) {
		this.context = context;
		this.sectionScore = sectionScore;
		inflater = LayoutInflater.from(context);
		Log.i("sectionscore",sectionScore.getLast_6_score()+"aaa");
	}
	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public Object getItem(int position) {
		return sectionScore;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VoldHolder holder=null;
		/*if(convertView==null){*/
			convertView = inflater.inflate(R.layout.sectionscore_item, null);
			holder = new VoldHolder();
			holder.score1 = (TextView) convertView.findViewById(R.id.score_1);
			holder.score2 = (TextView) convertView.findViewById(R.id.score_2);
			holder.score3 = (TextView) convertView.findViewById(R.id.score_3);
			convertView.setTag(convertView);
		/*}else{
			holder = (VoldHolder) convertView.getTag();
		}*/
		if(sectionScore.getFront_6_score().equals("null")){
			holder.score1.setText("一");
		}else{
			holder.score1.setText(sectionScore.getFront_6_score());
		}
		if(sectionScore.getMiddle_6_score().equals("null")){
			holder.score2.setText("一");
		}else{
			holder.score2.setText(sectionScore.getMiddle_6_score());
		}
		if(sectionScore.getLast_6_score().equals("null")){
			holder.score3.setText("一");
		}else{
			holder.score3.setText(sectionScore.getLast_6_score());
		}
		return convertView;
	}
	class VoldHolder{
		 TextView score1;
		 TextView score2;
		 TextView score3;
	}
}
