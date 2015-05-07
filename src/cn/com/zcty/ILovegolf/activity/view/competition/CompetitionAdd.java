package cn.com.zcty.ILovegolf.activity.view.competition;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.SelectSessionTAdapter;
import cn.com.zcty.ILovegolf.activity.view.MajorScoreActivity;
import cn.com.zcty.ILovegolf.model.CompetitionAddmatch;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class CompetitionAdd extends Activity{
	private Button fanhuiButton;
	private TextView nameTextView;
	private TextView ruleTextView;
	private TextView remarkTextView;
	private TextView qiudongTextView;
	private TextView zichangTextView;
	private TextView titaiTextView;
	private TextView qiudongTextView_2;
	private TextView zichangTextView_2;
	private TextView titaiTextView_2;

	private ListView titaiListView;
	private ListView titai2ListView;
	private RelativeLayout qiuchang2Layout;
	private RelativeLayout selectSession_t;
	private RelativeLayout selectSession_t_2;
	private Button createButton;
	private String tiTai[]={"红色T台","白色T台","蓝色T台","黑色T台","金色T台"};
	private boolean f = false;
	private String t_1;
	private String t_2;
	private CompetitionAddmatch add;
	private String uuid;
	private String flase = "no";//判断是否加入成功
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(msg.obj.equals("500")||msg.obj.equals("404")){
					Toast.makeText(CompetitionAdd.this, "网络异常，无法加入",Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(CompetitionAdd.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
				}
				if(flase.equals("success")){
					Intent intent = new Intent(CompetitionAdd.this,CompetitionScordActivity.class);
					intent.putExtra("data", uuid);
					startActivity(intent);
				}else{
					Toast.makeText(CompetitionAdd.this, "此id已在房间，不能重复加入",Toast.LENGTH_LONG).show();
				}
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
		
	}
	private void getData() {
		Intent intent = getIntent();
		add = (CompetitionAddmatch) intent.getSerializableExtra("add");
		nameTextView.setText(add.getName());
		ruleTextView.setText(add.getRule());
		remarkTextView.setText(add.getRemark());

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
			qiudongTextView.setText("前18洞");	
			zichangTextView.setText(add.getTitai().get(0).getName());
			qiuchang2Layout.setVisibility(View.GONE);
		}


	}
	private void setListeners() {
		fanhuiButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		selectSession_t.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(titaiListView.getVisibility()==View.GONE){
					titaiListView.setVisibility(View.VISIBLE);
				}else{
					titaiListView.setVisibility(View.GONE);
				}
			}
		});
		titaiListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				titaiTextView.setText(tiTai[position]);
				titaiListView.setVisibility(View.GONE);
				t_1 = add.getTitai().get(0).getBoxs().get(position);
				if(f){
					titai2ListView.setVisibility(View.VISIBLE);
				}else{
					titai2ListView.setVisibility(View.GONE);
					t_2 = null;
				}
			}
		});
		selectSession_t_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(titai2ListView.getVisibility()==View.GONE){
					titai2ListView.setVisibility(View.VISIBLE);
				}else{
					titai2ListView.setVisibility(View.GONE);
				}
			}
		});
		titai2ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				titaiTextView_2.setText(tiTai[position]);
				titai2ListView.setVisibility(View.GONE);
				t_2 = add.getTitai().get(1).getBoxs().get(position);
			}
		});
		
		createButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Add().start();
			}
		});
	}
	private void initView() {
		fanhuiButton = (Button) findViewById(R.id.button1);
		nameTextView = (TextView) findViewById(R.id.competition_match_name);
		ruleTextView = (TextView) findViewById(R.id.competition_match_mold);
		remarkTextView = (TextView) findViewById(R.id.competition_match_sgin);

		qiuchang2Layout = (RelativeLayout) findViewById(R.id.competition_selection_relative_2);
		titaiListView = (ListView) findViewById(R.id.competition_listview_t);
		titaiListView.setVisibility(View.VISIBLE);
		titai2ListView = (ListView) findViewById(R.id.competition_listview_t_2);

		selectSession_t = (RelativeLayout) findViewById(R.id.competition_selection_t);
		selectSession_t_2 = (RelativeLayout) findViewById(R.id.competition_selection_t_2);
		qiudongTextView = (TextView) findViewById(R.id.competition_match_zichang);
		zichangTextView = (TextView) findViewById(R.id.competition_match_chang);
		titaiTextView = (TextView) findViewById(R.id.competition_t_name);
		qiudongTextView_2 = (TextView) findViewById(R.id.competition_match_zichang_2);
		zichangTextView_2 = (TextView) findViewById(R.id.competition_match_chang_2);
		titaiTextView_2 = (TextView) findViewById(R.id.competition_t_name_2);
		createButton = (Button) findViewById(R.id.competition_chuangjian);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	class Add extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			Intent intent = getIntent();
			uuid = intent.getStringExtra("uuid");
			String password = intent.getStringExtra("pwd");
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path;
			if(f){
				 path = APIService.COMPETITIONCREAT+"token="+token+"&uuid="+uuid+"&password="+password+"&tee_boxes="+t_1+","+t_2;
			}else{
				 path = APIService.COMPETITIONCREAT+"token="+token+"&uuid="+uuid+"&password="+password+"&tee_boxes="+t_1;
			}
			String jsonData = HttpUtils.HttpClientPost(path);
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
}