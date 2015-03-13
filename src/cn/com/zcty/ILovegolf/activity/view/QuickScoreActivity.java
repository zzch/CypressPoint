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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


/**
 * ��ݼǷ���
 * @author deii
 *
 */
public class QuickScoreActivity extends Activity implements IXListViewListener {
    
    private  XListView mListView; //listView �ؼ�
    private  Intent intent;
   // private QuickScoreAdapter quickScoreAdapter;    //������
    private List<QuickContent> quickContent=new ArrayList<QuickContent>();           //��ʷ���µ���Ϣ����
    private Handler mHandler;
    private  int pag=1;
    private  DisplayMetrics dm = new DisplayMetrics();
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_quick_score);
		init();
		mListView=(XListView) findViewById(R.id.xListView);
		//mListView.setAdapter(new QuickScoreAdapter(this,quickContent));
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		
		
	     getWindowManager().getDefaultDisplay().getMetrics(dm);
		mHandler = new Handler();
		//listview����¼���ҳ����ת���Ƿֿ�ҳ
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				intent =new Intent(QuickScoreActivity.this,ScoreCardActivity.class);
				//intent��ֵ
				intent.putExtra("uuid", quickContent.get(position).getUuid());
				startActivity(intent);
				finish();
			}
		});
		
	}
    
  
    /**
     * ΪlistView����ˢ�ºͼ��صķ���
     */
    private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("�ո�");
	}
    
    /**
     * ����ˢ��
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
      * ��������
      */
   	@Override
   	public void onLoadMore() {
   		// TODO Auto-generated method stub
   		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mListView.setAdapter(new QuickScoreAdapter(QuickScoreActivity.this,quickContent,dm.widthPixels));
				onLoad();
			}
		}, 2000);
   	}
    
    /**
     * �ӷ�������ȡ����
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
				String path;
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
				mListView.setAdapter(new QuickScoreAdapter(QuickScoreActivity.this,quickContent,dm.widthPixels));
				super.onPostExecute(result);	
			}
    	}.execute();
    }	 
   
   
    //����¼�
	public void onclick(View v){
		
		switch(v.getId()){
		//���ذ�ť
		case R.id.k_back:
			intent=new Intent(QuickScoreActivity.this,ExerciseActivity.class);
			startActivity(intent);
			finish();	
			break;
	    //�½���ť
		case R.id.k_build:
			 intent=new Intent(QuickScoreActivity.this,ChoosePitchActivity.class);
			 startActivity(intent);
			 finish();
			break;
		}
	}
   }

 
  

