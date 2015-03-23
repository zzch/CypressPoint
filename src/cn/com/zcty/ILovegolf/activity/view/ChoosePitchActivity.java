package cn.com.zcty.ILovegolf.activity.view;
import java.net.URLEncoder;
import java.util.List;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.exercise.adapter.PitchAdapter;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


/**
 * 选择球场类
 * @author deii
 *
 */
public class ChoosePitchActivity extends Activity {
   
	private ListView listpich;//球场列表，并显示距离
	private List<QiuChangList> qiuchanglists;//从服务器端获取过来的球场列表信息
	private SharedPreferences ss;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_choosepitch);
		
		//找控件
		listpich=(ListView) findViewById(R.id.listpich);
		init();
		//子条目点击事件
		listpich.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				ss = getSharedPreferences("name", MODE_PRIVATE);
				SharedPreferences.Editor editor = ss.edit();
				editor.putString("name", qiuchanglists.get(position).getName());
				editor.commit();
				Intent intent=new Intent(ChoosePitchActivity.this,PlaySetActivity.class);
				intent.putExtra("uuid", qiuchanglists.get(position).getUuid());
				Log.i("--->>uuid", "uuid"+qiuchanglists.get(position).getUuid());
				SharedPreferences ss = getSharedPreferences("key", MODE_PRIVATE);
				SharedPreferences.Editor editors = ss.edit();
				editors.putString("key", qiuchanglists.get(position).getUuid());
				editors.commit();
				startActivity(intent);
				finish();
			}
		});
		
	}
	
	
	//获取球场列表信息
			public void init(){
				try {
					new AsyncTask<Void, Void, Void>() {
						SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
						String latitude="39.975368";
						String longitude="116.300841";
						String token=sp.getString("token", "token");
						String path=APIService.NEAREST_COURSE+
						"longitude="+URLEncoder.encode(longitude,"utf-8")+"&latitude="+URLEncoder.encode(latitude,"utf-8")+"&token="+URLEncoder.encode(token,"utf-8");
						//List<String> list =GpsGetLocation.getGpsLocationInfo(context);
						@Override
						protected Void doInBackground(Void... arg0) {
							// TODO Auto-generated method stub
						   try {
							qiuchanglists=JsonUtil.getChoosePitch_json(path, longitude, latitude, token);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							return null;
						}
						//通知适配器数据发生变化
					@Override				
					protected void onPostExecute(Void result) {
						listpich.setAdapter(new PitchAdapter(ChoosePitchActivity.this,qiuchanglists));
						super.onPostExecute(result);
					}
					}.execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
	
	//返回按钮点击事件
	public void choosepith_back(View v){
		Intent intent=new Intent(ChoosePitchActivity.this,QuickScoreActivity.class);
		startActivity(intent);
		finish();
	}

	//搜索按钮点击事件
	public void qiehuan(View v){
		Intent intent=new Intent(ChoosePitchActivity.this,ListChoosePitchActivity.class);
		startActivity(intent);
		finish();
	}	
}
	
