package cn.com.zcty.ILovegolf.activity.view;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.PitchAdapter;
import cn.com.zcty.ILovegolf.model.QiuChangList;
import cn.com.zcty.ILovegolf.tools.AutoListView;
import cn.com.zcty.ILovegolf.tools.AutoListView.OnLoadListener;
import cn.com.zcty.ILovegolf.tools.AutoListView.OnRefreshListener;
import cn.com.zcty.ILovegolf.tools.MyApplication;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

import com.handmark.pulltorefresh.library.PullToRefreshBase;


/**
 * 选择球场类
 * @author deii
 *
 */
public class ChoosePitchActivity extends Activity {
	private int page = 1;
	private ProgressDialog progressDialog;
	private AutoListView listpich;//球场列表，并显示距离
	private List<QiuChangList> qiuchanglists = new ArrayList<QiuChangList>();//从服务器端获取过来的球场列表信息
	private SharedPreferences ss;
	private Context context;
	public static String TAG = "LocTestDemo";
	private BroadcastReceiver broadcastReceiver;
	public static String LOCATION_BCR = "location_bcr";
	private	String address;
	private	String addres[];
	private PitchAdapter adapter;
	int size = 15;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				hideProgressDialog();
				getData();
					/*if(page!=1){
						listpich.setSelection(size);
						adapter.notifyDataSetInvalidated();//通知adapter数据有变化
					}*/
					
				
				
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_choosepitch);
		showProgressDialog("提示", "正在加载", this);
		MyApplication.getInstance().requestLocationInfo();
		initialize();
		initView();
		setListeners();




	}
	private void setListeners() {
		
		listpich.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				page = 1;
				qiuchanglists.clear();
				MyApplication.getInstance().requestLocationInfo();
			}
		});
		listpich.setOnLoadListener(new OnLoadListener() {
			
			@Override
			public void onLoad() {
				
				//size = qiuchanglists.size()-(qiuchanglists.size()/(listpich.getLastVisiblePosition()-listpich.getFirstVisiblePosition()));
						
				page++;
				new MyTask().start();
				Log.i("dasfd", page+"aaa"+size);
				


			}
		});
		
		
		
		
		//子条目点击事件
		listpich.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				/*
				 *返回球场name 
				 */
				Intent intent = new Intent(ChoosePitchActivity.this,CreateMatchActivity.class);
				intent.putExtra("name", qiuchanglists.get(position-1).getName());
				intent.putExtra("false", "0");
				intent.putExtra("uuid", qiuchanglists.get(position-1).getUuid());
				setResult(0, intent);
				finish();
			}
		});
	}
	private void initView() {
		listpich=(AutoListView) findViewById(R.id.listview);
	}
	public void getData(){
		if(listpich.getAdapter()==null){
			adapter = new PitchAdapter(ChoosePitchActivity.this,qiuchanglists);
			listpich.setAdapter(adapter);
		}else{
			//adapter.;//update your adapter's data
			adapter.notifyDataSetChanged();
		}
		
		
		
		listpich.onRefreshComplete();
		listpich.onLoadComplete();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0){
			if(resultCode==0){
				/*
				 *返回球场name 
				 */
				Intent intent = new Intent(ChoosePitchActivity.this,CreateMatchActivity.class);
				intent.putExtra("name", data.getStringExtra("name"));
				intent.putExtra("false", "0");
				intent.putExtra("uuid", data.getStringExtra("uuid"));
				setResult(0, intent);
				finish();
			}
		}
	}
	private void initialize()
	{
		registerBroadCastReceiver();
	}




	//返回按钮点击事件
	public void choosepith_back(View v){
		Intent intent=new Intent(ChoosePitchActivity.this,CreateMatchActivity.class);
		intent.putExtra("name", "null");
		setResult(0, intent);
		finish();
	}
	/*
	 * (non-Javadoc)返回键
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){

			Intent intent = new Intent(ChoosePitchActivity.this,CreateMatchActivity.class);		
			intent.putExtra("name", "null");
			setResult(0, intent);
			finish();

		}
		return false;
	}
	//搜索按钮点击事件
	public void qiehuan(View v){
		Intent intent=new Intent(ChoosePitchActivity.this,ListChoosePitchActivity.class);
		intent.putExtra("sign", "1");
		startActivityForResult(intent, 0);
		//finish();


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
				new MyTask().start();
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

	class MyTask extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");


			String path=APIService.NEAREST_COURSE+"longitude="+addres[1]+"&latitude="+addres[0]+"&token="+token+"&page="+page;
			Log.i("choosePath", path);
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("jsonData--->", ""+jsonData);
			JSONArray jsonArray;

			try {
				jsonArray = new JSONArray(jsonData);
				Log.i("lengthsss", jsonArray.length()+"");
				for(int i=0;i<jsonArray.length();i++)
				{
					QiuChangList qiuchanglist=new QiuChangList();				    
					JSONObject jsonObject=jsonArray.getJSONObject(i);
					qiuchanglist.setUuid(jsonObject.getString("uuid"));
					qiuchanglist.setName(jsonObject.getString("name"));
					qiuchanglist.setAddress(jsonObject.getString("address"));
					qiuchanglist.setDistance(jsonObject.getString("distance"));
					qiuchanglist.setHoles_count(jsonObject.getString("holes_count"));
					Log.i("lengthsss", i+"s");
					qiuchanglists.add(qiuchanglist);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what=1;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
	}
	/*
	 * 提示加载
	 */
	public   void  showProgressDialog(String title,String message,Activity context){
		if(progressDialog ==null){
			progressDialog = ProgressDialog.show( context, title, message,true,true );

		} else if (progressDialog .isShowing()){
			progressDialog.setTitle(title);
			progressDialog.setMessage(message);
		}
		progressDialog.show();

	}
	/*
	 * 隐藏加载
	 */
	public  void hideProgressDialog(){
		if(progressDialog !=null &&progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}
}

