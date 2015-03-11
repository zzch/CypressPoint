package cn.com.zcty.ILovegolf.exercise.adapter;

import java.net.URLEncoder;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.QuickScoreActivity;
import cn.com.zcty.ILovegolf.model.QuickContent;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public  class QuickScoreAdapter extends BaseAdapter implements OnClickListener{
	   
	 private  List<QuickContent> quickContents;
	 private Context context;
	 // 屏幕宽度,由于我们用的是HorizontalScrollView,所以按钮选项应该在屏幕外
	 private int mScreentWidth;
	 private View view;
	 public QuickScoreAdapter(Context context,List<QuickContent> quickContents,int screenWidth){
		this.context=context;
		this.quickContents= quickContents;
		this.mScreentWidth =screenWidth;
	 }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return quickContents.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return quickContents.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
       ViewHolder holder=null;
       // 如果没有设置过,初始化convertView
       if(convertView == null){
    	// 获得设置的view
    	convertView=LayoutInflater.from(context).inflate(R.layout.quick_score_item, parent,false); 
    	// 初始化holder
        holder = new ViewHolder();
        holder.hSView = (HorizontalScrollView) convertView.findViewById(R.id.hsv);
    	//球场名称  
        holder.kpitname=(TextView) convertView.findViewById(R.id.kpitname);  
        holder.kpitname.setText(quickContents.get(position).getCourse().get(0).getName());  
           Log.i("----->>", "球场名称"+quickContents.get(position).getCourse().get(0).getName());
  		 //时间
           holder.time=(TextView) convertView.findViewById(R.id.time);
           holder.time.setText(quickContents.get(position).getStarted_at());
  		 //比赛类型
           holder.type=(TextView) convertView.findViewById(R.id.practice);
           holder.type.setText(quickContents.get(position).getType());
  		 //命中杆数
           holder.gan_number=(TextView) convertView.findViewById(R.id.gan_number);
           holder.gan_number.setText(quickContents.get(position).getStrokes());
  		 //总杆数
           holder.Pole_number = (TextView) convertView.findViewById(R.id.Pole_number);  
           holder.Pole_number.setText(quickContents.get(position).getRecorded_scorecards_count());  
          //删除
           holder.butDelete = (Button) convertView.findViewById(R.id.butDelete); 
           holder.action = (LinearLayout) convertView.findViewById(R.id.action);
          
           // 设置内容view的大小为屏幕宽度,这样按钮就正好被挤出屏幕外
           holder.content = (LinearLayout) convertView.findViewById(R.id.content);
           LayoutParams lp = holder.content.getLayoutParams();
           lp.width = mScreentWidth;
           convertView.setTag(holder);
       }else{
    	   
    	// 有直接获得ViewHolder
           holder = (ViewHolder) convertView.getTag();
       }
       // 把位置放到view中,这样点击事件就可以知道点击的是哪一条item
       holder.butDelete.setTag(position);
		
    // 设置监听事件
       convertView.setOnTouchListener(new View.OnTouchListener()
       {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction())
			{
			 case MotionEvent.ACTION_DOWN:
				 if (view != null) {
                     ViewHolder viewHolder1 = (ViewHolder) view.getTag();
                     viewHolder1.hSView.smoothScrollTo(0, 0);
                 }
			 case MotionEvent.ACTION_UP:
                 // 获得ViewHolder
                 ViewHolder viewHolder = (ViewHolder) v.getTag();
                 view = v;
                 // 获得HorizontalScrollView滑动的水平方向值.
                 int scrollX = viewHolder.hSView.getScrollX();

                 // 获得操作区域的长度
                 int actionW = viewHolder.action.getWidth();

                 // 注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
                 // 如果水平方向的移动值<操作区域的长度的一半,就复原
                 if (scrollX < actionW / 2)
                 {
                     viewHolder.hSView.smoothScrollTo(0, 0);
                 }
                 else// 否则的话显示操作区域
                 {
                     viewHolder.hSView.smoothScrollTo(actionW, 0);
                 }
                 return true;
             }
			
			return false;
		} 
       });

       // 这里防止删除一条item后,ListView处于操作状态,直接还原
       if (holder.hSView.getScrollX() != 0) {
           holder.hSView.scrollTo(0, 0);
       }

       // 设置监听事件
       holder.butDelete.setOnClickListener(this);
     
         return convertView;
	 }
	
	public class ViewHolder{
		TextView kpitname,time,type,gan_number,Pole_number;
		Button butDelete;
		HorizontalScrollView hSView;
		LinearLayout action,content;
	 }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int position = (Integer) v.getTag();
        switch (v.getId()) {
        case R.id.butDelete:
        	quickContents.remove(position);
        	Log.i("----->>>", "remove"+quickContents.remove(position));
        	init();
        	// 刷新ListView内容
            notifyDataSetChanged();
        	break;
        	
        default:
            break;
        }
	}
	
	 public void init(){
		 new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				try {
		        	SharedPreferences sp=context.getSharedPreferences("register",Context.MODE_PRIVATE);
		        	String token=sp.getString("token", "token");
		        	String uuid=quickContents.get(0).getUuid();
		        	String path=APIService.DELETE_MATCHES+"uuid="+URLEncoder.encode(uuid,"utf-8")+"&token="+URLEncoder.encode(token, "utf-8");	
		        	quickContents=HttpUtils.HttpClientDelete(path);
		        	Log.i("---->>", "path-->>"+path);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				return null;
			}
		}.execute();
	 }
}   