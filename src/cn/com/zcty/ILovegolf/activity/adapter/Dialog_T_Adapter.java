package cn.com.zcty.ILovegolf.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.zcty.ILovegolf.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Dialog_T_Adapter extends BaseAdapter{
	private Context context;
	private List<String> list=new ArrayList<String>();
	public Dialog_T_Adapter(Context context, List<String> list){
		this.context=context;
		this.list=list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder =null;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.blend_dialog_t_item, null);
			holder=new Holder();
			holder.blend_dialog_t_item_textview=(TextView) convertView.findViewById(R.id.blend_dialog_list_item_textview);
			
			holder.blend_dialog_t_item_textview=(TextView) convertView.findViewById(R.id.blend_dialog_t_item_textview);
			holder.image_t=(ImageView) convertView.findViewById(R.id.image_t);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		holder.blend_dialog_t_item_textview.setText(list.get(position));
		if(list.get(position).equals("red")){
			holder.image_t.setBackgroundResource(R.drawable.e_red);
			holder.blend_dialog_t_item_textview.setText("红色T台");
		}else if(list.get(position).equals("white")){
			holder.image_t.setBackgroundResource(R.drawable.e_white);
			holder.blend_dialog_t_item_textview.setText("白色T台");
		}else if(list.get(position).equals("blue")){
			holder.image_t.setBackgroundResource(R.drawable.e_blue);
			holder.blend_dialog_t_item_textview.setText("蓝色T台");
		}else if(list.get(position).equals("black")){
			holder.image_t.setBackgroundResource(R.drawable.e_black);
			holder.blend_dialog_t_item_textview.setText("黑色T台");
		}else{
			holder.image_t.setBackgroundResource(R.drawable.e_gold);
			holder.blend_dialog_t_item_textview.setText("金色T台");
		}
		return convertView;
	}
	class Holder{
		TextView blend_dialog_t_item_textview;
        ImageView image_t;
	}

}
