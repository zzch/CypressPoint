 package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.location.ad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.SearchCityAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.SortAdapter;
import cn.com.zcty.ILovegolf.activity.exercise.city.ClearEditText;
import cn.com.zcty.ILovegolf.model.Course;
import cn.com.zcty.ILovegolf.model.SortModel;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

/**
 * 按城市搜索球场列表
 * @author deii
 *
 */

    public class ListChoosePitchActivity extends Activity {
	private List<String> citys=new ArrayList<String>();
	private Button search_back;
    private ExpandableListView sortListView;
    private List<List<Course>> child = new ArrayList<List<Course>>();
    private List<Course> childs= new ArrayList<Course>();
    private ClearEditText keyword;
    private SortAdapter adapter;
	private List<SortModel> sortModels;
	private List<String> uuidList = new ArrayList<String>();
	private SharedPreferences ss;
	private ArrayList<String> citys_name;
	private List<String> citys_address;
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
				sortListView.setOnChildClickListener(new OnChildClickListener() {
					
					@Override
					public boolean onChildClick(ExpandableListView parent, View v,
							int groupPosition, int childPosition, long id) {
						/*
						 * 返回球场name
						 */
						Intent intent = new Intent();
						intent.putExtra("name", child.get(groupPosition).get(childPosition).getName());
						intent.putExtra("false", "0");
						intent.putExtra("uuid", child.get(groupPosition).get(childPosition).getUuid());
						setResult(0, intent);
						finish();
						
						
						return false;
					}
				});
				setListeners();
		  };
		}
	};
	private ListView cityList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_citylist);
		initView();
		new Citys().start();
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
		
			Intent intent=new Intent(ListChoosePitchActivity.this,CreateMatchActivity.class);
			intent.putExtra("name", "null");
			setResult(0, intent);
			finish();
		
        }
		return false;
	}
	private void setListeners() {
		search_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ListChoosePitchActivity.this,CreateMatchActivity.class);
				intent.putExtra("name", "null");
				setResult(0, intent);
				finish();
			}
		});	
		keyword.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()!=0){
					cityList.setVisibility(View.VISIBLE);
					sortListView.setVisibility(View.GONE);
					citys_name = new ArrayList<String>();
					citys_address = new ArrayList<String>();
					String name = keyword.getText().toString();	
					
					for(int i=0;i<adapter.getGroupCount();i++){
						for(int j=0;j<adapter.getChildrenCount(i);j++){
		
							String name_city = adapter.getChildName(i, j);
							String name_address = adapter.getChildAddress(i, j);
							 if(name_city.indexOf(name)!=-1){
								citys_name.add(name_city);
								citys_address.add(name_address);
								uuidList.add(adapter.uuid(i, j));
							}
						}
					}
					   SearchCityAdapter adapters = new SearchCityAdapter(citys_name,citys_address,ListChoosePitchActivity.this);
						
						cityList.setAdapter(adapters);
						adapters.notifyDataSetChanged();
						
				}else{
					cityList.setVisibility(View.GONE);
					sortListView.setVisibility(View.VISIBLE);
				}
			}
		});
		cityList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				/*
				 * 返回球场name
				 */
				Intent intent = new Intent();
				intent.putExtra("name", citys_name.get(arg2));
				intent.putExtra("false", "0");
				intent.putExtra("uuid", uuidList.get(arg2));
				setResult(0, intent);
				finish();
			}
		});
	}
	public void initView(){
		sortListView = (ExpandableListView) findViewById(R.id.country_lvcountry);
		keyword = (ClearEditText) findViewById(R.id.filter_edit);
		search_back= (Button) findViewById(R.id.search_back);
		cityList = (ListView) findViewById(R.id.listView1);
		Intent intent = getIntent();
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
			    		JSONArray subArray=json.getJSONArray("venues");
			    		childs = new ArrayList<Course>();			    		
			    		for(int j=0;j<subArray.length();j++){		
			    			Course course = new Course();
							 JSONObject jsonObj =subArray.getJSONObject(j);
							 course.setName(jsonObj.getString("name"));
							 course.setAddress(jsonObj.getString("address"));
							 course.setUuid(jsonObj.getString("uuid"));
							 childs.add(course);
							 
							Log.i("golf_address", jsonObj.getString("address"));
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