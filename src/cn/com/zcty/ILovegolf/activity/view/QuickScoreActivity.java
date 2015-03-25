package cn.com.zcty.ILovegolf.activity.view;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.exercise.adapter.QuickScoreAdapter;
import cn.com.zcty.ILovegolf.model.Course;
import cn.com.zcty.ILovegolf.model.QuickContent;
import cn.com.zcty.ILovegolf.tools.MessageItem;
import cn.com.zcty.ILovegolf.tools.SlidingDeleteListView;
import cn.com.zcty.ILovegolf.tools.SlidingDeleteListView.IXListViewListener;
import cn.com.zcty.ILovegolf.tools.SlidingDeleteSlideView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import cn.com.zcty.ILovegolf.utils.JsonUtil;
import cn.com.zcty.ILovegolf.utils.ProgressDialogUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * 简单记分卡
 * @author deii
 *
 */
public class QuickScoreActivity extends Activity implements IXListViewListener,
AdapterView.OnItemLongClickListener,OnClickListener {
    
	private Handler mHandler;
	private SlidingDeleteListView mListView; //listView控件
	private SlidingDeleteSlideView mLastSlideViewWithStatusOn;
	private QuickScoreAdapter slideAdapter;
	private LayoutInflater mInflater;
	private String TAG = "XListViewActivity";
    private  Intent intent;
   // private List<QuickContent> quickContent=new ArrayList<QuickContent>();           
    private LinearLayout image_layout;
    private LinearLayout XListView_layout;
    private  int pag=1;
    private String path;
    private Button cancel;
    private Button dialog_ok;
    private AlertDialog dialog;
    private ProgressDialog progressDialog;
    private ArrayList<QuickContent> quickArrayList = new ArrayList<QuickContent>();
    private ArrayList<String> uuidArrayList = new ArrayList<String>();
    private ArrayList<String> nameArrayList = new ArrayList<String>();
    private MyTask thread;
    /** 记录选中listviw选中项*/
	private HashMap<Integer, Boolean> checkedItemMap = new HashMap<Integer, Boolean>();
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				mListView.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						SharedPreferences ss = getSharedPreferences("name", MODE_PRIVATE);
						SharedPreferences.Editor editor = ss.edit();
						editor.putString("name", quickArrayList.get(position-1).getCourse().get(position-1).getName());
						editor.commit();
						intent =new Intent(QuickScoreActivity.this,ScoreCardActivity.class);
						//intent传值
						Log.i("qqqqqqq", position+"zhou");
						//intent.putExtra("uuit_t",a);
						intent.putExtra("uuid", quickArrayList.get(position-1).getUuid());		
						startActivity(intent);
						finish();
						
					}
				});
				mListView.setPullLoadEnable(true);
				mListView.setEnableSlidingDelete(true);
				mListView.setAdapter(slideAdapter);
				mListView.setXListViewListener(QuickScoreActivity.this);
				hideProgressDialog();
				//progressDialog.dismiss();
			}
			
		};
	};
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_quick_score);		
		initView();
		thread = new MyTask();
		thread.start();
		
	}
 
    public void initView(){
    	mHandler = new Handler();    	
    	slideAdapter = new QuickScoreAdapter(this, quickArrayList,nameArrayList,
				new onSlideListener(), new onDeleteListen());
    	mListView = (SlidingDeleteListView) findViewById(R.id.xListView);
		image_layout = (LinearLayout) findViewById(R.id.image_layout);
		XListView_layout = (LinearLayout) findViewById(R.id.XListView_layout);
		showProgressDialog("提示", "正在加载");
		
		
		
		
    }
/*	Thread task = new Thread(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			Message msg = handler.obtainMessage();
			msg.what = 2;
			handler.sendMessageDelayed(msg, 5000);
		}
	};*/
    /*
	 * 提示加载
	 */
	public  void  showProgressDialog(String title,String message){
		if(progressDialog==null){
			progressDialog = ProgressDialog.show(this, title, message,true,false);
			
		}else if(progressDialog.isShowing()){
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
    /**
	 * @Function:加载更多，刷新完成
	 * */
	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("");// 刚刚
	}
	
	
	/**
	 * @Function:回调刷新
	 * */
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				quickArrayList.clear();
				new MyTask().start();
				slideAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	
	/**
	 * @Function:回调加载更多
	 * */
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				slideAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	
	

    
    /**
	 * @Function:监听选中listview item 项,保证选中只有一项是处于打开状态
	 * */
	private class onSlideListener implements SlidingDeleteSlideView.OnSlideListener {
		@Override
		public void onSlide(View view, int status) {
			if (mLastSlideViewWithStatusOn != null
					&& mLastSlideViewWithStatusOn != view) {
				mLastSlideViewWithStatusOn.shrink();
			}

			if (status == SLIDE_STATUS_ON) {
				mLastSlideViewWithStatusOn = (SlidingDeleteSlideView) view;
			}
		}
	}
	
	
	/**
	 * @Function:删除监听
	 * */
	private class onDeleteListen implements QuickScoreAdapter.OnDeleteListener {
		@Override
		public void onDelete(View view, int position) {
			Toast.makeText(QuickScoreActivity.this, "点击了删除", Toast.LENGTH_LONG).show();
			quickArrayList.remove(position);
			slideAdapter.notifyDataSetChanged();
			new MyTaskDele(uuidArrayList.get(position)).start();
		}

	}
	
	/**
	 * @Function:收集选中listview item 状态
	 * */
	private void putCheckedItemMap(int key) {
		// checkedItemMap
		if (checkedItemMap.containsKey(key)) {
			boolean value = checkedItemMap.get(key);
			if (value) {
				checkedItemMap.remove(key);
			}
		} else {
			checkedItemMap.put(key, true);
		}
	}
	
	/**
	 * @Function:强制刷新listview(数据源没有变化的情况)
	 * */
	private void updateListviewByDataSourceNoChange(boolean isLongState){
		slideAdapter.setIsLongState(isLongState);
		slideAdapter.setCheckItemMap(checkedItemMap);
		slideAdapter.notifyDataSetChanged();
		mListView.setAdapter(slideAdapter);
		mListView.setSelectionFromTop();
		
	}
	
  
 
   
    //点击事件
	public void onclick(View v){
		
		switch(v.getId()){
		//返回按钮
		case R.id.k_back:
			intent=new Intent(QuickScoreActivity.this,TabHostActivity.class);
			startActivity(intent);
			finish();	
			break;
	    //新建按钮
		case R.id.k_build:
			 intent=new Intent(QuickScoreActivity.this,ChoosePitchActivity.class);
			 startActivity(intent);
			 finish();
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	class MyTask extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String page=Integer.toHexString(pag);
			String token=sp.getString("token", "token");
			path = APIService.MATCHES_LIST+"page="+page+"&token="+token;
			 String JsonData=HttpUtils.HttpClientGet(path);
			 Log.i("asdf", JsonData);
			 try {
				JSONArray jsonarray=new JSONArray(JsonData);
				List<Course> arrayCouse = new ArrayList<Course>();
				for(int i=0;i<jsonarray.length();i++){
					 //实例化
					
					 QuickContent quickContent=new QuickContent(); 
					 JSONObject jsonObj=jsonarray.getJSONObject(i);
					 quickContent.setUuid(jsonObj.getString("uuid"));
					 Log.i("uuidddd", jsonObj.getString("uuid"));
					 quickContent.setType(jsonObj.getString("type"));
					
						Course course=new Course();
						JSONObject obj=jsonObj.getJSONObject("venue");
						course.setUuid(obj.getString("uuid"));
						uuidArrayList.add(jsonObj.getString("uuid"));
						course.setName(obj.getString("name"));
						nameArrayList.add(obj.getString("name"));
						course.setAddress(obj.getString("address"));
						arrayCouse.add(course);

					 quickContent.setStrokes(jsonObj.getString("score"));
					 quickContent.setRecorded_scorecards_count(jsonObj.getString("recorded_scorecards_count"));
					 
					 quickContent.setStarted_at(jsonObj.getString("started_at"));
					 quickContent.setCourse(arrayCouse);
					 quickArrayList.add(quickContent);
				   }
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
	}
	class MyTaskDele extends Thread{
		String uuid;
		public MyTaskDele(String uuid) {
			MyTaskDele.this.uuid = uuid;
		}
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			Log.i("ssss", uuid+"zhou");
			String path = APIService.DELET+"uuid="+uuid+"&token="+token;
			try {
				String jsonDele = HttpUtils.HttpClientDelete(path);
				Log.i("ssss", jsonDele+"zhou");
				//slideAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onScrollListener(int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollListenerBottom() {
		// TODO Auto-generated method stub
		
	}
   }