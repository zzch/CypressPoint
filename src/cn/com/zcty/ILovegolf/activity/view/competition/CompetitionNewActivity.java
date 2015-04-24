package cn.com.zcty.ILovegolf.activity.view.competition;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.SelectSession1Adapter;
import cn.com.zcty.ILovegolf.activity.adapter.SelectSessionTAdapter;
import cn.com.zcty.ILovegolf.model.Setcard;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
/**
 * 新建比赛
 * @author Administrator
 *
 */
public class CompetitionNewActivity extends Activity{
	private Button fanhuiButton;
	private Button chuangjianButton;
	private EditText userNameEditText;
	private EditText passWordEditText;
	private TextView moldTextView;
	private TextView sginTextView;
	private RelativeLayout selectSession;
	private RelativeLayout selectSession_t;
	private RelativeLayout selectSession_2;
	private RelativeLayout selectSession_t_2;
	private ListView selectSessionListView;
	private ListView selectSession_tListView;
	private ListView selectSession_2ListView;
	private ListView selectSession_t_2ListView;
	private TextView qiudongTextView;
	private TextView zichangTextView;
	private TextView titaiTextView;
	private TextView qiudongTextView_2;
	private TextView zichangTextView_2;
	private TextView titaiTextView_2;
	private LinearLayout linear;
	private ArrayList<String> nameArrayList = new ArrayList<String>();
	private ArrayList<String> name_2ArrayList = new ArrayList<String>();
	private ArrayList<Integer> diamodDong_2 = new ArrayList<Integer>();
	private ArrayList<String> diamond = new  ArrayList<String>();
	private ArrayList<String> color = new ArrayList<String>();
	private ArrayList<Integer> diamodDong = new ArrayList<Integer>();
	private ArrayList<String> diamond_t = new ArrayList<String>();
	private ArrayList<String> uuids = new ArrayList<String>();
	private final int REQUESTCODE=1;//返回的结果码   
	private String tiTai[]={"红色T台","白色T台","蓝色T台","黑色T台","金色T台"};
	private boolean f = false;
	
	private String id_1;
	private String id_2;
	private String t_1;
	private String t_2;
	
	private String name;//用户名
	private String password;//房间密码
	private String course_uuids;//uuid
	private String tee_boxes;//T台颜色
	private String remark;
	private String jsonData;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				getData();
				setListeners();
			}
			if(msg.what==2){
				Intent intent = new Intent(CompetitionNewActivity.this,CompetitionScordActivity.class);
				intent.putExtra("data", jsonData);
				startActivity(intent);
			}
		}

		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_competition_new);
		initView();
		setListerner();
		new Mytask().start();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUESTCODE){
		
			if (resultCode == 1) {
				selectSessionListView.setVisibility(View.VISIBLE);
				if(data.getStringExtra("sgin")!=null){
					
					sginTextView.setText(data.getStringExtra("sgin"));	
				}
			}
		 
		}
	}
	private void setListerner() {
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CompetitionNewActivity.this,CompetitionHomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		sginTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CompetitionNewActivity.this,SginText.class);
				startActivityForResult(intent, REQUESTCODE);
			}
		});
		chuangjianButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				name = userNameEditText.getText().toString();
				String ps = passWordEditText.getText().toString();
				Pattern p = Pattern.compile("[0-9]*"); 
				Matcher m = p.matcher(ps) ;			
				remark =sginTextView.getText().toString();
				if(remark.equals("点击设置比赛备注")){
					remark = "";
				}
				if(m.matches()&&ps.length()==4){
					password = ps;
					if(name.length()<1){
						name="比杆赛";
					}
					if(f){
					 if(titaiTextView_2.getText().toString().equals("")){
						 Toast.makeText(CompetitionNewActivity.this, "请选择子场和T台", Toast.LENGTH_LONG).show();
					 }else{
						 new Competition().start();
						 
					 }
					}else{
						if(titaiTextView.getText().toString().equals("")){
							Toast.makeText(CompetitionNewActivity.this, "请选择子场和T台", Toast.LENGTH_LONG).show();
						}else{
							 new Competition().start();
						}
					}
					
				}else{
					Toast.makeText(CompetitionNewActivity.this, "请输入4位数字的密码", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
	}
	
	private void setListeners() {
		/*
		 * 选择前面子场
		 */
		selectSession.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(selectSessionListView.getVisibility()==View.GONE){
					selectSessionListView.setVisibility(View.VISIBLE);
				}else{
					selectSessionListView.setVisibility(View.GONE);
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
				if(selectSession_2ListView.getVisibility()==View.GONE){
					selectSession_2ListView.setVisibility(View.VISIBLE);
				}else{
					selectSession_2ListView.setVisibility(View.GONE);
				}
			}
		});
		
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
	 * 点击EditText外 使EditText失去焦点并且隐藏键盘
	 */
	@Override  
	public boolean dispatchTouchEvent(MotionEvent ev) {  
	    if (ev.getAction() == MotionEvent.ACTION_DOWN) {  
	        View v = getCurrentFocus();  
	        if (isShouldHideInput(v, ev)) {  
	 
	            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
	            if (imm != null) {  
	                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
	                linear.requestFocus();
	            }  
	        }  
	        return super.dispatchTouchEvent(ev);  
	    }  
	    // 必不可少，否则所有的组件都不会有TouchEvent了   
	    if (getWindow().superDispatchTouchEvent(ev)) {  
	       return true;  
	    }  
	    return onTouchEvent(ev);  
	}  
	public  boolean isShouldHideInput(View v, MotionEvent event) {  
		    if (v != null && (v instanceof EditText)) {  
		        int[] leftTop = { 0, 0 };  
		        //获取输入框当前的location位置   
		        v.getLocationInWindow(leftTop);  
		        int left = leftTop[0];  
		        int top = leftTop[1];  
		        int bottom = top + v.getHeight();  
		        int right = left + v.getWidth();  
		        if (event.getX() > left && event.getX() < right  
		                && event.getY() > top && event.getY() < bottom) {  
		            // 点击的是输入框区域，保留点击EditText的事件   
		            return false;  
		        } else {  
		            return true;  
		        }  
		    }  
		    return false;  
		}
	private void getData() {
		selectSessionListView.setAdapter(new SelectSession1Adapter(this, diamond));
		selectSession_tListView.setAdapter(new SelectSessionTAdapter(this,color));
		selectSession_2ListView.setAdapter(new SelectSession1Adapter(this, diamond_t));
		selectSession_t_2ListView.setAdapter(new SelectSessionTAdapter(this,color));
		if(diamond.size()>=1){
			
			int itemHeight = 18;
			itemHeight = itemHeight+5;
			setListViewHeightBasedOnChildren(selectSessionListView,itemHeight);
			
		}
		if(diamond_t.size()>=1){
			
			int itemHeight = 18;
			itemHeight = itemHeight+5;
			setListViewHeightBasedOnChildren(selectSession_2ListView,itemHeight);
			
		}
		if(color.size()>=1){
			int itemHeight = 30;
			setListViewHeightBasedOnChildren(selectSession_tListView,itemHeight);
			setListViewHeightBasedOnChildren(selectSession_t_2ListView,itemHeight);
		}
		
	};
	private void initView() {
		userNameEditText = (EditText) findViewById(R.id.competition_match_name);
		passWordEditText = (EditText) findViewById(R.id.competition_match_pass);
		moldTextView = (TextView) findViewById(R.id.competition_match_mold);
		sginTextView = (TextView) findViewById(R.id.competition_match_sgin);
		selectSession = (RelativeLayout) findViewById(R.id.competition_selection_relative);
		selectSession_t = (RelativeLayout) findViewById(R.id.competition_selection_t);
		selectSession_2 = (RelativeLayout) findViewById(R.id.competition_selection_relative_2);
		selectSession_t_2 = (RelativeLayout) findViewById(R.id.competition_selection_t_2);
		selectSessionListView = (ListView) findViewById(R.id.competition_listview_qiuchang);
		
		selectSession_tListView = (ListView) findViewById(R.id.competition_listview_t);
		selectSession_2ListView = (ListView) findViewById(R.id.competition_listview_qiuchang_2);
		selectSession_t_2ListView = (ListView) findViewById(R.id.competition_listview_t_2);
		
		qiudongTextView = (TextView) findViewById(R.id.competition_match_zichang);
		zichangTextView = (TextView) findViewById(R.id.competition_match_chang);
		titaiTextView = (TextView) findViewById(R.id.competition_t_name);
		qiudongTextView_2 = (TextView) findViewById(R.id.competition_match_zichang_2);
		zichangTextView_2 = (TextView) findViewById(R.id.competition_match_chang_2);
		titaiTextView_2 = (TextView) findViewById(R.id.competition_t_name_2);
		selectSessionListView.setVisibility(View.VISIBLE);
		fanhuiButton = (Button) findViewById(R.id.button1);
		chuangjianButton = (Button) findViewById(R.id.competition_chuangjian);
		linear = (LinearLayout) findViewById(R.id.linear);
		/*
		 *设置数字键盘 
		 */
		passWordEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE); 
		passWordEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
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
			String path=APIService.COURSE_INFO+"uuid="+uuid+"&token="+token;			
			String jsonData=HttpUtils.HttpClientGet(path);
			try {
				JSONObject jsonObj=new JSONObject(jsonData);
				JSONArray subArray=jsonObj.getJSONArray("courses");
				Log.i("name", jsonData);
				for(int j=0;j<subArray.length();j++){
					JSONObject jsonobj=subArray.getJSONObject(j); 
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
					}
					uuids.add(jsonobj.getString("uuid"));
				}
				Message  msg = handler.obtainMessage();
				msg.what=1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	class Competition extends Thread{
		
		@Override
		public void run() {
			super.run();
			getsData();
		}
		public void getsData(){
		
			//用户的token
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = null;
			if(f){
				path = APIService.COMPETITION+
						"token="+token+"&name="+name+
						"&password="+password+
						"&rule="+"stroke_play"+
						"&course_uuids="+id_1+","+id_2+
						"&tee_boxes="+t_1+","+t_2+
						"&remark="+remark;
			}else{
				path = APIService.COMPETITION+
						"token="+token+"&name="+name+
						"&password="+password+
						"&rule="+"stroke_play"+
						"&course_uuids="+id_1+
						"&tee_boxes="+t_1+
						"&remark="+remark;
			}
			
			jsonData = HttpUtils.HttpClientPost(path);
			Log.i("zhouzhoupath", path);
			Log.i("zhouzhoupath", jsonData);
			Message msg = handler.obtainMessage();
			msg.what = 2;
			handler.sendMessage(msg);
		}
	}
	
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

}
