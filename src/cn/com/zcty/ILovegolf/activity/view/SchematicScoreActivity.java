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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.QuickScoreAdapter;
import cn.com.zcty.ILovegolf.model.Course;
import cn.com.zcty.ILovegolf.model.QuickContent;
import cn.com.zcty.ILovegolf.tools.XListView;
import cn.com.zcty.ILovegolf.tools.XListView.IXListViewListener;
import cn.com.zcty.ILovegolf.tools.XListView.RemoveListener;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

/**
 * 专业积分卡
 * @author deii
 *
 */
public class SchematicScoreActivity extends Activity implements IXListViewListener ,RemoveListener,OnItemClickListener{
	private XListView mListView;
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
	private String result = "shibai";
	private TextView titleName;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){


				getData();
				//hideProgressDialog();
				Log.i("shuzhijieguo", quickArrayList.size()+"");
				if(quickArrayList.size()!=0){
					image_tishi.setVisibility(View.INVISIBLE);
					mListView.setVisibility(View.VISIBLE);
				}else{
					image_tishi.setVisibility(View.VISIBLE);
					mListView.setVisibility(View.GONE);
				}
			}
			hideProgressDialog();
			if(msg.what==2){
				if(result.equals("success")){
				Toast.makeText(SchematicScoreActivity.this, "删除成功", Toast.LENGTH_LONG).show();
				slideAdapter.notifyDataSetChanged();
			}else{
				Toast.makeText(SchematicScoreActivity.this, "删除失败,当前网络不稳定", Toast.LENGTH_LONG).show();
				image_tishi.setVisibility(View.INVISIBLE);
				mListView.setVisibility(View.VISIBLE);
			}
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
	}
	private void initView() {
		mListView = (XListView) findViewById(R.id.xListView);
		mListView.setXListViewListener(this);
		image_tishi = (ImageView) findViewById(R.id.tishi);
		titleName = (TextView) findViewById(R.id.textView1);
		titleName.setText("专业记分卡");
	}
	private void getData() {
		slideAdapter = new QuickScoreAdapter(this, quickArrayList,nameArrayList);
		mListView.setAdapter(slideAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setPullLoadEnable(true);
		mListView.setRemoveListener(this);
		mListView.setPullLoadEnable(false);
		

	}
	//点击事件
	public void onclick(View v){

		switch(v.getId()){
		//返回按钮
		case R.id.k_back:
			intent=new Intent(SchematicScoreActivity.this,TabHostActivity.class);
			startActivity(intent);
			finish();	
			break;
			//新建按钮
		case R.id.k_build:
			intent=new Intent(SchematicScoreActivity.this,MajorChoosePitchActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		SharedPreferences ss = getSharedPreferences("name", MODE_PRIVATE);
		SharedPreferences.Editor editor = ss.edit();
		editor.putString("name", quickArrayList.get(position-1).getCourse().get(position-1).getName());
		editor.commit();
		intent =new Intent(SchematicScoreActivity.this,MajorScoreCardActivity.class);
		//intent传值
		intent.putExtra("uuid", quickArrayList.get(position-1).getUuid());		
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.remain_original_location);
		finish();
	}
	@Override
	public void removeItem(int position) {
		mListView.isSlide = false;
		mListView.itemView.findViewById(R.id.tv_coating).setVisibility(View.VISIBLE);
		quickArrayList.remove(position-1);
		new MyTaskDele(uuidArrayList.get(position-1)).start();
		slideAdapter.notifyDataSetChanged();
		if(quickArrayList.size()!=0){
			image_tishi.setVisibility(View.INVISIBLE);
			mListView.setVisibility(View.VISIBLE);
		}else{
			image_tishi.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.GONE);
		}
		
	}
	@Override
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
	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
	@Override
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
			path = APIService.MATCHES_LIST+"page="+page+"&token="+token+"&scoring_type=professional";
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
                     Log.i("cc", "cc----"+quickContent.getRecorded_scorecards_count());
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
			handler.sendMessage(msg);
		}
	}
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

}
