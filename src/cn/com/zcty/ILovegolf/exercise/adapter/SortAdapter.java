package cn.com.zcty.ILovegolf.exercise.adapter;


import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.Course;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


public class SortAdapter extends BaseExpandableListAdapter {
	private Context context;
	private List<String> group;
	private List<List<Course>> child;
	private LayoutInflater inflater;
	public SortAdapter(Context context,List<String> group,List<List<Course>> child){
		this.context = context;
		this.group = group;
		this.child = child;
		inflater = LayoutInflater.from(context);
		
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		 ItemHolder itemHolder;
		 if(convertView==null){
             itemHolder = new ItemHolder();
             convertView = inflater.inflate(R.layout.child_item, null);
             itemHolder.name = (TextView) convertView.findViewById(R.id.textView1);
             itemHolder.address = (TextView) convertView.findViewById(R.id.textView2);
             convertView.setTag(itemHolder);
     } else{
             itemHolder = (ItemHolder) convertView.getTag();
     }
	 String name = child.get(groupPosition).get(childPosition).getName();
	 String address = child.get(groupPosition).get(childPosition).getAddress();
     itemHolder.name.setText(name);
     itemHolder.address.setText(address);
      return convertView;

	}
	public String getChildName(int groupPosition, int childPosition){
		return child.get(groupPosition).get(childPosition).getName();
		
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return child.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return group.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder groupHolder;
		if(convertView==null){
		groupHolder = new GroupHolder();
		convertView = inflater.inflate(R.layout.ex_item, null);
		groupHolder.textView = (TextView) convertView.findViewById(R.id.textView1 );
		convertView.setTag(groupHolder);

		}else{
			groupHolder = (GroupHolder) convertView.getTag();
		}
		groupHolder. textView.setText(group.get(groupPosition));
		return convertView;
	}
	public String uuid(int groupPosition,int childPositon){
		return child.get(groupPosition).get(childPositon).getUuid();
		
	}
	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	   class ItemHolder{
           TextView name;
           TextView address;
   }
    class GroupHolder{
           TextView textView;
           
   }
}