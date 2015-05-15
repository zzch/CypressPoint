package cn.com.zcty.ILovegolf.activity.view;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentOne;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentTwo;
import cn.com.zcty.ILovegolf.tools.CircleImageView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class RankingStatics extends FragmentActivity{
	private ArrayList<Fragment> arrayFragment = new ArrayList<Fragment>();
	private ArrayList<String> scoreArrayList = new ArrayList<String>();
	private ArrayList<String> statusArrayList = new ArrayList<String>();
	private ArrayList<String> parArrayList = new ArrayList<String>();
	private Bitmap bitmap;
	private String portrait;
	private String username;
	private String ranking;
	private String schedule;
	private String score;
	private String par;
	private TextView usernameTextView;
	private TextView rankingTextView;//排名
	private TextView scheduleTextView;//进度
	private TextView scoreTextView;//成绩
	private TextView parTextView;
	private String match_uuid;
	private ViewPager tablePager;;
	private RadioGroup radioGroup;
	private RadioButton radioButton_qian;
	private RadioButton radioButton_hou;
	private CircleImageView totleImage;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				getData();
				if(!portrait.equals("null")){
					if(!fileIsExists()){						
						new Imageloder().start();
					}else{
						totleImage.setImageBitmap(converToBitmap(100,100));
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rankingstatics);
		initView();
		setListeners();
		
		new MyTask().start();
	}
	/*
	 * 监听器
	 */
	private void setListeners() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@SuppressLint("NewApi")
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (group.getCheckedRadioButtonId()) {
				case R.id.mainTabs_radio_qian:
					radioButton_qian.setBackgroundResource(R.drawable.group_1);
					radioButton_qian.setText("前九");
					radioButton_qian.setTextColor(0xff17191c);
					radioButton_hou.setBackgroundResource(R.drawable.group_2);
					radioButton_hou.setText("后九");
					radioButton_hou.setTextColor(0xffF8d57a);
					tablePager.setCurrentItem(0);
					break;

				case R.id.mainTabs_radio_hou:
					radioButton_hou.setBackgroundResource(R.drawable.group_3);
					radioButton_hou.setText("后九");
					radioButton_hou.setTextColor(0xff17191c);
					radioButton_qian.setBackgroundResource(R.drawable.group_4);
					radioButton_qian.setText("前九");
					radioButton_qian.setTextColor(0xffF8d57a);
					tablePager.setCurrentItem(1);
					break;
				}
			}
		});
		tablePager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					radioButton_qian.setChecked(true);
					break;

				case 1:
					radioButton_hou.setChecked(true);
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@SuppressLint("NewApi")
			@Override
			public void onPageScrollStateChanged(int position) {

			}
		});
	}
	/*
	 * 点击事件
	 */
	public void onclick(View v){
		switch (v.getId()) {
		case R.id.scorecard_back:
			finish();
			break;

		
		}
	}
	/*
	 * (non-Javadoc)返回键
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
	/*
	 * 处理数据
	 */
	private void getData() {
		arrayFragment.add(new StaticsFragmentOne(parArrayList,scoreArrayList,statusArrayList));
		arrayFragment.add(new StaticsFragmentTwo(parArrayList,scoreArrayList,statusArrayList));
		tablePager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
		usernameTextView.setText(username);	
		rankingTextView.setText(ranking);
		scheduleTextView.setText(schedule);
		scoreTextView.setText(score);
		parTextView.setText(par);
	}
	/*
	 * 初始化
	 */
	private void initView() {
		match_uuid = getIntent().getStringExtra("uuid");
		usernameTextView = (TextView) findViewById(R.id.competition_username);
		rankingTextView = (TextView) findViewById(R.id.competition_paiming);
		scheduleTextView = (TextView) findViewById(R.id.competition_jindu);
		scoreTextView = (TextView) findViewById(R.id.competition_chengji);
		parTextView = (TextView) findViewById(R.id.competition_par);
		totleImage = (CircleImageView) findViewById(R.id.myself_head);
		
		tablePager = (ViewPager) findViewById(R.id.viewpager);
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioButton_qian = (RadioButton) findViewById(R.id.mainTabs_radio_qian);
		radioButton_hou = (RadioButton) findViewById(R.id.mainTabs_radio_hou);
		radioButton_qian.setChecked(true);
		radioButton_qian.setBackgroundResource(R.drawable.group_1);
	}
	/**
	 * fragment的适配器
	 * @author Administrator
	 *
	 */
	class MyFragmentPagerAdapter  extends FragmentPagerAdapter{

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return arrayFragment.get(position);
		}

		@Override
		public int getCount() {
			return arrayFragment.size();
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
	/**
	 * 拿到统计数据
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

			SharedPreferences sp = getSharedPreferences("register",  Context.MODE_PRIVATE);
			String token = sp.getString("token", "token");
			String path = APIService.RANKINGINFORMATION+"uuid="+match_uuid+"&token="+token;
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("js", jsonData);
			Log.i("js", path);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				String user = jsonObject.getString("user");
				JSONObject usersJsonObject = new JSONObject(user);
				portrait = usersJsonObject.getString("portrait");//头像地址
				if(!portrait.equals("null")){
					JSONObject urlJsonObject = new JSONObject(portrait);
					portrait = urlJsonObject.getString("url");
				}
				username = usersJsonObject.getString("nickname");//用户昵称
				ranking = jsonObject.getString("position");//排名
				schedule = jsonObject.getString("recorded_scorecards_count");//进度
				score = jsonObject.getString("strokes");//成绩
				par = jsonObject.getString("total");//距标准杆
				
				String jsonArray = jsonObject.getString("scorecards");
				JSONObject jsonObject2 = new JSONObject(jsonArray);	
				JSONArray jsonArray2 = jsonObject2.getJSONArray("par");

				for(int i=0;i<jsonArray2.length();i++){
					if(jsonArray2.getString(i).equals("null")){
						parArrayList.add("nul");
						Log.i("asdfdsf", jsonArray2.getString(i));
					}else{
						parArrayList.add(jsonArray2.getString(i));
						Log.i("asdfdsf", jsonArray2.getString(i));
					}

				}
				JSONArray jsonArray3 = jsonObject2.getJSONArray("score");
				for(int i=0;i<jsonArray3.length();i++){
					if(jsonArray3.getString(i).equals("null")){
						scoreArrayList.add("nul");
					}else{
						scoreArrayList.add(jsonArray3.getString(i));
					}
				}
				JSONArray jsonArray4 = jsonObject2.getJSONArray("status");
				for(int i=0;i<jsonArray4.length();i++){
					if(jsonArray4.getString(i).equals("null")){
						statusArrayList.add("nul");
					}else{
						statusArrayList.add(jsonArray4.getString(i));
					}
				}
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
}
