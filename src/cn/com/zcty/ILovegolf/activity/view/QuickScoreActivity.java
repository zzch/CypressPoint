package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;
import java.util.List;

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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.QuickScoreAdapter;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.model.Course;
import cn.com.zcty.ILovegolf.model.QuickContent;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;


public class QuickScoreActivity extends Activity {
	private int itemHeight = 100;
	private ScrollView mScrollView;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private ListView mListView;
	private ImageView image_tishi;
	private QuickScoreAdapter slideAdapter;
	private ArrayList<QuickContent> quickArrayList = new ArrayList<QuickContent>();
	private ArrayList<String> uuidArrayList = new ArrayList<String>();
	private ArrayList<String> nameArrayList = new ArrayList<String>();
	private  Intent intent;
	private String path;
	private  int pag=1;
	private ProgressDialog progressDialog;
	private Handler mHandler;
	private String result = "shipai";
	int c = 0;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				getData();
				//slideAdapter.notifyDataSetChanged();
				mPullRefreshScrollView.onRefreshComplete();//刷新
				if(msg.obj.equals("404")||msg.obj.equals("500")){//判断是服务端问题
					Toast.makeText(QuickScoreActivity.this, "网络异常，错误提示"+msg.obj, Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(QuickScoreActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(QuickScoreActivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
					/*
					 * 如果没有数据，则出现提示添加数据的文字
					 */
					if(quickArrayList.size()!=0){
						image_tishi.setVisibility(View.INVISIBLE);
						mListView.setVisibility(View.VISIBLE);
					}else{
						image_tishi.setVisibility(View.VISIBLE);
						mListView.setVisibility(View.GONE);
					}}
			}
			/*
			 *加载成功，加载框消失 
			 */
			hideProgressDialog();
			if(msg.what==2){
				if(msg.obj.equals("404")||msg.obj.equals("500")){//判断是服务端问题
					Toast.makeText(QuickScoreActivity.this, "删除失败,当前网络异常，错误提示"+msg.obj, Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(QuickScoreActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
				}else{				
					if(result.equals("success")){
						Toast.makeText(QuickScoreActivity.this, "删除成功", Toast.LENGTH_LONG).show();
						slideAdapter.notifyDataSetChanged();
					}else{
						Toast.makeText(QuickScoreActivity.this, "删除失败,当前网络不稳定", Toast.LENGTH_LONG).show();
						image_tishi.setVisibility(View.INVISIBLE);
						mListView.setVisibility(View.VISIBLE);
					}}
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_quick_score);
		initView();	
		new MyTask().start();
		mHandler= new Handler();
		showProgressDialog("提示","正在努力加载数据！");
		setListeners();
	}
	private void setListeners() {
		/*
		 * 下拉或者上拉的时候
		 */
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

				new MyTask().start();


				

			}
		});

		mScrollView = mPullRefreshScrollView.getRefreshableView();
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		image_tishi = (ImageView) findViewById(R.id.tishi);
		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);

	}
	private void getData() {
		slideAdapter = new QuickScoreAdapter(this, quickArrayList);
		mListView.setAdapter(slideAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(QuickScoreActivity.this,CreateScoreCard.class);
				intent.putExtra("uuid", quickArrayList.get(position).getUuid());
				intent.putExtra("scoring_type", quickArrayList.get(position).getScoring_type());
				intent.putExtra("name", quickArrayList.get(position).getName());
				startActivity(intent);

			}
		});

		if(c!=quickArrayList.size()){
			for(int i=0;i<quickArrayList.size();i++){
				itemHeight = itemHeight + 2;
			}
			c = quickArrayList.size();
			setListViewHeightBasedOnChildren(mListView);
		}
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
			//创建新比赛
		case R.id.create_match:
			intent  = new Intent(QuickScoreActivity.this,CreateMatchActivity.class);
			startActivity(intent);
			finish();
			break;
			//加入比赛
		case R.id.add_match:
			intent  = new Intent(QuickScoreActivity.this,AddMatchActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}



	/*@Override
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

	}*/

	/*@Override
	public void onLoadMore() {
		//mListView.itemView.findViewById(R.id.tv_coating).setVisibility(View.VISIBLE);

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {

				quickArrayList.clear();
				slideAdapter.notifyDataSetChanged();
				onLoad();

			}
		}, 2000);


	}*/
	class MyTask extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			getData();
		}
		public void getData(){
			quickArrayList.clear();
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String page=Integer.toHexString(pag);
			String token=sp.getString("token", "token");
			Log.i("tokens", token);

			path = APIService.MATCHES_LIST+"page="+page+"&token="+token;

			String JsonData=HttpUtils.HttpClientGet(path);
			try {
				JSONArray jsonarray=new JSONArray(JsonData);
				for(int i=0;i<jsonarray.length();i++){
					JSONObject jsonObject = jsonarray.getJSONObject(i);
					QuickContent quickContent = new QuickContent();
					quickContent.setUuid(jsonObject.getString("uuid"));
					Log.i("chengjid", jsonObject.getString("uuid"));
					String venue = jsonObject.getString("venue");//获得venue 的map集合
					JSONObject venueJsonObject = new JSONObject(venue);
					quickContent.setName(venueJsonObject.getString("name"));

					String player = jsonObject.getString("player");//获得player 的map集合
					JSONObject playerJsonObject = new JSONObject(player);
					quickContent.setScoring_type(playerJsonObject.getString("scoring_type"));
					quickContent.setScore(playerJsonObject.getString("strokes"));
					quickContent.setRecorded_scorecards_count(playerJsonObject.getString("recorded_scorecards_count"));

					quickContent.setPlayers_count(jsonObject.getString("players_count"));
					quickContent.setStarted_at(jsonObject.getString("started_at"));
					quickArrayList.add(quickContent);
				}

				Message msg = handler.obtainMessage();
				msg.what = 1;
				msg.obj = JsonData;
				handler.sendMessage(msg);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/*class MyTaskDele extends Thread{
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
			String path = APIService.DELET+"uuid="+uuid+"&token="+token;
			String jsonDele = HttpUtils.HttpClientDelete(path);
			try {
				JSONObject json = new JSONObject(jsonDele);
				result = json.getString("result");

				//slideAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(quickArrayList.size()!=0){
				image_tishi.setVisibility(View.INVISIBLE);
				mListView.setVisibility(View.VISIBLE);
			}else{
				image_tishi.setVisibility(View.VISIBLE);
				mListView.setVisibility(View.GONE);
			}
			Message msg = handler.obtainMessage();
			msg.what = 2;
			msg.obj = jsonDele;
			handler.sendMessage(msg);
		}
	}*/
	/*
	 * 提示加载
	 */
	public  void  showProgressDialog(String title,String message){
		if(progressDialog==null){
			progressDialog = ProgressDialog.show(this, title, message,true,true);

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
	//定义函数动态控制listView的高度
	public void setListViewHeightBasedOnChildren(ListView listView) {


		//获取listview的适配器
		ListAdapter listAdapter = listView.getAdapter();
		//item的高度



		if (listAdapter == null) {
			return;
		}


		int totalHeight = 0;


		for (int i = 0; i < listAdapter.getCount(); i++) {
			totalHeight += Dp2Px(getApplicationContext(),itemHeight)+listView.getDividerHeight();
		}


		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params. height = totalHeight;


		listView.setLayoutParams(params);
	}
	//dp转化为px
	public int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int ) (dp * scale + 0.5f);
	}

}
