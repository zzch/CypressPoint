package cn.com.zcty.ILovegolf.activity.view.myself;


import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;

import org.apache.commons.codec.binary.BinaryCodec;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.HomePageActivity;
import cn.com.zcty.ILovegolf.activity.view.QuickScoreActivity;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.tools.CircleImageView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class Myself extends Activity {
	private RelativeLayout headLayout;
	private RelativeLayout opinionLayout;
	private Button exitButton;
	private String gender;
	private String description;
	private String birthday;
	private TextView signTextView;
	private String  url;
	private CircleImageView imageHead;
	private ImageView image_bg;
	private Bitmap bitmap ;
	private TextView nameTextView;
	private String year;
	private Bitmap bmp;
	private String type;
	private RelativeLayout accountRelativeLayout;
	private ProgressDialog progressDialog;
	//private RelativeLayout settingRelativeLayout;
	Intent intent;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				SharedPreferences sp = getSharedPreferences("register", MODE_PRIVATE);	
				Editor editor = sp.edit();	
				editor.putString("description", description);
				editor.commit();
				signTextView.setText(description);
				getListeners();
			}
			if(msg.what==2 ){
				
				if(url==null){
					bmp=BitmapFactory.decodeResource(Myself.this.getBaseContext().getResources(), R.drawable.hugh);
					//saveMyBitmap(bmp);
					hideProgressDialog();
				}else{
					new Imageloder().start();
				}
			}
		};
	};
	Handler handler1 = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				hideProgressDialog();
				//image_bg.setImageAlpha(80);
				imageHead.setImageBitmap(bitmap);
				//头像背景模糊
			//	FileUtil.blur(FileUtil.converToBitmap(100,100),Myself.this,headLayout);
				FileUtil.saveMyBitmap(bitmap);
			}
		
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myself);
		ShouYeActivity.getInstance().addActivity(this);
		initView();		
		setListeners();
		new Ziliao().start();
		
			if(FileUtil.fileIsExists()){
				imageHead.setImageBitmap(FileUtil.converToBitmap(100,100));
				//头像背景模糊
				//FileUtil.blur(FileUtil.converToBitmap(100,100),Myself.this,headLayout);
			}else{
			new Touxiang().start();
			showProgressDialog("提示", "正在加载", this);
			}
	
	}
	private void setListeners() {
		
          exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myself.this,SelecPicpupExit.class);
				startActivity(intent);
				
			}
		});
		
		/*settingRelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myself.this,SettionsActivity.class);
				startActivity(intent);
				
			}
		});*/
	}
	public void onclick(View v){
		
		switch (v.getId()) {
		case R.id.k_back:
			intent=new Intent(Myself.this,HomePageActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		intent=new Intent(Myself.this,HomePageActivity.class);
		startActivity(intent);
		finish();
	}
	private void getListeners() {
		accountRelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences sp = getSharedPreferences("register", MODE_PRIVATE);
				type = sp.getString("type", "type");
				Intent intent = new Intent();
				if(type.equals("guest")){
					intent.setClass(Myself.this, BingDingActivity.class);
					startActivity(intent);
				}else{
					intent.setClass(Myself.this, SettingActivity.class);
					startActivity(intent);
				}
				finish();
			}
		});

		headLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myself.this,InformationChangesActivity.class);
				intent.putExtra("birthday", birthday);
				intent.putExtra("year", year);
				startActivity(intent);
				//finish();
			}
		});
		opinionLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myself.this,FeedBackActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	private void initView() {
		opinionLayout = (RelativeLayout) findViewById(R.id.setting_opinion);
		accountRelativeLayout = (RelativeLayout) findViewById(R.id.setting_account);
		nameTextView = (TextView) findViewById(R.id.myself_name);		
		headLayout = (RelativeLayout) findViewById(R.id.myself_head_self);
		signTextView = (TextView) findViewById(R.id.myself_sign);
		imageHead = (CircleImageView) findViewById(R.id.myself_head);
		image_bg = (ImageView) findViewById(R.id.myself_bg);
		exitButton = (Button) findViewById(R.id.setting_exit);
		//settingRelativeLayout = (RelativeLayout) findViewById(R.id.myself_setting);
		SharedPreferences sp = getSharedPreferences("register", MODE_PRIVATE);
		String name = sp.getString("nickname", "nickname");
		nameTextView.setText(name);
	}
	
	class Ziliao extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.INFORMATION+"token="+token;
			String jsonData = HttpUtils.HttpClientGet(path);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				gender = jsonObject.getString("gender");
				description = jsonObject.getString("description");
				Log.i("xihuan", description);
				//if(jsonObject.getString("birthday").equals("null")){
					birthday = jsonObject.getString("birthday");
			/*	}else{
				}*/
					Log.i("birthday", birthday);
					if(!birthday.equals("null")){					
						birthday =Long.parseLong(jsonObject.getString("birthday"))*1000+"";
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						birthday = format.format(Long.parseLong(jsonObject.getString("birthday"))*1000);
						SimpleDateFormat formats = new SimpleDateFormat("yyyy");
						year = formats.format(Long.parseLong(jsonObject.getString("birthday"))*1000);
					}else{
						year = "1980";
					}
				
				//Log.i("birthday", birthday);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	}
	
	class Touxiang extends Thread{
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
			msg.obj = url;
			handler.sendMessage(msg);
		}
	}
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
	
	/*
     * 提示加载
     */
     public   void  showProgressDialog(String title,String message,Activity context){
            if(progressDialog ==null){
                   progressDialog = ProgressDialog.show( context, title, message,true,true );

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

