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
 * 打球设置类
 * @author deii
 *
 */
public class PlaySetActivity extends Activity implements OnClickListener {
    /**
     * 对象实例化
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
		//找控件
		expandableListView=(ExpandableListView) findViewById(R.id.expandableListView);
		expandableListView.setGroupIndicator(null);//去掉自带的图标
		//点击事件
		expandableListView.setOnChildClickListener(new OnChildClickListenerImpl());
		expandableListView.setOnGroupClickListener(new OnGroupClickListenerImpl());
		//ExpandableListView的适配
		expandableListView.setAdapter(new PlaySetExpandAdapter(this));
		init();
       
	} 
	public void init(){
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... arg0) {
				//取球场信息uuid的值
				Intent intent=getIntent();
				String uuid=intent.getStringExtra("uuid");
				Log.i("uuid---->>", ""+uuid);
				//用户的token
				SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
				String token=sp.getString("token", "token");
				Log.i("token---->>", ""+token);
				try {
					//根据球场信息的uuid来获取该球场的具体信息的访问url
					String path=APIService.COURSE_INFO+"uuid="+URLEncoder.encode(uuid,"utf-8")+"&token="+URLEncoder.encode(token,"utf-8");
					playSets=JsonUtil.getPlaySetExpland_json(path);
					//Log.i("球场的具体信息", ""+);
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
		//点击返回按钮
		case R.id.playset_back:
			intent=new Intent(PlaySetActivity.this,ChoosePitchActivity.class);
			startActivity(intent);
			finish();
			break;
		//点击开始按钮
		case R.id.playset_start:
			intent=new Intent(PlaySetActivity.this,ScoreCardActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}
	
	/**
	 * child点击事件
	 * @author deii
	 *
	 */
	class  OnChildClickListenerImpl implements OnChildClickListener {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			//找到children中的要取值的控件
			t_name=(TextView) v.findViewById(R.id.t_name);
			//取值并将值赋给color_name
			color_name= (String) t_name.getText();
			//将取到的值保存
			Toast.makeText(PlaySetActivity.this, "开球T台的值是："+color_name,Toast.LENGTH_LONG).show();
			return false;
		}
	}
  /**
   * group点击事件
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
	