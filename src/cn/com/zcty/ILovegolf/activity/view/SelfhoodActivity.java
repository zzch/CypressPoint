package cn.com.zcty.ILovegolf.activity.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.competition.CompetitionAdd;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.activity.view.myself.SelectPicPopupWindow;
import cn.com.zcty.ILovegolf.model.CompetitionAddmatch;
import cn.com.zcty.ILovegolf.tools.CircleImageView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class SelfhoodActivity extends Activity{
	private CompetitionAddmatch add = new CompetitionAddmatch();//存放进入后房间的信息
	private Button photoButton;
	private Bitmap image = null;
	private CircleImageView headImage;
	private EditText userNameEditText;
	private RadioGroup sexRadioGroup;
	private LinearLayout linear;
	private Button baocunButton;
	private ImageView sex_image;
	private String sex = "";
	private String nickname = "";
	private String success;
	private String id;
	private RadioButton radio_nan;
	private RadioButton radio_nv;
	private ProgressDialog progressDialog;
	private String cunzai;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(msg.what==1){
					if(msg.obj.equals("404")||msg.obj.equals("500")){
						Toast.makeText(SelfhoodActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
					}else if(msg.obj.equals("403")){
						Toast.makeText(SelfhoodActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
						FileUtil.delFile();
						Intent intent = new Intent(SelfhoodActivity.this,ShouYeActivity.class);
						startActivity(intent);
						finish();
					}else{
						if(success==null){
							FileUtil.delFile();
							Toast.makeText(SelfhoodActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
						}else{
						if(success.equals("success")){
							Toast.makeText(SelfhoodActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
							
							if(cunzai.equals("1")){
								Log.i("sdfsdfsdf", "yanzheng");
								Intent intent = new Intent(SelfhoodActivity.this, AddMatchActivity.class);
								
								startActivity(intent);
								finish();
							}else{
								
								Intent intent = new Intent(SelfhoodActivity.this,InviteActivity.class);
								//intent.setClass(SelfhoodActivity.this,InviteActivity.class);
								intent.putExtra("add", add);
								intent.putExtra("uuid", id);
								startActivity(intent);
								finish();
							}
							
						}else{
							FileUtil.delFile();
							Toast.makeText(SelfhoodActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
						}}
					}
				}
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_selfhood);
		initView();
		setListeners();
	}
	/*
	 * 初始化
	 */
	private void initView() {
	  //photoButton = (Button) findViewById(R.id.selfhood_head_button);
		headImage = (CircleImageView) findViewById(R.id.selfhood_head_image);
		userNameEditText = (EditText) findViewById(R.id.selfhood_username);
		sexRadioGroup = (RadioGroup) findViewById(R.id.main_radio);
		baocunButton = (Button) findViewById(R.id.selfhood_baocun);
		
		baocunButton.setBackgroundColor(0xff64af66);
		baocunButton.setTextColor(0xffededed);
		
		linear = (LinearLayout) findViewById(R.id.linear);
		radio_nan = (RadioButton) findViewById(R.id.mainTabs_radio_nan);
		radio_nv = (RadioButton) findViewById(R.id.mainTabs_radio_nv);
		id = getIntent().getStringExtra("uuid");
		cunzai  = getIntent().getStringExtra("cunzai");
		add = (CompetitionAddmatch) getIntent().getSerializableExtra("add");
	}
	private void setListeners(){
		userNameEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()>6){
					String f = s.toString().substring(0, 6);
					userNameEditText.setText(f);
				}
			}
		});
		
		/*
		 *获得头像 
		 */
		headImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(SelfhoodActivity.this,
						SelectPicPopupWindow.class), 1);
				//showProgressDialog("提示", "正在获得头像");
			}
		});
		/*
		 * 判断选择的性别
		 */
		sexRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.mainTabs_radio_nan:
					radio_nan.setBackgroundResource(R.drawable.nan_1);
					sex = "1";
					radio_nv.setBackgroundResource(R.drawable.nv);
					baocunButton.setBackgroundColor(0xff09850c);
					baocunButton.setTextColor(0xffffffff);
					break;

				case R.id.mainTabs_radio_nv:
					radio_nv.setBackgroundResource(R.drawable.nv_1);
					sex = "2";
					radio_nan.setBackgroundResource(R.drawable.nan);
					baocunButton.setBackgroundColor(0xff09850c);
					baocunButton.setTextColor(0xffffffff);
					break;
				}
			}
		});
		//保存信息
		baocunButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				nickname = userNameEditText.getText().toString();
				if(image==null){
					Toast.makeText(SelfhoodActivity.this, "请先上传头像", Toast.LENGTH_LONG).show();
				}else if(sex.equals("")){
					Toast.makeText(SelfhoodActivity.this, "请先选择性别", Toast.LENGTH_LONG).show();
				}else if(nickname.equals("")){
					Toast.makeText(SelfhoodActivity.this, "请先填写昵称", Toast.LENGTH_LONG).show();
				}else{
					
				
				new Selfhood().start();
			}
				}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(cunzai.equals("1")){
			Intent intent = new Intent(SelfhoodActivity.this,QuickScoreActivity.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			finish();
		}else{
			
			finish();
		}
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.analyze_result_back:
			if(cunzai.equals("1")){
				Intent intent = new Intent(SelfhoodActivity.this,QuickScoreActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
				finish();
			}else{
				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
				finish();
			}
			
			break;

		default:
			break;
		}
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	hideProgressDialog();
	}
	
	/**
	 * 返回头像
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//showProgressDialog("提示", "正在获得头像");
		switch (resultCode) {
		case 1:
			if (data != null) {
				//取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
				Uri mImageCaptureUri = data.getData();
				//返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
				if (mImageCaptureUri != null) {

					try {

						//这个方法是根据Uri获取Bitmap图片的静态方法
						image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);	
						image = FileUtil.comp(image);
						Log.i("ceshipath", image+"1");
						if (image != null) {
							//showProgressDialog("提示","正在上传");

							String phoneName = android.os.Build.BRAND; 
							if(phoneName.equals("samsung")){								
								image = FileUtil.rotaingImageView(90,image);							
							}
							headImage.setImageBitmap(image);
							hideProgressDialog();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Bundle extras = data.getExtras();
					if (extras != null) {
						//这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
						image = extras.getParcelable("data");
						if (image != null) {
							//showProgressDialog("提示","正在上传");
							image = FileUtil.rotaingImageView(0,image);
							image = FileUtil.comp(image);
							Log.i("ceshipath", image+"2");
							headImage.setImageBitmap(image);
							hideProgressDialog();
						}
					}
				}

			}
			break;
		default:
			break;

		}
	}
	/**
	 * 上传用户信息
	 * @author Administrator
	 *
	 */
	class Selfhood extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			FileUtil.saveMyBitmap(image);
			
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.GENGGXIN+"token="+token+"&gender="+sex+"&nickname="+nickname;
			String jsonData = HttpUtils.uploadImage(path, "/mnt/sdcard/testfile/golf.jpg");
			Log.i("selfhood", path);
			Log.i("selfhood", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				success = jsonObject.getString("result");
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
	}
	
	
	
	
	@Override 
    public boolean dispatchTouchEvent(MotionEvent ev) { 
       if (ev.getAction() == MotionEvent.ACTION_DOWN) { 
           View v = getCurrentFocus(); 
           if (isShouldHideInput(v, ev)) { 
   
               InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE ); 
               if (imm != null ) { 
                   imm.hideSoftInputFromWindow(v.getWindowToken(), 0); 
                   linear.requestFocus();
               } 
           } 
           return super .dispatchTouchEvent(ev); 
       } 
       // 必不可少，否则所有的组件都不会有TouchEvent了  
       if (getWindow().superDispatchTouchEvent(ev)) { 
          return true ; 
       } 
       return onTouchEvent(ev); 
   } 

	public  boolean isShouldHideInput(View v, MotionEvent event) { 
        if (v != null && (v instanceof EditText)) { 
            int[] leftTop = { 0, 0 }; 
            //获取输入框当前的location位置  
            v.getLocationInWindow(leftTop); 
            int left = leftTop[0]; 
            int top = leftTop[1]; 
            int bottom = top + v.getHeight(); 
            int right = left + v.getWidth(); 
            if (event.getX() > left && event.getX() < right 
                    && event.getY() > top && event.getY() < bottom) { 
                // 点击的是输入框区域，保留点击EditText的事件  
                return false ; 
            } else { 
                return true ; 
            } 
        } 
        return false ; 
    }
	/*
	 * 提示加载
	 */
	public  void  showProgressDialog(String title,String message){
		if(progressDialog==null){
			progressDialog = ProgressDialog.show(this, title, message,true,true);

		}else if(progressDialog.isShowing()){
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
