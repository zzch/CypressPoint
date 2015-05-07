package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.SelectSession1Adapter;
import cn.com.zcty.ILovegolf.activity.adapter.SelectSessionTAdapter;
import cn.com.zcty.ILovegolf.activity.view.competition.CompetitionNewActivity;
import cn.com.zcty.ILovegolf.activity.view.competition.CompetitionScordActivity;
import cn.com.zcty.ILovegolf.model.Courses;
import cn.com.zcty.ILovegolf.model.QiuChangList;
import cn.com.zcty.ILovegolf.tools.MyApplication;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CreateMatchActivity extends Activity {
	private String addres[];
	private String flase = "1";
	private String onResultuuid;
	private BroadcastReceiver broadcastReceiver;
	private List<QiuChangList> qiuchanglists;
	private int REQUSTCODE = 1;
	private	String address;
	public static String LOCATION_BCR = "location_bcr";
	private TextView fangshiTextView;
	private TextView easyTextView;
	private TextView majorTextView;
	private TextView qiuchang_name;
	private TextView qiudongTextView;
	private TextView zichangTextView;
	private TextView titaiTextView;
	private TextView qiudongTextView_2;
	private TextView zichangTextView_2;
	private TextView titaiTextView_2;
	private ListView selectSessionListView;
	private ListView selectSession_tListView;
	private ListView selectSession_2ListView;
	private ListView selectSession_t_2ListView;
	private ImageView onclickimage;
	private String pitchname;
	private ArrayList<String> nameArrayList = new ArrayList<String>();
	private ArrayList<String> name_2ArrayList = new ArrayList<String>();
	private ArrayList<Integer> diamodDong_2 = new ArrayList<Integer>();
	private ArrayList<String> diamond = new  ArrayList<String>();
	private ArrayList<String> color = new ArrayList<String>();
	private ArrayList<Integer> diamodDong = new ArrayList<Integer>();
	private ArrayList<String> diamond_t = new ArrayList<String>();
	private ArrayList<String> uuids = new ArrayList<String>();
	private RelativeLayout diamondRelativeLayout;
	private RelativeLayout selectSession;
	private RelativeLayout selectSession_t;
	private RelativeLayout selectSession_2;
	private RelativeLayout selectSession_t_2;
	private RelativeLayout jifenfangshi;
	private RelativeLayout leixing_layout;
	private ImageView imageView1;
	private String tiTai[]={"红色T台","白色T台","蓝色T台","黑色T台","金色T台"};
	private String tee_boxes;//T台颜色
	private String uuid;
	private boolean f = false;
	private String id_1;
	private String id_2;
	private String t_1;
	private String t_2;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				getData();
				onclckLister();
				qiuchang_name.setText(pitchname);
			}
			/*if(msg.what==2){
				Intent intent = new Intent(CompetitionNewActivity.this,CompetitionScordActivity.class);
				intent.putExtra("data", uuid);
				startActivity(intent);
			}*/
		}


	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_creatematch);
		MyApplication.getInstance().requestLocationInfo();
		initialize();
		initView();
		setListeners();

	}
	/*
	 *选择记分方式 
	 */
	private void setListeners() {

		jifenfangshi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(leixing_layout.getVisibility()==View.VISIBLE){
					leixing_layout.setVisibility(View.GONE);
					imageView1.setImageResource(R.drawable.image_icon_up);
				}else{
					leixing_layout.setVisibility(View.VISIBLE);
					imageView1.setImageResource(R.drawable.image_icon);
				}
			}
		});

		easyTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				leixing_layout.setVisibility(View.GONE);
				fangshiTextView.setText(easyTextView.getText().toString());
				imageView1.setImageResource(R.drawable.image_icon_up);
			}
		});
		majorTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				leixing_layout.setVisibility(View.GONE);
				fangshiTextView.setText(majorTextView.getText().toString());
				imageView1.setImageResource(R.drawable.image_icon_up);
			}
		});
	}

	public void initView(){
		fangshiTextView = (TextView) findViewById(R.id.creatematch_fangshi);
		easyTextView = (TextView) findViewById(R.id.easy);
		majorTextView = (TextView) findViewById(R.id.major);
		qiuchang_name = (TextView) findViewById(R.id.qiuchang_name);
		onclickimage = (ImageView) findViewById(R.id.onclickimage);
		selectSession = (RelativeLayout) findViewById(R.id.competition_selection_relative);
		selectSession_t = (RelativeLayout) findViewById(R.id.competition_selection_t);
		selectSession_2 = (RelativeLayout) findViewById(R.id.competition_selection_relative_2);
		selectSession_t_2 = (RelativeLayout) findViewById(R.id.competition_selection_t_2);
		selectSessionListView = (ListView) findViewById(R.id.competition_listview_qiuchang);
		qiudongTextView = (TextView) findViewById(R.id.competition_match_zichang);
		zichangTextView = (TextView) findViewById(R.id.competition_match_chang);
		titaiTextView = (TextView) findViewById(R.id.competition_t_name);
		qiudongTextView_2 = (TextView) findViewById(R.id.competition_match_zichang_2);
		zichangTextView_2 = (TextView) findViewById(R.id.competition_match_chang_2);
		titaiTextView_2 = (TextView) findViewById(R.id.competition_t_name_2);
		diamondRelativeLayout = (RelativeLayout) findViewById(R.id.creatematch_select_diamond);
		selectSession_tListView = (ListView) findViewById(R.id.competition_listview_t);
		selectSession_2ListView = (ListView) findViewById(R.id.competition_listview_qiuchang_2);
		selectSession_t_2ListView = (ListView) findViewById(R.id.competition_listview_t_2);
		selectSessionListView.setVisibility(View.VISIBLE);

		jifenfangshi = (RelativeLayout) findViewById(R.id.jifenfangshi);
		leixing_layout = (RelativeLayout) findViewById(R.id.leixing_layout);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView1.setImageResource(R.drawable.image_icon);
	}
	private void initialize()
	{
		registerBroadCastReceiver();
	}

	public void onclckLister(){
		/*
		 * 
		 */
		diamondRelativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CreateMatchActivity.this,ChoosePitchActivity.class);
				startActivityForResult(intent, REQUSTCODE);
			}
		});

		


		/*
		 * 选择前面子场
		 */
		selectSession.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("selectSession","----");
				selectSession_tListView.setVisibility(View.GONE);
				if(selectSessionListView.getVisibility()==View.GONE){
					selectSessionListView.setVisibility(View.VISIBLE);
					Log.i("selectSession","====");
				}else{
					selectSessionListView.setVisibility(View.GONE);
					Log.i("selectSession","++++++");
				}
				selectSession_2.setVisibility(View.GONE);
				selectSession_t.setVisibility(View.GONE);
				selectSession_t_2.setVisibility(View.GONE);
				zichangTextView.setText("");
				titaiTextView.setText("");
				qiudongTextView_2.setText("选择球场");
				zichangTextView_2.setText("");
				titaiTextView_2.setText("");
			}
		});



		selectSessionListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				qiudongTextView.setText("前"+diamodDong.get(position)+"洞");
				zichangTextView.setText(nameArrayList.get(position));
				id_1 = uuids.get(position);
				selectSession_t.setVisibility(View.VISIBLE);
				selectSession_tListView.setVisibility(View.VISIBLE);
				selectSessionListView.setVisibility(View.GONE);
				if(diamodDong.get(position)==9){
					f = true;
				}else{
					f = false;
				}
			}
		});
		/*
		 * 选择前面的T台
		 */
		selectSession_t.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(selectSession_tListView.getVisibility()==View.GONE){
					selectSession_tListView.setVisibility(View.VISIBLE);

				}else{
					selectSession_tListView.setVisibility(View.GONE);
				}
			}
		});
		selectSession_tListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				titaiTextView.setText(tiTai[position]);
				selectSession_tListView.setVisibility(View.GONE);
				t_1 = color.get(position);
				/*
				 * 判断是9个洞还是18个洞
				 * 如果是18个洞则不显示后面的选择子场
				 */
				if(f){
					selectSession_2.setVisibility(View.VISIBLE);
					selectSession_2ListView.setVisibility(View.VISIBLE);
				}else{
					selectSession_2.setVisibility(View.GONE);
					selectSession_2ListView.setVisibility(View.GONE);
					id_2=null;
					t_2=null;
				}

			}
		});

		/*
		 * 选择后面子场
		 */
		selectSession_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectSession_t_2ListView.setVisibility(View.GONE);
				if(selectSession_2ListView.getVisibility()==View.GONE){
					selectSession_2ListView.setVisibility(View.VISIBLE);
				}else{
					selectSession_2ListView.setVisibility(View.GONE);
				}
			}
		});
		//后9洞
		selectSession_2ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				qiudongTextView_2.setText("后"+diamodDong_2.get(position)+"洞");
				zichangTextView_2.setText(name_2ArrayList.get(position));
				selectSession_t_2.setVisibility(View.VISIBLE);
				selectSession_t_2ListView.setVisibility(View.VISIBLE);
				selectSession_2ListView.setVisibility(View.GONE);
				id_2 = uuids.get(position);
			}
		});
		/*
		 * 选择后面的T台
		 */
		selectSession_t_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(selectSession_t_2ListView.getVisibility()==View.GONE){
					selectSession_t_2ListView.setVisibility(View.VISIBLE);
				}else{
					selectSession_t_2ListView.setVisibility(View.GONE);
				}
			}
		});
		selectSession_t_2ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				titaiTextView_2.setText(tiTai[position]);
				selectSession_t_2ListView.setVisibility(View.GONE);
				t_2 = color.get(position);

			}
		});
	}

	/**
	 * 接受返回值
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUSTCODE){
			if(resultCode==0){
				//返回球场name，并且显示
				pitchname = data.getStringExtra("name");
				qiuchang_name.setText(pitchname);
				onResultuuid = data.getStringExtra("uuid");
				flase = data.getStringExtra("false");
			}
		}
	}

	/**
	 * 找到球场信息
	 * @author Administrator
	 *
	 */
	class Mytask extends Thread{

		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			//取球场信息uuid的值
			Intent intent=getIntent();
			String uuid=intent.getStringExtra("uuid");
			//用户的token
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");

			//根据球场信息的uuid来获取该球场的具体信息的访问url
			String path=APIService.NEAREST_NEAREST+"longitude="+addres[1]+"&latitude="+addres[0]+"&token="+token;			
			String jsonData=HttpUtils.HttpClientGet(path);
			try {
				JSONObject jsonObj=new JSONObject(jsonData);
				pitchname = jsonObj.getString("name");
				Log.i("json", "json----"+jsonObj);
				JSONArray subArray=jsonObj.getJSONArray("courses");

				for(int j=0;j<subArray.length();j++){
					JSONObject jsonobj=subArray.getJSONObject(j); 
					nameArrayList.add(jsonobj.getString("name"));
					Log.i("name", jsonobj.getString("name")+"a");
					diamond.add(jsonobj.getString("name")+"场("+jsonobj.getString("holes_count")+"洞)");

					if(Integer.parseInt(jsonobj.getString("holes_count"))==9){					
						diamond_t.add(jsonobj.getString("name")+"场("+jsonobj.getString("holes_count")+"洞)");
						name_2ArrayList.add(jsonobj.getString("name"));
						diamodDong_2.add(Integer.parseInt(jsonobj.getString("holes_count")));
					}
					diamodDong.add(Integer.parseInt(jsonobj.getString("holes_count")));
					JSONArray jj = jsonobj.getJSONArray("tee_boxes");
					for(int i=0;i<jj.length();i++){
						color.add(jj.getString(i));
						Log.i("color", ""+color);
					}
					uuids.add(jsonobj.getString("uuid"));
				}
				Message  msg = handler.obtainMessage();
				msg.what=1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}
	/**
	 * 利用返回的uuid 找到球场信息
	 */
	class OnResultMytask extends Thread{

		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){

			//用户的token
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");

			//根据球场信息的uuid来获取该球场的具体信息的访问url
			String path=APIService.DIAMONDINFORMATION+"uuid="+onResultuuid+"&token="+token;			
			Log.i("zhouzhzou", path);
			String jsonData=HttpUtils.HttpClientGet(path);
			try {
				JSONObject jsonObj=new JSONObject(jsonData);
				pitchname = jsonObj.getString("name");
				Log.i("json", "json----"+jsonObj);
				JSONArray subArray=jsonObj.getJSONArray("courses");

				for(int j=0;j<subArray.length();j++){
					JSONObject jsonobj=subArray.getJSONObject(j); 
					nameArrayList.add(jsonobj.getString("name"));
					Log.i("name", jsonobj.getString("name")+"a");
					diamond.add(jsonobj.getString("name")+"场("+jsonobj.getString("holes_count")+"洞)");

					if(Integer.parseInt(jsonobj.getString("holes_count"))==9){					
						diamond_t.add(jsonobj.getString("name")+"场("+jsonobj.getString("holes_count")+"洞)");
						name_2ArrayList.add(jsonobj.getString("name"));
						diamodDong_2.add(Integer.parseInt(jsonobj.getString("holes_count")));
					}
					diamodDong.add(Integer.parseInt(jsonobj.getString("holes_count")));
					JSONArray jj = jsonobj.getJSONArray("tee_boxes");
					for(int i=0;i<jj.length();i++){
						color.add(jj.getString(i));
						Log.i("color", ""+color);
					}
					uuids.add(jsonobj.getString("uuid"));
				}
				Message  msg = handler.obtainMessage();
				msg.what=1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}
	private void getData() {
		selectSessionListView.setAdapter(new SelectSession1Adapter(this, diamond));
		selectSession_tListView.setAdapter(new SelectSessionTAdapter(this,color));
		selectSession_2ListView.setAdapter(new SelectSession1Adapter(this, diamond_t));
		selectSession_t_2ListView.setAdapter(new SelectSessionTAdapter(this,color));
		if(diamond.size()>=1){

			int itemHeight = 13;
			itemHeight = itemHeight+5;
			setListViewHeightBasedOnChildren(selectSessionListView,itemHeight);

		}
		if(diamond_t.size()>=1){

			int itemHeight = 13;
			itemHeight = itemHeight+5;
			setListViewHeightBasedOnChildren(selectSession_2ListView,itemHeight);

		}
		if(color.size()>=1){
			int itemHeight = 20;
			setListViewHeightBasedOnChildren(selectSession_tListView,itemHeight);
			setListViewHeightBasedOnChildren(selectSession_t_2ListView,itemHeight);
		}

	};

	//定义函数动态控制listView的高度
	public void setListViewHeightBasedOnChildren(ListView listView,int itemHeight) {


		//获取listview的适配器
		ListAdapter listAdapter = listView.getAdapter();
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
				diamond.clear();
				if(!flase.equals("0")){					
					new Mytask().start();
				}
			}
		};
		IntentFilter intentToReceiveFilter = new IntentFilter(LOCATION_BCR);

		registerReceiver(broadcastReceiver, intentToReceiveFilter);
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		diamond.clear();
		if(flase.equals("0")){
			leixing_layout.setVisibility(View.VISIBLE);
			selectSessionListView.setVisibility(View.VISIBLE);
			qiudongTextView.setText("选择子场");
			zichangTextView.setText("");
			titaiTextView.setText("");
			qiudongTextView_2.setText("选择球场");
			zichangTextView_2.setText("");
			titaiTextView_2.setText("");
			new OnResultMytask().start();
		}else{
			new Mytask().start();
		}
	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}


}
