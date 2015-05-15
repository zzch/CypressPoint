package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.SelectSession1Adapter;
import cn.com.zcty.ILovegolf.activity.adapter.SelectSessionTAdapter;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.model.QiuChangList;
import cn.com.zcty.ILovegolf.tools.MyApplication;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class CreateMatchActivity extends Activity {
	private String scoring_type = "null";
	private String course_uuids = "null";
	private String course_uuids_2 = "null";
	private String tee= "null";
	private String tee_2 = "null";

	private String addres[];
	private String flase = "1";
	private String onResultuuid;
	private BroadcastReceiver broadcastReceiver;
	private List<QiuChangList> qiuchanglists;
	private int REQUSTCODE = 1;
	private	String address;
	public static String LOCATION_BCR = "location_bcr";
	private Button create_fanhui;
	private String id;
	private TextView fangshiTextView;
	private TextView easyTextView;
	private TextView majorTextView;
	private View v1;
	private View v2;
	private View v_2;
	private View v_3;
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
	private RelativeLayout majorRelativeLayout;
	private RelativeLayout diamondRelativeLayout;
	private RelativeLayout selectSession;
	private RelativeLayout selectSession_t;
	private RelativeLayout selectSession_2;
	private RelativeLayout selectSession_t_2;
	private RelativeLayout jifenfangshi;
	private RelativeLayout leixing_layout;
	private ImageView imageView1;

	private ImageView imageView2;
	private ImageView imageView3;
	private ImageView imageView4;
	private ImageView imageView5;
	private ImageView titaicolor_1;
	private ImageView titaicolor_2;
	private Button startButton;
	private String tiTai[]={"红色T台","白色T台","蓝色T台","黑色T台","金色T台"};
	private int tiTaiColor[]={R.drawable.e_red,R.drawable.e_white,R.drawable.e_blue,R.drawable.e_black,R.drawable.e_gold};
	private String tee_boxes;//T台颜色
	private String uuid;
	private boolean f = false;
	private String id_1;
	private String id_2;
	private String t_1;
	private String t_2;
	private SelectSession1Adapter diamondAdapter;
	private SelectSessionTAdapter colorAdapter;
	private SelectSession1Adapter diamond2Adapter;
	private SelectSessionTAdapter color2Adapter;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(CreateMatchActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(CreateMatchActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(CreateMatchActivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
				
				getData();
				onclckLister();
				qiuchang_name.setText(pitchname);
				/*diamondAdapter.notifyDataSetChanged();
				colorAdapter.notifyDataSetChanged();
				diamond2Adapter.notifyDataSetChanged();
				color2Adapter.notifyDataSetChanged();*/
				}
			}
			if(msg.what==2){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(CreateMatchActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(CreateMatchActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(CreateMatchActivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
				//正常情况下
					Intent intent = new Intent(CreateMatchActivity.this,CreateScoreCard.class);
					intent.putExtra("uuid", id);
					intent.putExtra("scoring_type", scoring_type);	
					intent.putExtra("name", pitchname);
					startActivity(intent);
					finish();
				}
			}

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
					majorRelativeLayout.setVisibility(View.GONE);
					v1.setVisibility(View.GONE);
					v2.setVisibility(View.GONE);
					imageView1.setImageResource(R.drawable.image_down);
				}else{
					v1.setVisibility(View.VISIBLE);
					v2.setVisibility(View.VISIBLE);
					leixing_layout.setVisibility(View.VISIBLE);
					majorRelativeLayout.setVisibility(View.VISIBLE);
					imageView1.setImageResource(R.drawable.image_up);
				}
			}
		});

		leixing_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				v1.setVisibility(View.GONE);
				v2.setVisibility(View.GONE);
				leixing_layout.setVisibility(View.GONE);
				majorRelativeLayout.setVisibility(View.GONE);
				fangshiTextView.setText(easyTextView.getText().toString());
				imageView1.setImageResource(R.drawable.image_down);
			}
		});
		majorRelativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				v1.setVisibility(View.GONE);
				v2.setVisibility(View.GONE);
				leixing_layout.setVisibility(View.GONE);
				majorRelativeLayout.setVisibility(View.GONE);
				fangshiTextView.setText(majorTextView.getText().toString());
				imageView1.setImageResource(R.drawable.image_down);
			}
		});
	}

	public void initView(){
		v_2 = findViewById(R.id.view_2);
		v_2.setVisibility(View.GONE);
		v_3 = findViewById(R.id.view_3);
		v_3.setVisibility(View.GONE);
		v1 = findViewById(R.id.v1);
		v2 = findViewById(R.id.v2);
		startButton = (Button) findViewById(R.id.start);
		fangshiTextView = (TextView) findViewById(R.id.creatematch_fangshi);
		easyTextView = (TextView) findViewById(R.id.easy);
		majorTextView = (TextView) findViewById(R.id.major);
		qiuchang_name = (TextView) findViewById(R.id.qiuchang_name);
		
		selectSession = (RelativeLayout) findViewById(R.id.competition_selection_relative);
		selectSession_t = (RelativeLayout) findViewById(R.id.competition_selection_t);
		selectSession_2 = (RelativeLayout) findViewById(R.id.competition_selection_relative_2);
		selectSession_t_2 = (RelativeLayout) findViewById(R.id.competition_selection_t_2);
		selectSessionListView = (ListView) findViewById(R.id.competition_listview_qiuchang);
		qiudongTextView = (TextView) findViewById(R.id.competition_match_zichang);
		zichangTextView = (TextView) findViewById(R.id.competition_match_chang);
		titaiTextView = (TextView) findViewById(R.id.competition_t_name);
	    titaicolor_1 = (ImageView) findViewById(R.id.titaicolor_1);
		qiudongTextView_2 = (TextView) findViewById(R.id.competition_match_zichang_2);
		zichangTextView_2 = (TextView) findViewById(R.id.competition_match_chang_2);
		titaiTextView_2 = (TextView) findViewById(R.id.competition_t_name_2);
		titaicolor_2 = (ImageView) findViewById(R.id.titaicolor_2);
		diamondRelativeLayout = (RelativeLayout) findViewById(R.id.creatematch_select_diamond);
		selectSession_tListView = (ListView) findViewById(R.id.competition_listview_t);
		selectSession_2ListView = (ListView) findViewById(R.id.competition_listview_qiuchang_2);
		selectSession_t_2ListView = (ListView) findViewById(R.id.competition_listview_t_2);
		selectSessionListView.setVisibility(View.VISIBLE);

		jifenfangshi = (RelativeLayout) findViewById(R.id.jifenfangshi);
		leixing_layout = (RelativeLayout) findViewById(R.id.leixing_layout);
		majorRelativeLayout = (RelativeLayout) findViewById(R.id.creatematch_major);

		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView3 = (ImageView) findViewById(R.id.imageView3);
		imageView4 = (ImageView) findViewById(R.id.imageView4);
		imageView5 = (ImageView) findViewById(R.id.imageView5);
		create_fanhui = (Button) findViewById(R.id.create_fanhui);

	}
	private void initialize()
	{
		registerBroadCastReceiver();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(CreateMatchActivity.this,QuickScoreActivity.class);
		startActivity(intent);
		finish();
	}
	public void onclckLister(){

		/*
		 * 创建比赛
		 */
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(fangshiTextView.getText().toString().equals("简单")){
					scoring_type = "simple";
				}else if(fangshiTextView.getText().toString().equals("专业")){
					scoring_type = "professional";
				}

				if(f){
					//如果是前9洞
					if(scoring_type.equals("null")
							||course_uuids.equals("null")
							||tee.equals("null")
							||course_uuids_2.equals("null")
							||tee_2.equals("null")){
						startButton.setBackgroundColor(0xff796941);
						startButton.setTextColor(0xff191b1d);
						Toast.makeText(CreateMatchActivity.this
								, "请把信息选择完整", Toast.LENGTH_LONG).show();
					}else{
						new CreteMatch().start();
						
					}
				}else{
					//如果是前18洞
					if(scoring_type.equals("null")
							||course_uuids.equals("null")
							||tee.equals("null")){
						startButton.setBackgroundColor(0xff796941);
						startButton.setTextColor(0xff191b1d);
						Toast.makeText(CreateMatchActivity.this
								, "请把信息选择完整", Toast.LENGTH_LONG).show();
					}else{
						new CreteMatch().start();	
						
					}
				}
			}
		});

		create_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CreateMatchActivity.this,QuickScoreActivity.class);
				startActivity(intent);
				finish();
			}
		});



		/*
		 * 手动选择球场
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
				v_2.setVisibility(View.GONE);
				v_3.setVisibility(View.GONE);
				selectSession_tListView.setVisibility(View.GONE);
				if(selectSessionListView.getVisibility()==View.GONE){
					selectSessionListView.setVisibility(View.VISIBLE);

					imageView2.setImageResource(R.drawable.image_up);

					Log.i("selectSession","====");
				}else{
					selectSessionListView.setVisibility(View.GONE);
					imageView2.setImageResource(R.drawable.image_down);
					Log.i("selectSession","++++++");

				}
				titaicolor_1.setVisibility(View.GONE);
				titaicolor_2.setVisibility(View.GONE);
				selectSession_t.setVisibility(View.GONE);
				selectSession_2.setVisibility(View.GONE);
				selectSession_t_2.setVisibility(View.GONE);
				selectSession_t_2ListView.setVisibility(View.GONE);
				selectSession_2ListView.setVisibility(View.GONE);
				zichangTextView.setText("");
				titaiTextView.setText("");
				titaicolor_1.setVisibility(View.GONE);
				startButton.setBackgroundColor(0xff796941);
				startButton.setTextColor(0xff191b1d);
				qiudongTextView_2.setText("选择球场");
				zichangTextView_2.setText("");
				titaiTextView_2.setText("");


				tee = "null";
				course_uuids = "null";
				tee_2 = "null";

			}
		});



		selectSessionListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				course_uuids = uuids.get(position);
				v_2.setVisibility(View.VISIBLE);
				v_3.setVisibility(View.VISIBLE);
				qiudongTextView.setText("前"+diamodDong.get(position)+"洞");
				zichangTextView.setText(nameArrayList.get(position));
				id_1 = uuids.get(position);
				selectSession_t.setVisibility(View.VISIBLE);
				selectSession_tListView.setVisibility(View.VISIBLE);
				selectSessionListView.setVisibility(View.GONE);
				imageView2.setImageResource(R.drawable.image_down);
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
				v_3.setVisibility(View.GONE);
				if(selectSession_tListView.getVisibility()==View.GONE){
					selectSession_tListView.setVisibility(View.VISIBLE);
					imageView3.setImageResource(R.drawable.image_up);
					
				}else{
					selectSession_tListView.setVisibility(View.GONE);
					imageView3.setImageResource(R.drawable.image_down);
				}
			}
		});
		selectSession_tListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				imageView3.setImageResource(R.drawable.image_down);
				titaicolor_1.setVisibility(View.VISIBLE);
				tee = color.get(position);
				titaiTextView.setText(tiTai[position]);
				titaicolor_1.setImageResource(tiTaiColor[position]);
				selectSession_tListView.setVisibility(View.GONE);
				startButton.setBackgroundColor(0xfff8d57a);
				startButton.setTextColor(0xff222222);
				t_1 = color.get(position);
				/*
				 * 判断是9个洞还是18个洞
				 * 如果是18个洞则不显示后面的选择子场
				 */
				if(f){
					v_3.setVisibility(View.VISIBLE);
					selectSession_2.setVisibility(View.VISIBLE);
					selectSession_2ListView.setVisibility(View.VISIBLE);
				}else{
					v_3.setVisibility(View.GONE);
					selectSession_2.setVisibility(View.GONE);
					selectSession_2ListView.setVisibility(View.GONE);
					imageView3.setImageResource(R.drawable.image_down);
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
				selectSession_t_2.setVisibility(View.GONE);
				selectSession_t_2ListView.setVisibility(View.GONE);
				if(selectSession_2ListView.getVisibility()==View.GONE){
					selectSession_2ListView.setVisibility(View.VISIBLE);
					imageView4.setImageResource(R.drawable.image_up);
				}else{
					selectSession_2ListView.setVisibility(View.GONE);
					imageView4.setImageResource(R.drawable.image_down);
				}
			}
		});
		//后9洞
		selectSession_2ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				course_uuids_2 = uuids.get(position);
				qiudongTextView_2.setText("后"+diamodDong_2.get(position)+"洞");
				zichangTextView_2.setText(name_2ArrayList.get(position));
				selectSession_t_2.setVisibility(View.VISIBLE);
				selectSession_t_2ListView.setVisibility(View.VISIBLE);
				selectSession_2ListView.setVisibility(View.GONE);
				imageView4.setImageResource(R.drawable.image_down);
				startButton.setBackgroundColor(0xfff8d57a);
				startButton.setTextColor(0xff222222);
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
					imageView5.setImageResource(R.drawable.image_up);
				}else{
					selectSession_t_2ListView.setVisibility(View.GONE);
					imageView5.setImageResource(R.drawable.image_down);
				}
			}
		});
		selectSession_t_2ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				titaicolor_2.setVisibility(View.VISIBLE);
				tee_2 = color.get(position);
				titaiTextView_2.setText(tiTai[position]);
				titaicolor_2.setImageResource(tiTaiColor[position]);
				selectSession_t_2ListView.setVisibility(View.GONE);
				imageView5.setImageResource(R.drawable.image_down);
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
				if(!data.getStringExtra("name").equals("null")){
					pitchname = data.getStringExtra("name");
					qiuchang_name.setText(pitchname);
					onResultuuid = data.getStringExtra("uuid");
					flase = data.getStringExtra("false");
				}else{

				}


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
				msg.obj = jsonData;
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
			uuids.clear();
			nameArrayList.clear();
			diamodDong.clear();
			name_2ArrayList.clear();
			diamodDong_2.clear();
			diamond.clear();
			color.clear();
			//用户的token
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");

			//根据球场信息的uuid来获取该球场的具体信息的访问url
			String path=APIService.DIAMONDINFORMATION+"uuid="+onResultuuid+"&token="+token;			
			String jsonData=HttpUtils.HttpClientGet(path);
			try {
				JSONObject jsonObj=new JSONObject(jsonData);
				pitchname = jsonObj.getString("name");
				Log.i("json", "json----"+jsonObj);
				JSONArray subArray=jsonObj.getJSONArray("courses");

				for(int j=0;j<subArray.length();j++){
					JSONObject jsonobj=subArray.getJSONObject(j); 
					Log.i("name", jsonobj.getString("name")+"a");
					nameArrayList.add(jsonobj.getString("name"));
				
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
				msg.obj = jsonData;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}
	/**
	 * 创建比赛
	 * @author Administrator
	 *
	 */
	class CreteMatch extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			//用户的token
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			
			String path;
			if(course_uuids_2.equals("null")){
				//当前面为18洞的时候	
				path = APIService.CREADMATCHES+"token="+token+"&course_uuids="+course_uuids+"&tee_boxes="+tee+"&scoring_type="+scoring_type;
			}else{
				//当前面为9洞的时候
				path = APIService.CREADMATCHES+"token="+token+"&course_uuids="+course_uuids+","+course_uuids_2+"&tee_boxes="+tee+","+tee_2+"&scoring_type="+scoring_type;
			}
			Log.i("createpath", path);
			String jsonData = HttpUtils.HttpClientPost(path);
			Log.i("createpath", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				id = jsonObject.getString("uuid");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 2;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
	}
	private void getData() {
		
		diamondAdapter = new SelectSession1Adapter(this, diamond);
		colorAdapter = new SelectSessionTAdapter(this,color);
		diamond2Adapter = new SelectSession1Adapter(this, diamond_t);
		color2Adapter = new SelectSessionTAdapter(this,color);
		selectSessionListView.setAdapter(diamondAdapter);
		selectSession_tListView.setAdapter(colorAdapter);
		selectSession_2ListView.setAdapter(diamond2Adapter);
		selectSession_t_2ListView.setAdapter(color2Adapter);
		if(diamond.size()>=1){

			int itemHeight = 45;
			itemHeight = itemHeight+5;
			setListViewHeightBasedOnChildren(selectSessionListView,itemHeight);

		}
		if(diamond_t.size()>=1){

			int itemHeight = 45;
			itemHeight = itemHeight+5;
			setListViewHeightBasedOnChildren(selectSession_2ListView,itemHeight);

		}
		if(color.size()>=1){
			int itemHeight = 80;
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
				selectSession_2.setVisibility(View.GONE);
				selectSession_t_2.setVisibility(View.GONE);
				selectSession_t_2ListView.setVisibility(View.GONE);
				selectSession_2ListView.setVisibility(View.GONE);
				
				
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
			titaicolor_1.setVisibility(View.GONE);
			startButton.setBackgroundColor(0xff796941);
			startButton.setTextColor(0xff191b1d);
			qiudongTextView_2.setText("选择球场");
			zichangTextView_2.setText("");
			titaiTextView_2.setText("");
			titaicolor_2.setVisibility(View.GONE);
			startButton.setBackgroundColor(0xff796941);
			startButton.setTextColor(0xff191b1d);
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
