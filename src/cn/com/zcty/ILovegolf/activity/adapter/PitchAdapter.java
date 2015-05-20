package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.List;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.QiuChangList;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PitchAdapter extends BaseAdapter {
	
	private Context context;
	private List<QiuChangList> qiuchanglists;
	private LayoutInflater inflater;
	
	public PitchAdapter(Context context,List<QiuChangList> qiuchanglists){
   	 this.context=context;
   	 this.qiuchanglists=qiuchanglists;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return qiuchanglists.size();
	} 
	@Override
	public Object getItem(int poisition) {
		// TODO Auto-generated method stub
		return qiuchanglists.get(poisition);
	}

	@Override
	public long getItemId(int poisition) {
		// TODO Auto-generated method stub
		return poisition;
	}

	@Override
	public View getView(int poisition, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=View.inflate(context, R.layout.choosepitch_item, null);
	
		TextView  pitchname=(TextView) view.findViewById(R.id.pitchname);
		TextView  address=(TextView) view.findViewById(R.id.address);
		TextView  distance=(TextView) view.findViewById(R.id.distance);
          
           pitchname.setText(qiuchanglists.get(poisition).getName());
          // pitchname.setTextColor(Color.argb(155, 55, 155, 255)); 
            Log.i("-----pitchname-->>", ""+qiuchanglists.get(poisition).getName());
           address.setText(qiuchanglists.get(poisition).getHoles_count()+"洞");
         //  address.setTextColor(0x00000000);
           distance.setText(qiuchanglists.get(poisition).getDistance());
		return view;
        }
	
}
