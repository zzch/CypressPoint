package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.MajorScoreCardGridViewAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.ScoreCardGridViewAdapter;
import cn.com.zcty.ILovegolf.model.MajorScore;
import cn.com.zcty.ILovegolf.model.Scorecards;
import cn.com.zcty.ILovegolf.model.Setcard;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

/**
 * 记分卡类
 * @author deii
 *
 */
public class MajorScoreCardActivity extends Activity {
	private List<Scorecards> scorecarsArray = new ArrayList<Scorecards>();
	private List<Setcard> setcardsArray = new ArrayList<Setcard>(19);
	private GridView grid_scorecard;	
	private MajorScoreCardGridViewAdapter adapter;
	private Setcard setCard;
	private final int REQUESTCODE=1;//返回的结果码   
	private String uuid;
	private String uuid_t;
	private String match_uuid;
	private ArrayList<MajorScore> majorArray;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				setListeners();
				adapter.notifyDataSetChanged();
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scorecard);
		initView();	
	
		new MyTask().start();
		Log.i("shizhegejiemian", "shizhegejiemian");
	}
	
	/*private void getData() {
		
	}*/

	private void setListeners() {
		adapter = new MajorScoreCardGridViewAdapter(scorecarsArray, setcardsArray, this);
		grid_scorecard.setAdapter(adapter);
		grid_scorecard.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View View, int position,
					long arg3) {
				
				if(position%2!=0){
					/*SharedPreferences sp = getSharedPreferences("setCard", MODE_PRIVATE);
					SharedPreferences.Editor editor = sp.edit();
					if(adapter.getResult(position).getRodNum()!=null){
						editor.putString("rodnum", adapter.getResult(position).getRodNum());
						editor.putString("putts", adapter.getResult(position).getPutts());
						editor.putString("penalties", adapter.getResult(position).getPenalties());
						editor.putString("te", adapter.getResult(position).getTe());
						editor.putString("direction", adapter.getResult(position).getPar());
						editor.commit();
					}*/
					Intent intent = new Intent(MajorScoreCardActivity.this,MajorScoreActivity.class);
					intent.putExtra("number", scorecarsArray.get(position/2).getNumber());
					intent.putExtra("par",scorecarsArray.get(position/2).getPar());
					intent.putExtra("uuid", scorecarsArray.get(position/2).getUuid());
					intent.putExtra("diatance", scorecarsArray.get(position/2).getDistance_from_hole_to_tee_box());
					intent.putExtra("color", scorecarsArray.get(position/2).getTee_box_color());
					intent.putExtra("position", position+"");
					startActivityForResult(intent, REQUESTCODE);
				}
				
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUESTCODE){
		for(int i=0;i<=18;i++){
			if (resultCode == i) {
				String score = data.getStringExtra("score");
				String putts = data.getStringExtra("putts");
				String penalties = data.getStringExtra("penalties");
				int position =Integer.parseInt(data.getStringExtra("position"));
				setCard = new Setcard();
				setCard.setRodNum(score);
				setCard.setPutts(putts);
				setCard.setPenalties(penalties);
				setcardsArray.set(position/2, setCard);
				adapter = new MajorScoreCardGridViewAdapter(scorecarsArray, setcardsArray, this);
				grid_scorecard.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		}
		}
	}
	/**
	 * 初始化
	 */
	private void initView() {
		grid_scorecard = (GridView) findViewById(R.id.gridView1);
		grid_scorecard.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}
	
	public void onclick(View v){
		Intent intent;
		switch(v.getId()){
		//记分卡返回按钮
		case R.id.scorecard_back:
			intent=new Intent(MajorScoreCardActivity.this,SchematicScoreActivity.class);
			startActivity(intent);
			finish();
			break;
			//点击统计按钮
		case R.id.scorecard_score:
			intent=new Intent(MajorScoreCardActivity.this,MajorStatisticsActivity.class);
			startActivity(intent);
			
			break;
		}
	}
	class MyTask extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp = getSharedPreferences("register", Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			SharedPreferences ss = getSharedPreferences("edit",Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = ss.edit();
			
			String path;
			
			Intent intent=getIntent();
			 uuid=intent.getStringExtra("uuid");
			Log.i("jjszhouhe", uuid);
			 uuid_t = intent.getStringExtra("uuid_t");
			String boxes = intent.getStringExtra("color");
			String boxes_t = intent.getStringExtra("color_t");
	
			if(uuid_t==null&&boxes!=null){
				 path = APIService.CREATE_PRACTICE_EVENTS+"course_uuids="+uuid+"&tee_boxes="+boxes+"&token="+token+"&scoring_type=professional";
				String  jsonArrayData = HttpUtils.HttpClientPost(path);
				  try {
						JSONObject jsonObject = new JSONObject(jsonArrayData);
						Log.i("jjs", jsonArrayData);
						match_uuid = jsonObject.getString("uuid");
						editor.putString("match_uuid", match_uuid);
						editor.commit();
						JSONArray jsonArray = jsonObject.getJSONArray("scorecards");
						Log.i("jjjs", jsonArrayData);
						for(int i=0;i<jsonArray.length();i++){
							JSONObject jsonObjects = jsonArray.getJSONObject(i);
							Scorecards scorecards = new Scorecards();
							scorecards.setUuid(jsonObjects.getString("uuid"));						
							scorecards.setNumber(jsonObjects.getString("number"));						
							scorecards.setPar(jsonObjects.getString("par"));					
							scorecards.setTee_box_color(jsonObjects.getString("tee_box_color"));						
							scorecards.setDistance_from_hole_to_tee_box(jsonObjects.getString("distance_from_hole_to_tee_box"));						
							scorecarsArray.add(scorecards);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				  for(int i=0;i<=18;i++){
						Setcard setcard = new Setcard();
						setcardsArray.add(setcard);
					}
			}else if(boxes==null){
				 for(int i=0;i<=18;i++){
						Setcard setcard = new Setcard();
						setcardsArray.add(setcard);
					}
				  editor.putString("match_uuid", uuid);
				  editor.commit();
				  path = APIService.LIANXISAISHI+"uuid="+uuid+"&token="+token+"&scoring_type=professional";
				  String jsonArrayData = HttpUtils.HttpClientGet(path);	 
				  try {
					  String direction;
					
					  	JSONObject jsonObj = new JSONObject(jsonArrayData);
					  	
						JSONArray jsonArray = jsonObj.getJSONArray("scorecards");
						for(int i=0;i<jsonArray.length();i++){
							JSONObject jsonObjects = jsonArray.getJSONObject(i);
							Scorecards scorecards = new Scorecards();
							scorecards.setUuid(jsonObjects.getString("uuid"));							
							scorecards.setNumber(jsonObjects.getString("number"));
							Log.i("numb", ""+scorecards.getNumber());
							scorecards.setPar(jsonObjects.getString("par"));							
							scorecards.setTee_box_color(jsonObjects.getString("tee_box_color"));							
							scorecards.setDistance_from_hole_to_tee_box(jsonObjects.getString("distance_from_hole_to_tee_box"));							
							scorecarsArray.add(scorecards);
							Setcard setcard = new Setcard();
							setcard.setRodNum(jsonObjects.getString("score"));
							Log.i("jjs", jsonObjects.getString("score"));
							if(jsonObjects.getString("direction").equals("pure")){
								direction = "命中";
							}else if(jsonObjects.getString("direction").equals("slice")){
								direction = "右侧";
							}else{
								direction = "左侧";
							}
							setcard.setPar(direction);
							setcard.setPutts(jsonObjects.getString("putts"));
							setcard.setPenalties(jsonObjects.getString("penalties"));
							setcard.setTe(jsonObjects.getString("driving_distance"));
							setcardsArray.set(i, setcard);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			}
			else{
				 path = APIService.CREATE_PRACTICE_EVENTS+"course_uuids="+uuid+","+uuid_t+"&tee_boxes="+boxes+","+boxes_t+"&token="+token+"&scoring_type=professional";
				String  jsonArrayData = HttpUtils.HttpClientPost(path);
				  try {
						JSONObject jsonObject = new JSONObject(jsonArrayData);
						Log.i("jjs", jsonArrayData);
						match_uuid = jsonObject.getString("uuid");
						editor.putString("match_uuid", match_uuid);
						editor.commit();
						JSONArray jsonArray = jsonObject.getJSONArray("scorecards");
						Log.i("jjjs", jsonArrayData);
						for(int i=0;i<jsonArray.length();i++){
							JSONObject jsonObjects = jsonArray.getJSONObject(i);
							Scorecards scorecards = new Scorecards();
							scorecards.setUuid(jsonObjects.getString("uuid"));							
							scorecards.setNumber(jsonObjects.getString("number"));						
							scorecards.setPar(jsonObjects.getString("par"));							
							scorecards.setTee_box_color(jsonObjects.getString("tee_box_color"));							
							scorecards.setDistance_from_hole_to_tee_box(jsonObjects.getString("distance_from_hole_to_tee_box"));
							scorecarsArray.add(scorecards);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				  for(int i=0;i<=18;i++){
						Setcard setcard = new Setcard();
						setcardsArray.add(setcard);
					}
			}
			
			
			
			
			Message msg = handler.obtainMessage();
			msg.what=1;
			handler.sendMessage(msg);
		}
	}
}

