package cn.com.zcty.ILovegolf.activity.view;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.PlaySetListViewAdapter;
import cn.com.zcty.ILovegolf.model.Diamond;
import cn.com.zcty.ILovegolf.tools.PickDialog;
import cn.com.zcty.ILovegolf.tools.PickDialogListener;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

/**
 * 打球设置类
 * @author deii
 *
 */
public class PlaySetActivity extends Activity implements OnClickListener {
    /**
     * 对象实例化
     */
	String color_name;
	private TextView t_name;
	private Button button_Start;
	private Button playset_back;
	private ListView listViewPlaySet;
	private ListView listViewPlaySet_t;
	private List<String> playset = new ArrayList<String>();
	private List<String> playset_t = new ArrayList<String>();
	private ArrayList<String> diamond_t = new ArrayList<String>();
	private LinearLayout linearLayout;
	private ArrayList<String> diamond = new  ArrayList<String>();
	private ArrayList<String> color = new ArrayList<String>();
	private ArrayList<Integer> diamodDong = new ArrayList<Integer>();
	private ArrayList<String> uuids = new ArrayList<String>();
	private PickDialog pickDialog;
	private PlaySetListViewAdapter adapter;
	private PlaySetListViewAdapter adapter_t;
	private String uuid;
	private String c;
	private String uuid_t;
	private String tiTai[]={"红色T台","白色T台","蓝色T台","黑色T台","金色T台"};
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				
				if(diamond.size()==1){
					playset.add(diamond.get(0));
					playset.add("开球T台");
					uuid = uuids.get(0);
					Log.i("uuid", uuid+"zzz");
					adapter = new PlaySetListViewAdapter(playset, PlaySetActivity.this);
					listViewPlaySet.setAdapter(adapter);
					if(diamodDong.get(0)==9){
						Log.i("count", diamodDong.get(0)+"");
						listViewPlaySet_t.setVisibility(View.VISIBLE);
					}else{
						listViewPlaySet_t.setVisibility(View.INVISIBLE);
					}
				}else{
					playset.add("选择子场");
					playset.add("开球T台");
					adapter = new PlaySetListViewAdapter(playset, PlaySetActivity.this);
					listViewPlaySet.setAdapter(adapter);
				}
				if(diamond_t.size()==1){
					playset_t.add(diamond_t.get(0));
					playset_t.add("开球T台");
					adapter_t = new PlaySetListViewAdapter(playset_t, PlaySetActivity.this);
					
				}else{
					playset_t.add("选择子场");
					playset_t.add("开球T台");
					adapter_t = new PlaySetListViewAdapter(playset_t, PlaySetActivity.this);
					
				}
				listViewPlaySet_t.setAdapter(adapter_t);
				
				
			}else{
				
			}
			setListeners();
		}
		
		
	};
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_playset);
		initView();	
        new Mytask().start();
        
	} 
	private void setListeners() {
		Intent intent = getIntent();
		final String sign = intent.getStringExtra("sign");
		listViewPlaySet.setOnItemClickListener(new OnItemClickListener() {

			private View mTempView;
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				this.mTempView = v;
				if(position==0){
					pickDialog = new PickDialog(PlaySetActivity.this, "选择子场", new PickDialogListener() {

						@Override
						public void onRightBtnClick() {
							
						}
						
						@Override
						public void onListItemLongClick(int position, String string) {
							
						}
						
						@Override
						public void onListItemClick(int position, String string) {
									playset.set(0, diamond.get(position));	
									uuid = uuids.get(position);
									Log.i("uuid", uuid+"zzz");
									if(diamodDong.get(position)==9){
										Log.i("count", diamodDong.get(position)+"");
										listViewPlaySet_t.setVisibility(View.VISIBLE);
										
									}else{
										
										uuid_t = null;
										
										listViewPlaySet_t.setVisibility(View.INVISIBLE);
									}
									playset.set(1, "开球T台");
									playset_t.set(0, "选择子场");
									playset_t.set(1, "开球T台");
									adapter.notifyDataSetChanged();
									button_Start.setClickable(false);
									button_Start.setTextColor(0x50F8d57a);
						}
						
						@Override
						public void onLeftBtnClick() {
							
						}
						
						@Override
						public void onCancel() {
							
						}
						
					});

					pickDialog.show();
					pickDialog.initListViewData(diamond);		
					
				}
				if(position==1&&!playset.get(0).equals("选择子场")){
					pickDialog = new PickDialog(PlaySetActivity.this, "开球T台", new PickDialogListener() {
						@Override
						public void onRightBtnClick() {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onListItemLongClick(int position, String string) {
							
						}
						
						@Override
						public void onListItemClick(int position, String string) {
							final int i = position;
							c = color.get(position);
							//final String colors = color.get(position);
							
							playset.set(1, tiTai[position]);
							
							//textview.setTextColor(Color.BLACK);
							adapter.notifyDataSetChanged();
							
							if(listViewPlaySet_t.getVisibility()!=0&&!playset.equals("开球T台")){
								button_Start.setTextColor(0xffF8d57a);
								button_Start.setClickable(false);
								button_Start.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										Intent intent;
										if(sign.equals("0")){										
											intent = new Intent(PlaySetActivity.this,MajorScoreCardActivity.class);									
										}else{
											intent = new Intent(PlaySetActivity.this,ScoreCardActivity.class);									

										}
										intent.putExtra("uuid", uuid);
										intent.putExtra("color", c);
										Log.i("uuid", uuid+c+1);
										startActivity(intent);
										finish();
									}
								});
							}
							
							
						}
						
						

						@Override
						public void onLeftBtnClick() {
							
						}
						
						@Override
						public void onCancel() {
							
						}
					});
					pickDialog.show();
					pickDialog.init_t_Data(color);	
				}
				
			}
		});
		listViewPlaySet_t.setOnItemClickListener(new OnItemClickListener() {

			private View mTempView;
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				this.mTempView = v;
				
				if(position==0){
					pickDialog = new PickDialog(PlaySetActivity.this, "选择子场", new PickDialogListener() {

						@Override
						public void onRightBtnClick() {
							
						}
						
						@Override
						public void onListItemLongClick(int position, String string) {
							
						}
						
						@Override
						public void onListItemClick(int position, String string) {
									uuid_t = uuids.get(position);
									playset_t.set(0, diamond_t.get(position));	
									playset_t.set(1, "开球T台");
									button_Start.setClickable(false);
									button_Start.setTextColor(0x50F8d57a);
									adapter_t.notifyDataSetChanged();
						}
						
						@Override
						public void onLeftBtnClick() {
							
						}
						
						@Override
						public void onCancel() {
							
						}
						
					});

					pickDialog.show();
					pickDialog.initListViewData(diamond_t);		
					
				}
				if(position==1&&!playset_t.get(0).equals("选择子场")){
					pickDialog = new PickDialog(PlaySetActivity.this, "开球T台", new PickDialogListener() {
						
						@Override
						public void onRightBtnClick() {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onListItemLongClick(int position, String string) {
							
						}
						
						@Override
						public void onListItemClick(int position, String string) {
							final int i = position;
							final String colors = color.get(position);
							playset_t.set(1, tiTai[position]);
							//textview.setTextColor(Color.BLACK);
							adapter_t.notifyDataSetChanged();
								if(!playset_t.equals("开球T台")){
									button_Start.setClickable(true);
									button_Start.setTextColor(0xffF8d57a);
									button_Start.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											Intent intent;
											if(sign.equals("0")){										
												intent = new Intent(PlaySetActivity.this,MajorScoreCardActivity.class);									
											}else{
												intent = new Intent(PlaySetActivity.this,ScoreCardActivity.class);									

											}											
											String c_t = colors;
											intent.putExtra("uuid_t", uuid_t);
											intent.putExtra("uuid", uuid);
											intent.putExtra("color", c);
											intent.putExtra("color_t", c_t);
											Log.i("asdf", uuid+"zhouhe"+uuid);
											startActivity(intent);
											//finish();
										}
									});
								}
								
						}
						
						@Override
						public void onLeftBtnClick() {
							
						}
						
						@Override
						public void onCancel() {
							
						}
					});
					pickDialog.show();
					pickDialog.init_t_Data(color);	
				}
				}
		});
	
	};
	private void initView() {
		listViewPlaySet = (ListView) findViewById(R.id.listView1);	
		listViewPlaySet_t = (ListView) findViewById(R.id.listView2);
		button_Start = (Button) findViewById(R.id.playset_start);
		playset_back = (Button) findViewById(R.id.playset_back);
		playset_back.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch(v.getId()){
		//点击返回按钮
		case R.id.playset_back:
			//intent=new Intent(PlaySetActivity.this,ChoosePitchActivity.class);
			//startActivity(intent);
			finish();
			break;
		
		}
	}
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
			Log.i("uz", uuid);
			//用户的token
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			try {
				//根据球场信息的uuid来获取该球场的具体信息的访问url
			String path=APIService.COURSE_INFO+"uuid="+URLEncoder.encode(uuid,"utf-8")+"&token="+URLEncoder.encode(token,"utf-8");
			String jsonData=HttpUtils.HttpClientGet(path);
			JSONObject jsonObj=new JSONObject(jsonData);
			JSONArray subArray=jsonObj.getJSONArray("courses");
			Log.i("name", jsonData);
			for(int j=0;j<subArray.length();j++){
				JSONObject jsonobj=subArray.getJSONObject(j); 
				diamond.add(jsonobj.getString("name")+"场("+jsonobj.getString("holes_count")+"洞)");
				if(Integer.parseInt(jsonobj.getString("holes_count"))==9){					
					diamond_t.add(jsonobj.getString("name")+"场("+jsonobj.getString("holes_count")+"洞)");
				}
				diamodDong.add(Integer.parseInt(jsonobj.getString("holes_count")));
				Log.i("name", jsonobj.getString("holes_count"));
				JSONArray jj = jsonobj.getJSONArray("tee_boxes");
				for(int i=0;i<jj.length();i++){
					color.add(jj.getString(i));
				}
				uuids.add(jsonobj.getString("uuid"));
			}
			Message msg = handler.obtainMessage();
			msg.what = 1;
			handler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
	