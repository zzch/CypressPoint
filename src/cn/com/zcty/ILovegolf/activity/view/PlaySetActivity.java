package cn.com.zcty.ILovegolf.activity.view;


import java.net.URLEncoder;
import java.util.List;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.exercise.adapter.PlaySetExpandAdapter;
import cn.com.zcty.ILovegolf.model.PlaySet;
import cn.com.zcty.ILovegolf.model.QiuChangList;
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
	private List<QiuChangList> qiuchanglists;
	private int position;
    private String uuid;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_playset);
		//获取球场信息uuid的值
		Intent intent=getIntent();
		uuid=intent.getStringExtra("uuid");
		//找控件
		expandableListView=(ExpandableListView) findViewById(R.id.expandableListView);
		expandableListView.setGroupIndicator(null);//去掉自带的图标

		//ExpandableListView的适配
		expandableListView.setAdapter(new PlaySetExpandAdapter(this));
		init();
       
	} 
	public void init(){
			
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... arg0) {
				
				Log.i("uuid---->>", ""+uuid);
				//用户的token
				SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
				String token=sp.getString("token", "token");
				Log.i("token---->>", ""+token);
				try {
					//根据球场信息的uuid来获取该球场的具体信息的访问URL
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
			intent.putExtra("playsetuuid", "uuid");
			Log.i("--->>uuid", "球场uuid---"+uuid);
			startActivity(intent);
			finish();
			break;
		}
	}
	
	
}
	