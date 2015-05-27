package cn.com.zcty.ILovegolf.activity.view.competition;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.SelectSessionTAdapter;
import cn.com.zcty.ILovegolf.activity.view.CreateScoreCard;
import cn.com.zcty.ILovegolf.activity.view.MajorScoreActivity;
import cn.com.zcty.ILovegolf.activity.view.QuickScoreActivity;
import cn.com.zcty.ILovegolf.model.CompetitionAddmatch;
import cn.com.zcty.ILovegolf.tools.CircleImageView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class CompetitionAdd extends Activity{
	
	private TextView fangzhuTextView;
	private Bitmap bitmap;
	private CircleImageView headCircleImageView;
	private Button fanhuiButton;
	private TextView qiudongTextView;
	private TextView zichangTextView;
	private TextView titaiTextView;
	private TextView qiudongTextView_2;
	private TextView zichangTextView_2;
	private TextView titaiTextView_2;
	private ProgressDialog progressDialog;
	private View v1;
	private View v2;
	private View v_2;
	private View v_4;
	private View v_3;
	private TextView fangshiTextView;
	private TextView easyTextView;
	private TextView majorTextView;
	private ImageView imageView1;
	private ImageView imageView3;
	private ImageView imageView5;
	private ImageView titaicolor_1;
	private ImageView titaicolor_2;
	private RelativeLayout majorRelativeLayout;
	private RelativeLayout jifenfangshi;
	private RelativeLayout leixing_layout;
	private ListView titaiListView;
	private ListView titai2ListView;
	private RelativeLayout qiuchangLayout;
	private RelativeLayout qiuchang2Layout;
	private RelativeLayout selectSession_t;
	private RelativeLayout selectSession_t_2;
	private Button createButton;
	private String tiTai[]={"红色T台","白色T台","蓝色T台","黑色T台","金色T台"};
	private int tiTaiColor[]={R.drawable.e_red,R.drawable.e_white,R.drawable.e_blue,R.drawable.e_black,R.drawable.e_gold};
	private boolean f = false;
	private String t_1 = "";
	private String t_2 = "";
	private CompetitionAddmatch add;
	private String uuid;
	private String flase = "no";//判断是否加入成功
	private String scoring_type ;
	private TextView playNameTextView;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				hideProgressDialog();
				if(msg.obj.equals("500")||msg.obj.equals("404")){
					Toast.makeText(CompetitionAdd.this, "网络异常，无法加入",Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(CompetitionAdd.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
				}
				if(flase.equals("success")){
					Intent intent = new Intent(CompetitionAdd.this,CreateScoreCard.class);
					intent.putExtra("uuid", uuid);
					intent.putExtra("scoring_type", scoring_type);
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(CompetitionAdd.this, "此id已在房间，不能重复加入",Toast.LENGTH_LONG).show();
				}
			}
			if(msg.what==2){
				headCircleImageView.setImageBitmap(bitmap);
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_competition_add);
		initView();
		setListeners();
		getData();
		new Imageloder().start();
		
	}
	private void getData() {
		Intent intent = getIntent();
		add = (CompetitionAddmatch) intent.getSerializableExtra("add");
		Log.i("zhouhetiancai", add.getPortrait());
		fangzhuTextView.setText(add.getUseName());
		playNameTextView.setText(add.getName());
		titaiListView.setAdapter(new SelectSessionTAdapter(this,add.getTitai().get(0).getBoxs()));
		if(add.getTitai().size()>1){
			qiudongTextView.setText("前9洞");
			qiudongTextView_2.setText("后9洞");
			selectSession_t.setVisibility(View.VISIBLE);
			selectSession_t_2.setVisibility(View.VISIBLE);
			zichangTextView_2.setText(add.getTitai().get(1).getName());
			qiuchang2Layout.setVisibility(View.VISIBLE);	
			
			titai2ListView.setAdapter(new SelectSessionTAdapter(this,add.getTitai().get(1).getBoxs()));	
			f = true;
		}else{
			f = false;
			selectSession_t.setVisibility(View.VISIBLE);
			selectSession_t_2.setVisibility(View.GONE);
			qiudongTextView.setText("球场");	
			zichangTextView.setText(add.getTitai().get(0).getName());
			qiuchang2Layout.setVisibility(View.GONE);
			
		}


	}
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
		
		
		
		fanhuiButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CompetitionAdd.this,QuickScoreActivity.class);
				startActivity(intent);
				finish();
			}
		});
		selectSession_t.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(titaiListView.getVisibility()==View.GONE){
					titaiListView.setVisibility(View.VISIBLE);
					imageView3.setImageResource(R.drawable.image_up);
					
				}else{
					titaiListView.setVisibility(View.GONE);
				
					imageView3.setImageResource(R.drawable.image_down);
				}
			}
		});
		titaiListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				v_3.setVisibility(View.VISIBLE);
				v_4.setVisibility(View.GONE);
				imageView3.setImageResource(R.drawable.image_down);
				titaicolor_1.setVisibility(View.VISIBLE);
				titaiTextView.setText(tiTai[position]);
				titaicolor_1.setImageResource(tiTaiColor[position]);
				titaiListView.setVisibility(View.GONE);
				t_1 = add.getTitai().get(0).getBoxs().get(position);
				
				
				if(f){
					titai2ListView.setVisibility(View.VISIBLE);
					v_3.setVisibility(View.VISIBLE);
					v_4.setVisibility(View.VISIBLE);
					createButton.setBackgroundColor(0xff64af66);
					createButton.setTextColor(0xffededed);
				}else{
					titai2ListView.setVisibility(View.GONE);
					v_3.setVisibility(View.GONE);
					v_4.setVisibility(View.GONE);
					t_2 = "";
					createButton.setBackgroundColor(0xff09850c);
					createButton.setTextColor(0xffffffff);
				}
			}
		});
		selectSession_t_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(titai2ListView.getVisibility()==View.GONE){
					titai2ListView.setVisibility(View.VISIBLE);
					imageView5.setImageResource(R.drawable.image_up);
				}else{
					titai2ListView.setVisibility(View.GONE);
					imageView5.setImageResource(R.drawable.image_down);
				}
			}
		});
		titai2ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				titaicolor_2.setVisibility(View.VISIBLE);
				imageView5.setImageResource(R.drawable.image_down);
				titaiTextView_2.setText(tiTai[position]);
				titaicolor_2.setImageResource(tiTaiColor[position]);
				titai2ListView.setVisibility(View.GONE);
				t_2 = add.getTitai().get(1).getBoxs().get(position);
				createButton.setBackgroundColor(0xff09850c);
				createButton.setTextColor(0xffffffff);
			}
		});
		
		createButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(f){
					if(t_1.equals("")||t_2.equals("")){
						Toast.makeText(CompetitionAdd.this, "请选择完，在加入", Toast.LENGTH_LONG).show();
					}else{
						showProgressDialog("提示", "正在加入房间");
						new Add().start();
					}
				}else{
					if(t_1.equals("")){
						Toast.makeText(CompetitionAdd.this, "请选择完，在加入", Toast.LENGTH_LONG).show();
					}else{
						showProgressDialog("提示", "正在加入房间");
						new Add().start();
					}
				}
				
			}
		});
	}
	
	private void initView() {
		headCircleImageView = (CircleImageView) findViewById(R.id.head_image);
		
		playNameTextView = (TextView) findViewById(R.id.address);
		v1 = findViewById(R.id.v1);
		v2 = findViewById(R.id.v2);
		v_2 = findViewById(R.id.view_2);
		v_3 = findViewById(R.id.view_3);
		v_4 = findViewById(R.id.view_4);

		v_3.setVisibility(View.GONE);
		v_4.setVisibility(View.GONE);
		fangshiTextView = (TextView) findViewById(R.id.username);
		
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView3 = (ImageView) findViewById(R.id.imageView3);
		imageView5 = (ImageView) findViewById(R.id.imageView5);
		
		fangshiTextView = (TextView) findViewById(R.id.creatematch_fangshi);
		easyTextView = (TextView) findViewById(R.id.easy);
		majorTextView = (TextView) findViewById(R.id.major);
		jifenfangshi = (RelativeLayout) findViewById(R.id.jifenfangshi);
		leixing_layout = (RelativeLayout) findViewById(R.id.leixing_layout);
		majorRelativeLayout = (RelativeLayout) findViewById(R.id.creatematch_major);
		fangzhuTextView = (TextView) findViewById(R.id.username);
		fanhuiButton = (Button) findViewById(R.id.button1);
		
		qiuchangLayout = (RelativeLayout) findViewById(R.id.competition_selection_relative);
		qiuchang2Layout = (RelativeLayout) findViewById(R.id.competition_selection_relative_2);
		titaiListView = (ListView) findViewById(R.id.competition_listview_t);
		titaiListView.setVisibility(View.VISIBLE);
		titai2ListView = (ListView) findViewById(R.id.competition_listview_t_2);

		titaicolor_1 = (ImageView) findViewById(R.id.titaicolor_1);
	    titaicolor_2 = (ImageView) findViewById(R.id.titaicolor_2);
		
		selectSession_t = (RelativeLayout) findViewById(R.id.competition_selection_t);
		selectSession_t_2 = (RelativeLayout) findViewById(R.id.competition_selection_t_2);
		qiudongTextView = (TextView) findViewById(R.id.competition_match_zichang);
		zichangTextView = (TextView) findViewById(R.id.competition_match_chang);
		titaiTextView = (TextView) findViewById(R.id.competition_t_name);
		qiudongTextView_2 = (TextView) findViewById(R.id.competition_match_zichang_2);
		zichangTextView_2 = (TextView) findViewById(R.id.competition_match_chang_2);
		titaiTextView_2 = (TextView) findViewById(R.id.competition_t_name_2);
		createButton = (Button) findViewById(R.id.competition_chuangjian);
		Intent intent = getIntent();
		uuid = intent.getStringExtra("uuid");
		if(fangshiTextView.getText().toString().equals("简单")){
			scoring_type = "simple";
		}else if(fangshiTextView.getText().toString().equals("专业")){
			scoring_type = "professional";
		}
		
		leixing_layout.setVisibility(View.GONE);
		majorRelativeLayout.setVisibility(View.GONE);
		v1.setVisibility(View.GONE);
		v2.setVisibility(View.GONE);
		imageView1.setImageResource(R.drawable.image_down);
		createButton.setBackgroundColor(0xff64af66);
		createButton.setTextColor(0xffededed);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(CompetitionAdd.this,QuickScoreActivity.class);
		startActivity(intent);
		finish();
	}
	/**
	 * 选择项目，参加比赛
	 * @author Administrator
	 *
	 */
	class Add extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path;
			
			
			if(f){
				 path = APIService.COMPETITIONCREAT+"token="+token+"&uuid="+uuid+"&scoring_type="+scoring_type+"&tee_boxes="+t_1+","+t_2;
			}else{
				 path = APIService.COMPETITIONCREAT+"token="+token+"&uuid="+uuid+"&scoring_type="+scoring_type+"&tee_boxes="+t_1;
			}
			String jsonData = HttpUtils.HttpClientPost(path);
			Log.i("CompeAdd", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				flase = jsonObject.getString("result");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
	}
	/**
	 * 获得创建人的头像
	 * @author Administrator
	 *
	 */
	class Imageloder extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			
		bitmap = HttpUtils.imageloder(add.getPortrait());
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
