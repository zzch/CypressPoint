package cn.com.zcty.ILovegolf.activity.view;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.model.Scorecards;
import cn.com.zcty.ILovegolf.model.Setcard;
import cn.com.zcty.ILovegolf.tools.CircleImageView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 记分卡
 * @author Administrator
 *
 */
public class CreateScoreCard extends Activity{
	private String portrait;
	private String username;
	private Bitmap bitmap;
	
	private String ranking;
	private String schedule;
	private String score;
	private String par;
	private TextView usernameTextView;
	private TextView rankingTextView;//排名
	private TextView scheduleTextView;//进度
	private TextView scoreTextView;//成绩
	private TextView parTextView;
	
	private CircleImageView totleImage;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				if(msg.obj.equals("404")||msg.obj.equals("505")){
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
						converToBitmap(100,100);
					}
				}
				}
			}
			if(msg.what==2){
				totleImage.setImageBitmap(bitmap);
				saveMyBitmap(bitmap);
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scord);
		initView();
		new MyTask().start();
	}
	private void initView() {
		totleImage = (CircleImageView) findViewById(R.id.myself_head);
		usernameTextView = (TextView) findViewById(R.id.competition_username);
		rankingTextView = (TextView) findViewById(R.id.competition_paiming);
		scheduleTextView = (TextView) findViewById(R.id.competition_jindu);
		scoreTextView = (TextView) findViewById(R.id.competition_chengji);
		parTextView = (TextView) findViewById(R.id.competition_par);
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
		
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			Intent intent=getIntent();
			String	uuid = intent.getStringExtra("uuid");
			String path = APIService.MATCHINFORMATION+"token="+token+"&uuid="+uuid;
			String jsonArrayData = HttpUtils.HttpClientGet(path);
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
				username = usersJsonObject.getString("nickname");//用户昵称
				/*
				 * 获取player
				 */
				ranking = userJsonObject.getString("position");//排名
				schedule = userJsonObject.getString("recorded_scorecards_count");//进度
				score = userJsonObject.getString("strokes");//成绩
				par = userJsonObject.getString("total");//距标准杆
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
}
