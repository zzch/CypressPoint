package cn.com.zcty.ILovegolf.activity.view.myself;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class SelecPicpupExit extends Activity{
	private Button settingButton;
	private Button exitButton;
	private Button quxiaoButton;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(msg.obj.equals("500")||msg.obj.equals("404")){
					Toast.makeText(SelecPicpupExit.this, "网络异常,退出失败", Toast.LENGTH_LONG).show();
				}else{
				delFile();
				
				SharedPreferences sharedpre=getSharedPreferences("register",Context.MODE_PRIVATE);
				Editor editor = sharedpre.edit();
				editor.clear().apply();
				Intent intent = new Intent(SelecPicpupExit.this,ShouYeActivity.class);
				startActivity(intent);
				finish();
				}
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_dialog_exit);
		initView();
		setListeners();
	}
	private void setListeners() {
		SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
		String f = sp.getString("isfangshi", "0");
		if(f.equals("0")){
			settingButton.setVisibility(View.VISIBLE);
		}else{
			settingButton.setVisibility(View.GONE);
		}
		quxiaoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				new SginOut().start();
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
	private void initView() {
		settingButton = (Button) findViewById(R.id.btn_setting);
		exitButton = (Button) findViewById(R.id.btn_exit);
		quxiaoButton = (Button) findViewById(R.id.btn_cancel);
	}
	
	//删除文件
		public  void delFile(){
			File file = new File("/mnt/sdcard/testfile");
			deleteFile(file);
		}
		public void deleteFile(File file) {
			
			if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
			file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
			File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
			for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
			this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
			}
			}
			file.delete();
			} else {
			Log.i("tishis","文件不存在！"+"\n");
			}
			}
	class SginOut extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.SIGNOUT+"token="+token;
			String jsonData = HttpUtils.HttpClientDelete(path);
			Message msg = handler.obtainMessage();
			msg.what=1;
			msg.obj = jsonData;
			handler.sendMessage(msg);
			
		}
	}
}
