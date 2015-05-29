package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.RankingAdapter;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.model.Ranking;
import cn.com.zcty.ILovegolf.tools.AutoListView;
import cn.com.zcty.ILovegolf.tools.AutoListView.OnRefreshListener;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class RankingActivity extends Activity{

	private Button paiming_back;
	private AutoListView rankListView;
	private RelativeLayout invite_much;
	private Button rank_invite_but;
	private RelativeLayout layout_rank;
	private ProgressDialog progressDialog;
	private LinearLayout linear;
	private ArrayList<Ranking> rankings = new ArrayList<Ranking>();//adapter的数据
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			rankListView.onRefreshComplete();
			if(msg.what==1){
				hideProgressDialog();
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(RankingActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
					
				}else if(msg.obj.equals("403")){					
					Toast.makeText(RankingActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(RankingActivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
					linear.setVisibility(View.VISIBLE);
					getData();
				}
				
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ranking);
		initView();		
		showProgressDialog("提示", "正在加载", this);
		setListeners();	
		new Rankings().start();
	}

	/*
	 *存放监听器 
	 */
	private void setListeners() {
		// TODO Auto-generated method stub
		paiming_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		//邀请更多好友
         invite_much.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String	uuid = getIntent().getStringExtra("uuid");
				Intent j = new Intent();
				if(FileUtil.fileIsExists()){
					j.setClass(RankingActivity.this,InviteActivity.class);
				}else{
					//j = new Intent(CreateScoreCard.this,SelfhoodActivity.class);
					j.setClass(RankingActivity.this,SelfhoodActivity.class);
				}
				j.putExtra("cunzai", "0");
				j.putExtra("uuid", uuid);
				startActivity(j);
			    finish();
			}
		});
         
         //邀请好友
         rank_invite_but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String	uuid = getIntent().getStringExtra("uuid");
				Intent j = new Intent();
				if(FileUtil.fileIsExists()){
					j.setClass(RankingActivity.this,InviteActivity.class);
				}else{
					//j = new Intent(CreateScoreCard.this,SelfhoodActivity.class);
					j.setClass(RankingActivity.this,SelfhoodActivity.class);
				}
				j.putExtra("cunzai", "0");
				j.putExtra("uuid", uuid);
				startActivity(j);
			    finish();
			}
		});
		rankListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(rankings.get(position-1).getSelf().equals("true")){
					finish();
				}else{
				Intent intent = new Intent(RankingActivity.this,RankingStatics.class);
				intent.putExtra("uuid", rankings.get(position-1).getUuid());
				startActivity(intent);}
			}
		});
		
	
		rankListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new Rankings().start();
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
	/*
	 * 初始化控件
	 */
	public void initView(){
		paiming_back = (Button) findViewById(R.id.paiming_back);
		rankListView = (AutoListView) findViewById(R.id.ranking);
		invite_much = (RelativeLayout) findViewById(R.id.invite_much);
		rank_invite_but = (Button) findViewById(R.id.rank_invite_but);
		layout_rank = (RelativeLayout) findViewById(R.id.layout_rank);
		linear = (LinearLayout) findViewById(R.id.linear);
		linear.setVisibility(View.GONE);
		rankListView.setLoadEnable(true);
	}
	/*
	 * 数据的操作
	 */
	public void getData(){
		rankListView.setAdapter(new RankingAdapter(this, rankings));	
		if(!getIntent().getStringExtra("fangzhu").equals("true")){
			invite_much.setVisibility(View.GONE);
			layout_rank.setVisibility(View.GONE);
		}else{
			if(rankings.size()<1){
				
				   invite_much.setVisibility(View.GONE);
				   Log.i("-----", "sdufhshdjhkjshfjhsjkhfjhs");
				  layout_rank.setVisibility(View.VISIBLE);
			   }else{
				  layout_rank.setVisibility(View.GONE);
				  invite_much.setVisibility(View.VISIBLE);
			   }
		}
		
	}
	
	/**
	 * 获取排名的数据
	 * @author Administrator
	 *
	 */
	class Rankings extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			rankings.clear();
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			Intent intent=getIntent();
			String	uuid = intent.getStringExtra("uuid");
			String path = APIService.RANKING+"token="+token+"&match_uuid="+uuid;
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("rankingdata", path);
			Log.i("rankingdata", jsonData);
			try {
				JSONArray jsonArray = new JSONArray(jsonData);
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Ranking ranking = new Ranking();
					ranking.setUuid(jsonObject.getString("uuid"));
					ranking.setPosition(jsonObject.getString("position"));
					ranking.setSelf(jsonObject.getString("self"));
					String user = jsonObject.getString("user");//获得user map集合
					JSONObject userJsonObject = new JSONObject(user);
					Log.i("ranking", userJsonObject.getString("nickname"));
					ranking.setNickname(userJsonObject.getString("nickname"));
					
					String portrait = userJsonObject.getString("portrait");//获得头像 map集合
					JSONObject portraitJsonObject = new JSONObject(portrait);
					ranking.setPortrait(portraitJsonObject.getString("url"));
					
					ranking.setRecorded_scorecards_count(jsonObject.getString("recorded_scorecards_count"));
					ranking.setTotal(jsonObject.getString("total"));
					
					rankings.add(ranking);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what=1;
			msg.obj=jsonData;
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
