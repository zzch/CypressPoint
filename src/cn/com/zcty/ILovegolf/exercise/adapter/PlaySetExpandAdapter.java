package cn.com.zcty.ILovegolf.exercise.adapter;


import cn.com.zcty.ILovegolf.activity.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaySetExpandAdapter extends BaseExpandableListAdapter  {

	private String[] groups={"选择球场","选择发球洞","开球T台"};
	private int [][] children={{},{},{R.drawable.red,R.drawable.blue,R.drawable.white,R.drawable.jin,R.drawable.black}};
	private String [][]children_name={{},{},{"红T","蓝T","白T","金T","黑T"}};
	private Context context;
	public PlaySetExpandAdapter(Context context){
		this.context=context;
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return children[groupPosition][childPosition];
	}
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean ischildren, View viewchildren,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=View.inflate(context, R.layout.children_item, null);
		ImageView t_color=(ImageView) view.findViewById(R.id.t_color);
		TextView t_name=(TextView) view.findViewById(R.id.t_name);
		t_color.setImageResource(children[groupPosition][childPosition]);
		t_name.setText(children_name[groupPosition][childPosition]);
		return view;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return children[groupPosition].length;
	}
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groups[groupPosition];
	}
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groups.length;
	}
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpland, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=View.inflate(context, R.layout.group_item, null);
		TextView group_item1=(TextView) view.findViewById(R.id.group_item1);
		TextView info=(TextView) view.findViewById(R.id.info);
		ImageView iv=(ImageView) view.findViewById(R.id.top_bottom);
		//取值赋值
		if(isExpland){
			iv.setImageResource(R.drawable.u378_normal);
		}else{
			iv.setImageResource(R.drawable.u379_normal);
		}
		group_item1.setText(groups[groupPosition]);
		return view;
	}
	//表示孩子是否和组ID是跨基础数据的更改改
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}
	//孩子在指定的位置是可选的，即：children中的元素是可点击
	@Override
	public boolean isChildSelectable(int groupPosition,  int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
}