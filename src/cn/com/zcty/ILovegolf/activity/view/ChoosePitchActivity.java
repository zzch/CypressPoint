package cn.com.zcty.ILovegolf.activity.view;
import java.net.URLEncoder;
import java.util.List;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.PitchAdapter;
import cn.com.zcty.ILovegolf.model.QiuChangList;
import cn.com.zcty.ILovegolf.tools.MyApplication;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.JsonUtil;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
	private Context context;
	public static String TAG = "LocTestDemo";
	private BroadcastReceiver broadcastReceiver;
	public static String LOCATION_BCR = "location_bcr";
	private	String address;
	String addres[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_choosepitch);
		initialize();
		MyApplication.getInstance().requestLocationInfo();
		//找控件
		listpich=(ListView) findViewById(R.id.listpich);
		
		
		//子条目点击事件
		listpich.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				/*
				 *返回球场name 
				 */
				Intent intent = new Intent(ChoosePitchActivity.this,CreateMatchActivity.class);
				intent.putExtra("name", qiuchanglists.get(position).getName());
				intent.putExtra("false", "0");
				intent.putExtra("uuid", qiuchanglists.get(position).getUuid());
				setResult(0, intent);
				finish();
			}
		});
		
	}
	
	private void initialize()
	{
		registerBroadCastReceiver();
	}
	
	//获取球场列表信息
			public void init(){
				
				try {
					new AsyncTask<Void, Void, Void>() {
						SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
						String token=sp.getString("token", "token");
						
						
						String path=APIService.NEAREST_COURSE+"longitude="+addres[1]+"&latitude="+addres[0]+"&token="+token;
						
						@Override
						protected Void doInBackground(Void... arg0) {
							// TODO Auto-generated method stub
						   try {
							   Log.i("address",addres[0]);
							qiuchanglists=JsonUtil.getChoosePitch_json(path);
							Log.i("xiaoqin", "xiaoqin"+path);
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
		intent.putExtra("sign", "1");
		startActivity(intent);
		finish();
	}
	
	
	/**
	 * 注册一个广播，监听定位结果
	 */
	private void registerBroadCastReceiver()
	{
		broadcastReceiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context context, Intent intent)
			{
				 address = intent.getStringExtra("address");
				 addres = address.split(",");
				 Log.i("address", addres[0]);
				//locInfo.setText(address);			
				init();
			}
		};
		IntentFilter intentToReceiveFilter = new IntentFilter(LOCATION_BCR);
		
		registerReceiver(broadcastReceiver, intentToReceiveFilter);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

}
	
