package cn.com.zcty.ILovegolf.activity.view.fragment;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.AnlyzeDiamondAdapter;
import cn.com.zcty.ILovegolf.activity.view.count.AnalyzeResultActivity;
import cn.com.zcty.ILovegolf.model.AnlyzeDiamond;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class AnlyzeDiamondFragment extends Fragment{
	private View view;
	private ListView listView;
	private TextView tishiTextView;
	private ArrayList<AnlyzeDiamond> anDiamonds = new ArrayList<AnlyzeDiamond>();
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				getData();
				setListeners();
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.anlyze_diamond_fragment, container, false);	
		initView();		
		
		new Diamond().start();
		return view;
	}
	private void setListeners() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(getActivity(),AnalyzeResultActivity.class);
				intent.putExtra("uuid",anDiamonds.get(position).getUuid());
				intent.putExtra("fs", "3");
				startActivity(intent);
			}
		});
	}
	private void getData() {
		/*
		 * 判断是否添加了球场
		 * 如果添加了球场就隐藏提示
		 */
		if(anDiamonds.size()>0){
			tishiTextView.setVisibility(View.GONE);
		}else{
			tishiTextView.setVisibility(View.VISIBLE);
		}
		/*
		 * 添加适配器
		 */
		listView.setAdapter(new AnlyzeDiamondAdapter(getActivity(), anDiamonds));
	}
	/*
	 * 找到控件
	 */
	private void initView() {
		listView = (ListView) view.findViewById(R.id.listView1);
		tishiTextView = (TextView) view.findViewById(R.id.anlyze_diamond_tishi);
	}
	class Diamond extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
			
		}
		public void getData(){
			SharedPreferences sp = getActivity().getSharedPreferences("register", Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			String path = APIService.VISTTED+"token="+token;
			String JsonData = HttpUtils.HttpClientGet(path);
			Log.i("pathDiamond", path);
			Log.i("pathDiamond", JsonData);
			try {
				JSONArray jsonArray = new JSONArray(JsonData);
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					AnlyzeDiamond diamond = new AnlyzeDiamond();
					diamond.setUuid(jsonObject.getString("uuid"));
					diamond.setName(jsonObject.getString("name"));
					diamond.setAddress(jsonObject.getString("address"));
					diamond.setVisited_count(jsonObject.getString("visited_count"));
					anDiamonds.add(diamond);
				}
				Message msg = handler.obtainMessage();
				msg.what=1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
