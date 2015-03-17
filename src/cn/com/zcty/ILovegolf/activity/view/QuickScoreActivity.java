package cn.com.zcty.ILovegolf.activity.view;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.exercise.adapter.QuickScoreAdapter;
import cn.com.zcty.ILovegolf.model.QuickContent;
import cn.com.zcty.ILovegolf.tools.XListView;
import cn.com.zcty.ILovegolf.tools.XListView.IXListViewListener;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.JsonUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
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
public class QuickScoreActivity extends Activity implements IXListViewListener {
    
    private  XListView mListView; //listView控件
    private  Intent intent;
    private List<QuickContent> quickContent=new ArrayList<QuickContent>();           
    private Handler mHandler;
    private LinearLayout image_layout;
    private LinearLayout XListView_layout;
    private  int pag=1;
    private String path;
    private Button cancel;
    private Button dialog_ok;
    private AlertDialog dialog;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_quick_score);
		init();
		mListView = (XListView) findViewById(R.id.xListView);
		image_layout = (LinearLayout) findViewById(R.id.image_layout);
		XListView_layout = (LinearLayout) findViewById(R.id.XListView_layout);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
	
		//listview子条目的点击事件
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "点击了这一行", Toast.LENGTH_SHORT).show();
			}
		});
		/*mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("wwwww", "listview_item-----------------------------");
				Toast.makeText(getApplicationContext(), "点击了这一行", Toast.LENGTH_SHORT).show();
				intent =new Intent(QuickScoreActivity.this,HistoryScoreCardActivity.class);
				//intent传值
				intent.putExtra("uuid", quickContent.get(position).getUuid());
				startActivity(intent);
				finish();
			}
		});*/
		
	}
   
    /**
     * 为listView设置刷新和加载的方法
     */
    private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
    
    /**
     * 下拉刷新
     */
    @Override
   	public void onRefresh() {
   		// TODO Auto-generated method stub
    	mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				init();
				onLoad();
			}
    	}, 2000);
   	}
     /**
      * 上拉加载
      */
   	@Override
   	public void onLoadMore() {
   		// TODO Auto-generated method stub
   		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mListView.setAdapter(new QuickScoreAdapter(QuickScoreActivity.this,quickContent));
				onLoad();
			}
		}, 2000);
   	}
    
    /**
     * 从服务器获取数据
     */
    public void init(){
    	new AsyncTask<Void, Void, Void>(){
    		
			
			
			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
				String page=Integer.toHexString(pag);
				String token=sp.getString("token", "token");
				Log.i("token------>>", ""+token);
				
				try {
					path = APIService.MATCHES_LIST+"page="+URLEncoder.encode(page,"utf-8")+"&token="+URLEncoder.encode(token, "utf-8");
					quickContent=JsonUtil.getQuickScore_json(path);
					Log.i("path---->>>", ""+path);
					Log.i("page----->>>", "page--->>>"+page);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				mListView.setAdapter(new QuickScoreAdapter(QuickScoreActivity.this,quickContent));
				super.onPostExecute(result);	
			}
    	}.execute();
    }	 
   
    public void fristdialog(){
    	AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.dialog_delete, null);
		cancel=(Button) view.findViewById(R.id.cancel);
		dialog_ok=(Button) view.findViewById(R.id.dialog_ok);
		//取消
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
			}
		});
		//好
		dialog_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
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
   }