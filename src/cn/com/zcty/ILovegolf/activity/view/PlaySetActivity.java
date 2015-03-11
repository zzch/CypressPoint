package cn.com.zcty.ILovegolf.activity.view;


import java.net.URLEncoder;
import java.util.List;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.exercise.adapter.PlaySetExpandAdapter;
import cn.com.zcty.ILovegolf.model.PlaySet;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.JsonUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ����������
 * @author deii
 *
 */
public class PlaySetActivity extends Activity implements OnClickListener {
    /**
     * ����ʵ����
     */
	private ExpandableListView  expandableListView;
	String color_name;
	private TextView t_name;
	private PlaySetExpandAdapter playSetExpandAdapter;
	private List<PlaySet> playSets;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_playset);
		//�ҿؼ�
		expandableListView=(ExpandableListView) findViewById(R.id.expandableListView);
		expandableListView.setGroupIndicator(null);//ȥ���Դ���ͼ��
		//����¼�
		expandableListView.setOnChildClickListener(new OnChildClickListenerImpl());
		expandableListView.setOnGroupClickListener(new OnGroupClickListenerImpl());
		//ExpandableListView������
		expandableListView.setAdapter(new PlaySetExpandAdapter(this));
		init();
       
	} 
	public void init(){
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... arg0) {
				//ȡ����Ϣuuid��ֵ
				Intent intent=getIntent();
				String uuid=intent.getStringExtra("uuid");
				Log.i("uuid---->>", ""+uuid);
				//�û���token
				SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
				String token=sp.getString("token", "token");
				Log.i("token---->>", ""+token);
				try {
					//��������Ϣ��uuid����ȡ���򳡵ľ�����Ϣ�ķ���url
					String path=APIService.COURSE_INFO+"uuid="+URLEncoder.encode(uuid,"utf-8")+"&token="+URLEncoder.encode(token,"utf-8");
					playSets=JsonUtil.getPlaySetExpland_json(path);
					//Log.i("�򳡵ľ�����Ϣ", ""+);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				return null;
			}
		}.execute();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch(v.getId()){
		//������ذ�ť
		case R.id.playset_back:
			intent=new Intent(PlaySetActivity.this,ChoosePitchActivity.class);
			startActivity(intent);
			finish();
			break;
		//�����ʼ��ť
		case R.id.playset_start:
			intent=new Intent(PlaySetActivity.this,ScoreCardActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}
	
	/**
	 * child����¼�
	 * @author deii
	 *
	 */
	class  OnChildClickListenerImpl implements OnChildClickListener {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			//�ҵ�children�е�Ҫȡֵ�Ŀؼ�
			t_name=(TextView) v.findViewById(R.id.t_name);
			//ȡֵ����ֵ����color_name
			color_name= (String) t_name.getText();
			//��ȡ����ֵ����
			Toast.makeText(PlaySetActivity.this, "����T̨��ֵ�ǣ�"+color_name,Toast.LENGTH_LONG).show();
			return false;
		}
	}
  /**
   * group����¼�
   * @author deii
   *
   */
	class OnGroupClickListenerImpl implements OnGroupClickListener{
		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			// TODO Auto-generated method stub
			return false;
		}
	}
	
}
	