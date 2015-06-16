package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.AnlyzeDiamond;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AnlyzeDiamondAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<AnlyzeDiamond> anDiamonds = new ArrayList<AnlyzeDiamond>();
	private LayoutInflater inflater;
	public AnlyzeDiamondAdapter(Context context,ArrayList<AnlyzeDiamond> anDiamonds) {
		this.context = context;
		this.anDiamonds = anDiamonds;
		inflater = inflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return anDiamonds.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return anDiamonds.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.diamond_item, null);
			holder = new Holder();
			holder.image_addres = (ImageView) convertView.findViewById(R.id.image_addres);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.diamond_name);
			holder.addressTextView = (TextView) convertView.findViewById(R.id.diamond_address);
			holder.changciTextView = (TextView) convertView.findViewById(R.id.diamond_changci);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		if(anDiamonds.get(position).getAddress().equals("null")){
			holder.addressTextView.setText("星园国际D做805");
			//holder.addressTextView.setVisibility(View.GONE);
			//holder.image_addres.setVisibility(View.GONE);
		}else{
			holder.image_addres.setVisibility(View.VISIBLE);
			holder.addressTextView.setText(anDiamonds.get(position).getAddress());
		}
		holder.nameTextView.setText(anDiamonds.get(position).getName());
		holder.changciTextView.setText(anDiamonds.get(position).getVisited_count());
		return convertView;
	}
	class Holder{
		TextView nameTextView;
		TextView addressTextView;
		TextView changciTextView;
		ImageView image_addres;
	}
}
