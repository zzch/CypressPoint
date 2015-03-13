package cn.com.zcty.ILovegolf.activity.view;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.exercise.city.ClearEditText;
import cn.com.zcty.ILovegolf.exercise.adapter.SortAdapter;
import cn.com.zcty.ILovegolf.model.Course;
import cn.com.zcty.ILovegolf.model.SortModel;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

/**
 * 按城市搜索球场列表
 * @author deii
 *
 */

public class ListChoosePitchActivity extends Activity {
	private List<String> citys=new ArrayList<String>();
	private Button huntButton;
	private Button search_back;
    private ExpandableListView sortListView;
    private List<List<Course>> child = new ArrayList<List<Course>>();
    private List<Course> childs= new ArrayList<Course>();
    private ClearEditText keyword;
    private SortAdapter adapter;
    private boolean flase;
	private List<SortModel> sortModels;

	
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.arg1==1){

				 adapter = new SortAdapter(ListChoosePitchActivity.this, citys, child);
				 sortListView.setAdapter(adapter);
				
				//默认展开
				if(adapter!=null){		
				for (int i = 0; i < citys.size(); i++) { 
					sortListView.expandGroup(i); 
				  }
				}
				sortListView.setOnGroupClickListener(new OnGroupClickListener() {
					
					@Override
					public boolean onGroupClick(ExpandableListView parent, View v,
							int groupPosition, long id) {
						return true;
					}
				});
			    
				setListeners();
		};}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_citylist);
		initView();
		new Citys().start();
		
	}
	private void setListeners() {
		huntButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArrayList<String> citys_name = new ArrayList<String>();
				String name = keyword.getText().toString();	
				for(int i=0;i<adapter.getGroupCount();i++){
					for(int j=0;j<adapter.getChildrenCount(i);j++){
						
						String name_city = adapter.getChildName(i, j);
						 if(name_city.indexOf(name)!=-1){
							citys_name.add(name_city);
							flase = true;
						}
					}
				}
				if(flase){
					Intent intent = new Intent(ListChoosePitchActivity.this,HuntActivity.class);
					Log.i("kk", citys_name.toString());
					intent.putStringArrayListExtra("city", citys_name); 
					
					startActivity(intent);
				}else{
					flase=false;
				}
			}
		});
		
		search_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ListChoosePitchActivity.this,ChoosePitchActivity.class );
				startActivity(intent);
			}
		});	
		
	}
	public void initView(){
		sortListView = (ExpandableListView) findViewById(R.id.country_lvcountry);
		keyword = (ClearEditText) findViewById(R.id.filter_edit);
		huntButton = (Button) findViewById(R.id.button1);
		search_back= (Button) findViewById(R.id.search_back);
	}
	class Citys extends Thread{
		public Citys() {
		}
		@Override
		public void run() {
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			
			 try {		    	
				 String token=sp.getString("token", "token");
				 String path=APIService.SEARCH_COURSE+"&token="+token;
				
				 String JsonData=HttpUtils.HttpClientGet(path);
				 
			    	JSONArray jsonarray = new JSONArray(JsonData);
			    	for(int i=0;i<jsonarray.length();i++){
			    		JSONObject json=jsonarray.getJSONObject(i);		
			    		citys.add(json.getString("name"));
			    		JSONArray subArray=json.getJSONArray("courses");
			    		childs = new ArrayList<Course>();			    		
			    		for(int j=0;j<subArray.length();j++){		
			    			Course course = new Course();
							 JSONObject jsonObj =subArray.getJSONObject(j);
							 course.setName(jsonObj.getString("name"));
							 course.setAddress(jsonObj.getString("address"));
							 childs.add(course);
							 
							Log.i("cc", jsonObj.getString("name"));
						 }
			    		child.add(childs);
			    	}
			    	
			    	Message msg = handler.obtainMessage();
					msg.arg1 = 1;
					handler.sendMessage(msg);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.i("erro", "erro");
					e.printStackTrace();
				}
		    
		}
	
   }
	
}