package cn.com.zcty.ILovegolf.activity.view;



import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.competition.CompetitionAdd;
import cn.com.zcty.ILovegolf.activity.view.competition.CompetitionScordActivity;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.model.CompetitionAddmatch;
import cn.com.zcty.ILovegolf.model.CompetitionHome;
import cn.com.zcty.ILovegolf.model.TeeBoxs;
import cn.com.zcty.ILovegolf.tools.SecurityPasswordEditText;
import cn.com.zcty.ILovegolf.tools.SecurityPasswordEditText.OnEditTextListener;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import cn.com.zcty.ILovegolf.utils.KeyboardUtil;
import cn.com.zcty.ILovegolf.utils.ViewUtil;

public class AddMatchActivity extends Activity {
	private CompetitionAddmatch add = new CompetitionAddmatch();//存放进入后房间的信息
	private ArrayList<TeeBoxs> titai = new ArrayList<TeeBoxs>();
	private ArrayList<String> color = new ArrayList<String>();//存放T颜色
	private String passWord;
	private String message = null;
	private String uuid = null;
	private ProgressDialog progressDialog;
	private KeyboardUtil kbUtil; 
	private EditText etPwdOne, etPwdTwo, etPwdThree, etPwdFour, etPwdText;  
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			hideProgressDialog();
			if(msg.what==1){
				if(msg.what==1){
					if(msg.obj.equals("404")||msg.obj.equals("500")){
						Toast.makeText(AddMatchActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
					}else if(msg.obj.equals("401")){
						FileUtil.delFile();
						Intent intent = new Intent(AddMatchActivity.this,ShouYeActivity.class);
						startActivity(intent);
						Toast.makeText(AddMatchActivity.this, "帐号异地登录，请重新登录", Toast.LENGTH_LONG).show();
						finish();
					}else{
						
				if(uuid==null||uuid.equals("")){
				new AddMatchYiChang().start();
				}else{
					//验证成功
					new User().start();
					
				}}
					}
			}
			if(msg.what==2){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(AddMatchActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(AddMatchActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(AddMatchActivity.this,ShouYeActivity.class);
					startActivity(intent);
					finish();
				}else{
					if(!etPwdFour.getText().toString().equals("")&&etPwdText.getText().toString().length()==4){
						etPwdOne.setText("");
						etPwdTwo.setText("");
						etPwdThree.setText("");
						etPwdFour.setText("");
					}
					
				Toast.makeText(AddMatchActivity.this, message, Toast.LENGTH_LONG).show();
				//passwordEditText.setInputnumber(null);//如果错误就清空当前输入的密码
				
				}
			}
			if(msg.what==3){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(AddMatchActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
				}else if(msg.obj.equals("403")){
					Toast.makeText(AddMatchActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(AddMatchActivity.this,ShouYeActivity.class);
					startActivity(intent);
					overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
					finish();
				}else{
					
					Intent intent = new Intent();
					//if(FileUtil.fileIsExists()){
						intent.setClass(AddMatchActivity.this,CompetitionAdd.class);
					//}else{
						//intent.setClass(AddMatchActivity.this,SelfhoodActivity.class);
					//}
				
				intent.putExtra("uuid", uuid);
				intent.putExtra("add", add);
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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addematch);
		initView();
		setListeners();
		getData();
	}

	private void setListeners() {
		
		 etPwdText.addTextChangedListener(new TextWatcher() {  
	            @Override  
	            public void onTextChanged(CharSequence arg0, int arg1, int arg2,  
	                    int arg3) {  
	            }  
	  
	            @Override  
	            public void beforeTextChanged(CharSequence arg0, int arg1,  
	                    int arg2, int arg3) {  
	            }  
	  
	            @Override  
	            public void afterTextChanged(Editable arg0) {  
	            	if((etPwdOne.getText().toString().equals("")&&!etPwdTwo.getText().toString().equals(""))
	            	||(etPwdOne.getText().toString().equals("")&&!etPwdThree.getText().toString().equals(""))
	            	||(etPwdOne.getText().toString().equals("")&&!etPwdFour.getText().toString().equals(""))
	            			){
	            		Log.i("aaaa","aaaa");
	            		etPwdOne.setText("");
						etPwdTwo.setText("");
						etPwdThree.setText("");
						etPwdFour.setText("");
	            	}
	                if (etPwdFour.getText() != null  
	                        && etPwdFour.getText().toString().length() >= 1&&etPwdText.getText().length()==4) {  
	                	passWord = etPwdText.getText().toString();
	                	showProgressDialog("提示", "正在验证密码",AddMatchActivity.this);
						new AddMatch().start();
	                }  
	            }  
	        });  
	}
	public void getData(){
		 kbUtil = new KeyboardUtil(AddMatchActivity.this);  
	        ArrayList<EditText> list = new ArrayList<EditText>();  
	        list.add(etPwdOne);  
	        list.add(etPwdTwo);  
	        list.add(etPwdThree);  
	        list.add(etPwdFour);  
	        list.add(etPwdText);  
	        kbUtil.setListEditText(list);  
	        etPwdOne.setInputType(InputType.TYPE_NULL);  
	        etPwdTwo.setInputType(InputType.TYPE_NULL);  
	        etPwdThree.setInputType(InputType.TYPE_NULL);  
	        etPwdFour.setInputType(InputType.TYPE_NULL);  
	}
	public void initView(){
		etPwdOne = (EditText) findViewById(R.id.etPwdOne_setLockPwd);  
        etPwdTwo = (EditText) findViewById(R.id.etPwdTwo_setLockPwd);  
        etPwdThree = (EditText) findViewById(R.id.etPwdThree_setLockPwd);  
        etPwdFour = (EditText) findViewById(R.id.etPwdFour_setLockPwd);  
        etPwdText = (EditText) findViewById(R.id.etPwdText_setLockPwd);  
	}
	/*
	 * 点击事件
	 */
	public void onclick(View v){
		switch (v.getId()) {
		case R.id.add_back:
			Intent intent = new Intent(AddMatchActivity.this,QuickScoreActivity.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
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
		Intent intent = new Intent(AddMatchActivity.this,QuickScoreActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
		finish();
	}
	/**
	 * 验证密码
	 * @author Administrator
	 *
	 */
	class AddMatch extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			//用户的token
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.CHECKINGPASSWORD+"token="+token+"&password="+passWord;
			String jsonData = HttpUtils.HttpClientPost(path);
			Log.i("addMatch", path);
			Log.i("addMatch",jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				uuid = jsonObject.getString("uuid");
				
				
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
	}
	/**
	 * 验证密码
	 * @author Administrator
	 *
	 */
	class AddMatchYiChang extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			//用户的token
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.CHECKINGPASSWORD+"token="+token+"&password="+passWord;
			String jsonData = HttpUtils.HttpClientPost(path);
			Log.i("addMatch", path);
			Log.i("addMatch",jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				message = jsonObject.getString("message");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 2;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
	}
	/**
	 * 用户的资料
	 * @author Administrator
	 *
	 */
	class User extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			
			String path = APIService.MATCHINFATION+"token="+token+"&uuid="+uuid;
			String jsonData = HttpUtils.HttpClientGet(path);
			Log.i("dayin", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				String venuejs = jsonObject.getString("venue");
				JSONObject userJsonObject = new JSONObject(venuejs);
				add.setName(userJsonObject.getString("name"));
				JSONArray jsonArray = userJsonObject.getJSONArray("courses");
				for(int j=0;j<jsonArray.length();j++){
					JSONObject coursesJsonObject = jsonArray.getJSONObject(j);
					TeeBoxs teeBoxs = new TeeBoxs();
					teeBoxs.setName(coursesJsonObject.getString("name"));
					JSONArray boxesArray = coursesJsonObject.getJSONArray("tee_boxes");
					for(int t=0;t<boxesArray.length();t++){
						color.add(boxesArray.getString(t));
					}
					teeBoxs.setBoxs(color);
					titai.add(teeBoxs);
				}
				add.setTitai(titai);
				String ownerjs = jsonObject.getString("owner");
				JSONObject ownerJsonObject = new JSONObject(ownerjs);
				add.setUseName(ownerJsonObject.getString("nickname"));
				String portrait = ownerJsonObject.getString("portrait");
				Log.i("shijienihao",portrait);
				JSONObject portraitJsonObject = new JSONObject(portrait);
				add.setPortrait(portraitJsonObject.getString("url"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				Message msg = handler.obtainMessage();
				msg.what=3;
				msg.obj = jsonData;
				handler.sendMessage(msg);
			
		}
	}
	
	/*
     * 提示加载
     */
     public   void  showProgressDialog(String title,String message,Activity context){
            if(progressDialog ==null){
                   progressDialog = ProgressDialog.show( context, title, message,false,false);
                   

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
