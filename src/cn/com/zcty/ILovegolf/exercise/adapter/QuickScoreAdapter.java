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
	 // ��Ļ���,���������õ���HorizontalScrollView,���԰�ťѡ��Ӧ������Ļ��
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
       // ���û�����ù�,��ʼ��convertView
       if(convertView == null){
    	// ������õ�view
    	convertView=LayoutInflater.from(context).inflate(R.layout.quick_score_item, parent,false); 
    	// ��ʼ��holder
        holder = new ViewHolder();
        holder.hSView = (HorizontalScrollView) convertView.findViewById(R.id.hsv);
    	//������  
        holder.kpitname=(TextView) convertView.findViewById(R.id.kpitname);  
        holder.kpitname.setText(quickContents.get(position).getCourse().get(0).getName());  
           Log.i("----->>", "������"+quickContents.get(position).getCourse().get(0).getName());
  		 //ʱ��
           holder.time=(TextView) convertView.findViewById(R.id.time);
           holder.time.setText(quickContents.get(position).getStarted_at());
  		 //��������
           holder.type=(TextView) convertView.findViewById(R.id.practice);
           holder.type.setText(quickContents.get(position).getType());
  		 //���и���
           holder.gan_number=(TextView) convertView.findViewById(R.id.gan_number);
           holder.gan_number.setText(quickContents.get(position).getStrokes());
  		 //�ܸ���
           holder.Pole_number = (TextView) convertView.findViewById(R.id.Pole_number);  
           holder.Pole_number.setText(quickContents.get(position).getRecorded_scorecards_count());  
          //ɾ��
           holder.butDelete = (Button) convertView.findViewById(R.id.butDelete); 
           holder.action = (LinearLayout) convertView.findViewById(R.id.action);
          
           // ��������view�Ĵ�СΪ��Ļ���,������ť�����ñ�������Ļ��
           holder.content = (LinearLayout) convertView.findViewById(R.id.content);
           LayoutParams lp = holder.content.getLayoutParams();
           lp.width = mScreentWidth;
           convertView.setTag(holder);
       }else{
    	   
    	// ��ֱ�ӻ��ViewHolder
           holder = (ViewHolder) convertView.getTag();
       }
       // ��λ�÷ŵ�view��,��������¼��Ϳ���֪�����������һ��item
       holder.butDelete.setTag(position);
		
    // ���ü����¼�
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
                 // ���ViewHolder
                 ViewHolder viewHolder = (ViewHolder) v.getTag();
                 view = v;
                 // ���HorizontalScrollView������ˮƽ����ֵ.
                 int scrollX = viewHolder.hSView.getScrollX();

                 // ��ò�������ĳ���
                 int actionW = viewHolder.action.getWidth();

                 // ע��ʹ��smoothScrollTo,����Ч���������Ƚ�Բ��,����Ӳ
                 // ���ˮƽ������ƶ�ֵ<��������ĳ��ȵ�һ��,�͸�ԭ
                 if (scrollX < actionW / 2)
                 {
                     viewHolder.hSView.smoothScrollTo(0, 0);
                 }
                 else// ����Ļ���ʾ��������
                 {
                     viewHolder.hSView.smoothScrollTo(actionW, 0);
                 }
                 return true;
             }
			
			return false;
		} 
       });

       // �����ֹɾ��һ��item��,ListView���ڲ���״̬,ֱ�ӻ�ԭ
       if (holder.hSView.getScrollX() != 0) {
           holder.hSView.scrollTo(0, 0);
       }

       // ���ü����¼�
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
        	// ˢ��ListView����
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