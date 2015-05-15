package cn.com.zcty.ILovegolf.activity.view;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.CreateScoreCardAdapter;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.model.ScoreCardsMatch;
import cn.com.zcty.ILovegolf.model.TeeBoxsMatch;
import cn.com.zcty.ILovegolf.tools.CircleImageView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
/**
 * 记分卡
 * @author Administrator
 *
 */
public class CreateScoreCard extends Activity{
	private String portrait;
	private String username;
	private Bitmap bitmap;
	
	private String used;
	private ArrayList<String> color = new ArrayList<String>();
	private ArrayList<String> distance = new ArrayList<String>();
	private String keren;
	private String ranking;
	private String schedule;
	private String score;
	private String par;
	private Button yaoqingButton;
	private TextView usernameTextView;
	private TextView rankingTextView;//排名
	private TextView scheduleTextView;//进度
	private TextView scoreTextView;//成绩
	private TextView parTextView;
	private LinearLayout linearLayout;
	private ListView scoreListView;//存放数据
	private CircleImageView totleImage;
	private ProgressDialog progressDialog;
	private String id;
	private String scoring_type;
	private String owned;
	private String picname;
	private ArrayList<ScoreCardsMatch> scoreCardsMatchs = new ArrayList<ScoreCardsMatch>();//存放成绩
	private ArrayList<TeeBoxsMatch> teeBoxsMatchs = new ArrayList<TeeBoxsMatch>();//存放T台颜色
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(CreateScoreCard.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(CreateScoreCard.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(CreateScoreCard.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
				usernameTextView.setText(username);	
				rankingTextView.setText(ranking);
				scheduleTextView.setText(schedule);
				scoreTextView.setText(score);
				parTextView.setText(par);
				if(!portrait.equals("null")){
					if(!fileIsExists()){						
						new Imageloder().start();
					}else{
						totleImage.setImageBitmap(converToBitmap(100,100));
					}
				}
				CreateScoreCardAdapter adapter = new CreateScoreCardAdapter(CreateScoreCard.this, scoreCardsMatchs);
				scoreListView.setAdapter(adapter);
				hideProgressDialog();
				linearLayout.setVisibility(View.VISIBLE);
				if(owned.equals("true")){
					yaoqingButton.setVisibility(View.VISIBLE);
				}else{
					yaoqingButton.setVisibility(View.GONE);
				}
				}
			}
			if(msg.what==2){
				totleImage.setImageBitmap(bitmap);
				saveMyBitmap(bitmap);//把bitmap保存到手机目录中
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scord);
		initView();
		setListeners();
		new MyTask().start();
		showProgressDialog("提示", "正在加载内容，请稍等");
	}
	private void setListeners() {
		scoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(scoring_type.equals("simple")){
					Intent intent = new Intent(CreateScoreCard.this,ScoreCardUpDateActivity.class);
					intent.putExtra("uuid", scoreCardsMatchs.get(position).getUuid());
					intent.putExtra("number", (position+1)+"");
					startActivity(intent);
				}else{
					Intent intent = new Intent(CreateScoreCard.this,MajorScoreActivity.class);
					intent.putExtra("uuid", scoreCardsMatchs.get(position).getUuid());
					intent.putExtra("number", (position+1)+"");
					intent.putExtra("par", scoreCardsMatchs.get(position).getPar());
					intent.putExtra("color", color.get(position));
					intent.putExtra("diatance", distance.get(position));
					startActivity(intent);
				}
			}
		});
	}
	private void initView() {
		linearLayout = (LinearLayout) findViewById(R.id.linears);
		totleImage = (CircleImageView) findViewById(R.id.myself_head);
		usernameTextView = (TextView) findViewById(R.id.competition_username);
		rankingTextView = (TextView) findViewById(R.id.competition_paiming);
		scheduleTextView = (TextView) findViewById(R.id.competition_jindu);
		scoreTextView = (TextView) findViewById(R.id.competition_chengji);
		parTextView = (TextView) findViewById(R.id.competition_par);
		scoreListView = (ListView) findViewById(R.id.score_match);
		yaoqingButton = (Button) findViewById(R.id.competition_button_yaoqing);
		Intent intent=getIntent();
		id = intent.getStringExtra("uuid");
		scoring_type = intent.getStringExtra("scoring_type");
		picname = intent.getStringExtra("name");
		
		
	}
	

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		new MyTask().start();
		showProgressDialog("提示", "正在加载内容，请稍等");
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(CreateScoreCard.this,QuickScoreActivity.class);
		startActivity(intent);
		finish();
	}
	/*
	 * 点击跳转
	 */
	public void onclick(View v){
		switch (v.getId()) {
		case R.id.scorecard_back:
			Intent intent = new Intent(CreateScoreCard.this,QuickScoreActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.competition_button_paiming:
			Intent i = new Intent(CreateScoreCard.this,RankingActivity.class);
			i.putExtra("uuid", id);
			startActivity(i);
			break;
		case R.id.competition_button_yaoqing:
			Intent j = new Intent();
			if(fileIsExists()){
				j.setClass(CreateScoreCard.this,InviteActivity.class);
			}else{
				//j = new Intent(CreateScoreCard.this,SelfhoodActivity.class);
				j.setClass(CreateScoreCard.this,SelfhoodActivity.class);
			}
			j.putExtra("cunzai", "0");
			j.putExtra("uuid", id);
			startActivity(j);
			break;
		case R.id.competition_button_tongji:
			Intent f = new Intent();
			if(scoring_type.equals("simple")){
				f.setClass(CreateScoreCard.this,StatisticsAvtivity.class);
			}else{
				f.setClass(CreateScoreCard.this,MajorStatisticsActivity.class);
			}
			
			f.putExtra("name", picname);
			f.putExtra("uuid", id);
			startActivity(f);
			break;
		default:
			break;
		}
	}
	/**
	 * 获得用户资料
	 * @author Administrator
	 *
	 */
	class MyTask extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			scoreCardsMatchs.clear();
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			Intent intent=getIntent();
			String	uuid = intent.getStringExtra("uuid");
			String path = APIService.MATCHINFORMATION+"token="+token+"&uuid="+uuid;
			String jsonArrayData = HttpUtils.HttpClientGet(path);
			Log.i("scoress", jsonArrayData);
			Log.i("scoress", path);
			try {
				JSONObject jsonObject = new JSONObject(jsonArrayData);
				
				
				/*
				 * 获得头像和用户地址
				 */
				String player = jsonObject.getString("player");
				JSONObject userJsonObject = new JSONObject(player);
				String user = userJsonObject.getString("user");
				JSONObject usersJsonObject = new JSONObject(user);
				portrait = usersJsonObject.getString("portrait");//头像地址
				if(!portrait.equals("null")){
					JSONObject urlJsonObject = new JSONObject(portrait);
					portrait = urlJsonObject.getString("url");
				}
				username = usersJsonObject.getString("nickname");//用户昵称
				/*
				 * 获取player
				 */
				ranking = userJsonObject.getString("position");//排名
				schedule = userJsonObject.getString("recorded_scorecards_count");//进度
				score = userJsonObject.getString("strokes");//成绩
				par = userJsonObject.getString("total");//距标准杆
				owned = userJsonObject.getString("owned");//判断是否房主
				
				/*
				 * 获取成绩
				 */
				JSONArray scorecardsJsonObject = jsonObject.getJSONArray("scorecards");
				for(int i=0;i<scorecardsJsonObject.length();i++){
					JSONObject j = scorecardsJsonObject.getJSONObject(i);
					ScoreCardsMatch scoreCardsMatch = new ScoreCardsMatch();
					scoreCardsMatch.setUuid(j.getString("uuid"));
					scoreCardsMatch.setNumber(j.getString("number"));
					scoreCardsMatch.setPar(j.getString("par"));
					
					scoreCardsMatch.setScore(j.getString("score"));
					scoreCardsMatch.setPutts(j.getString("putts"));
					scoreCardsMatch.setPenalties(j.getString("penalties"));
					scoreCardsMatch.setDriving_distance(j.getString("driving_distance"));
					scoreCardsMatch.setDirection(j.getString("direction"));
					
					/*
					 * 获得T台的数组
					 */
					JSONArray array = j.getJSONArray("tee_boxes");
					int count = 0;
					for(int l=0;l<array.length();l++){
						JSONObject jj = array.getJSONObject(l);
						TeeBoxsMatch teeBoxsMatch = new TeeBoxsMatch();
						count++;
						teeBoxsMatch.setColor(jj.getString("color"));
						teeBoxsMatch.setDistance_from_hole(jj.getString("distance_from_hole"));
						teeBoxsMatch.setUsed(jj.getString("used"));
						if(jj.getString("used").equals("true")){
							color.add(jj.getString("color"));
							distance.add(jj.getString("distance_from_hole"));
						}
						teeBoxsMatchs.add(teeBoxsMatch);
					}
					scoreCardsMatch.setCount(count);
					scoreCardsMatch.setTeeboxs(teeBoxsMatchs);
					scoreCardsMatchs.add(scoreCardsMatch);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what=1;
			msg.obj = jsonArrayData;
			handler.sendMessage(msg);
		}
			
			
		
	}
	
	/**
	 * 获取头像
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
		
		bitmap = HttpUtils.imageloder(portrait);
		Message msg = handler.obtainMessage();
		msg.what = 2;
		handler.sendMessage(msg);	
			
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
	/**
	 * 判断文件是否存在
	 */
	public boolean fileIsExists(){

        File f=new File("/mnt/sdcard/testfile");

          if(!f.exists()){

                     return false;

             }

             return true;

      }
	/**
	 * 从文件中读取图片
	 */
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
	/*
     * 提示加载
     */
     public  void   showProgressDialog(String title,String message){
            if(progressDialog ==null){
                   progressDialog = ProgressDialog.show( this, title, message,true,true );

           } else if (progressDialog .isShowing()){
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
