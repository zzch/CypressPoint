package cn.com.zcty.ILovegolf.activity.view.competition;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.ScoreCardGridViewAdapter;
import cn.com.zcty.ILovegolf.activity.view.ScoreCardUpDateActivity;
import cn.com.zcty.ILovegolf.model.CompetitionAddmatch;
import cn.com.zcty.ILovegolf.model.Scorecards;
import cn.com.zcty.ILovegolf.model.Setcard;
import cn.com.zcty.ILovegolf.tools.CircleImageView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class CompetitionScordActivity extends Activity{
	private List<Scorecards> scorecarsArray = new ArrayList<Scorecards>();
	private List<Setcard> setcardsArray = new ArrayList<Setcard>(19);
	private List<Setcard> setcardsArray_2 = new ArrayList<Setcard>(19);
	private CompetitionAddmatch add;
	private TextView nameTextView;
	private Button fanhuiButton;
	private CircleImageView imageHead;
	private GridView grid_scorecard;	
	private ScoreCardGridViewAdapter adapter;
	private Setcard setCard;
	private String  url;
	private final int REQUESTCODE=1;//返回的结果码   
	private String match_uuid;
	private Bitmap bitmap ;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				setListeners();
				adapter.notifyDataSetChanged();
			}
			
		};
	};
	Handler handler1 = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				imageHead.setImageBitmap(bitmap);
				saveMyBitmap(bitmap);
			}
		
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_competition_scord);
		initView();	
		setListener();
		
	}
	
	private void setListener() {
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(CompetitionScordActivity.this,CompetitionScoreActivity.class);
				//startActivity(intent);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		//Intent intent = new Intent(CompetitionScordActivity.this,CompetitionScoreActivity.class);
		//startActivity(intent);
		finish();
	}
	
	private void initView() {
		grid_scorecard = (GridView) findViewById(R.id.competition_gridView1);
		grid_scorecard.setSelector(new ColorDrawable(Color.TRANSPARENT));
		fanhuiButton = (Button) findViewById(R.id.scorecard_back);
		imageHead = (CircleImageView) findViewById(R.id.myself_head);
		nameTextView = (TextView) findViewById(R.id.competition_username);
		add = (CompetitionAddmatch) getIntent().getSerializableExtra("add");
		url = add.getPortrait();
		SharedPreferences sp = getSharedPreferences("register", MODE_PRIVATE);
		String name = sp.getString("nickname", "nickname");
		nameTextView.setText(name);
		new MyTask().start();
				if(fileIsExists()){
					imageHead.setImageBitmap(converToBitmap(100,100));
				}else{
					if(!url.equals("null")){
						new Imageloder().start();
						Log.i("sdf", "sadf");
					}
					
				}
	}
	private void setListeners() {
		adapter = new ScoreCardGridViewAdapter(scorecarsArray, setcardsArray, this);
		grid_scorecard.setAdapter(adapter);
		grid_scorecard.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View View, int position,
					long arg3) {
				
				if(position%2!=0){
					SharedPreferences sp = getSharedPreferences("setCard", MODE_PRIVATE);
					SharedPreferences.Editor editor = sp.edit();
					if(adapter.getResult(position).getRodNum()!=null){
						editor.putString("rodnum", adapter.getResult(position).getRodNum());
						editor.putString("putts", adapter.getResult(position).getPutts());
						editor.putString("penalties", adapter.getResult(position).getPenalties());
						editor.putString("te", adapter.getResult(position).getTe());
						editor.putString("direction", adapter.getResult(position).getPar());
						editor.commit();
					}
					Intent intent = new Intent(CompetitionScordActivity.this,ScoreCardUpDateActivity.class);
					intent.putExtra("number", scorecarsArray.get(position/2).getNumber());
					intent.putExtra("par",scorecarsArray.get(position/2).getPar());
					intent.putExtra("uuid", scorecarsArray.get(position/2).getUuid());
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
				int position = Integer.parseInt(data.getStringExtra("position"));
				setCard = (Setcard) data.getSerializableExtra("scard");
				setcardsArray.set(position/2, setCard);
				adapter.notifyDataSetChanged();
			}
		 }
		}
	}
	class MyTask extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
		
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			Intent intent=getIntent();
			String	uuid = intent.getStringExtra("data");
			String path = APIService.COMPETITIONINFORMATION+"token="+token+"&uuid="+uuid;
			String jsonArrayData = HttpUtils.HttpClientGet(path);
			Log.i("Competition", path);
			Log.i("Competition", jsonArrayData);
			/*SharedPreferences ss = getSharedPreferences("edit",Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = ss.edit();*/
				  try {
					  	//JSONObject j = new JSONObject(jsonArrayDatas);
					  	//String jsonArrayData = j.getString("scorecards");
						JSONObject jsonObject = new JSONObject(jsonArrayData);
						//match_uuid = jsonObject.getString("uuid");
						/*editor.putString("match_uuid", match_uuid);
						editor.commit();*/
						JSONArray jsonArray = jsonObject.getJSONArray("scorecards");
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
						Message msg = handler.obtainMessage();
						msg.what=1;
						handler.sendMessage(msg);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				  for(int i=0;i<=18;i++){
						Setcard setcard = new Setcard();
						setcardsArray.add(setcard);
						//setcardsArray_2.add(setcard);
					}
			
				 
			}
			
			
		
	}
	public Bitmap converToBitmap( int w, int h){
		 BitmapFactory.Options opts = new BitmapFactory.Options();
		 // 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		BitmapFactory.decodeFile("/mnt/sdcard/testfile/golf.jpg", opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
			 scaleWidth = ((float) width) / w;
			 scaleHeight = ((float) height) / h;
		}
		 opts.inJustDecodeBounds = false;
		 float scale = Math.max(scaleWidth, scaleHeight);
		 opts.inSampleSize = (int)scale;
		 WeakReference<Bitmap> weak = new WeakReference<Bitmap>
		 (BitmapFactory.decodeFile("/mnt/sdcard/testfile/golf.jpg", opts));

		return  Bitmap.createScaledBitmap(weak.get(), w, h, true);

		
	}
	/*class Touxiang extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.TITLE+"token="+token;
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("xihuan", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				JSONObject jsObjectuser = new JSONObject(jsonObject.getString("user"));
				JSONObject jsObjectportrait = new JSONObject(jsObjectuser.getString("portrait"));
				url = jsObjectportrait.getString("url");
				Log.i("urlimage", url);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 2;
			handler.sendMessage(msg);
		}
	}*/
	class Imageloder extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			
		bitmap = HttpUtils.imageloder(url);
		Message msg = handler1.obtainMessage();
		msg.what = 1;
		handler1.sendMessage(msg);	
			
	}
	}
	/**
	 * 把bitmap存入手机文件目录
	 * @param bitName
	 */
	@SuppressLint("SdCardPath")
	public void saveMyBitmap(Bitmap bitName)  {
        File f = new File("/mnt/sdcard/testfile"); 
        if(f.exists()){
        	f.delete();
        }else{
        	f.mkdir();
        }
        FileOutputStream fOut = null;
        try {
                fOut = new FileOutputStream("/mnt/sdcard/testfile/golf.jpg");
                bitName.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
            	fOut.flush();
            	fOut.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
       
} 
	public boolean fileIsExists(){

	              File f=new File("/mnt/sdcard/testfile");

		            if(!f.exists()){

		                       return false;

		               }

		               return true;

		        }
}
