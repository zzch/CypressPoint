package cn.com.zcty.ILovegolf.activity.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.activity.view.myself.SelectPicPopupWindow;
import cn.com.zcty.ILovegolf.tools.CircleImageView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class SelfhoodActivity extends Activity{
	private Button photoButton;
	private Bitmap image = null;
	private CircleImageView headImage;
	private EditText userNameEditText;
	private RadioGroup sexRadioGroup;
	private LinearLayout linear;
	private Button baocunButton;
	private String sex = "";
	private String nickname = "";
	private String success;
	private String id;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(msg.what==1){
					if(msg.obj.equals("404")||msg.obj.equals("505")){
						Toast.makeText(SelfhoodActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
					}else if(msg.obj.equals("403")){
						Toast.makeText(SelfhoodActivity.this, "此帐号在其它android手机登录，请检查身份信息是否被泄漏", Toast.LENGTH_LONG).show();
						FileUtil.delFile();
						Intent intent = new Intent(SelfhoodActivity.this,ShouYeActivity.class);
						startActivity(intent);
						finish();
					}else{
						if(success.equals("success")){
							Toast.makeText(SelfhoodActivity.this, "保存成功", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(SelfhoodActivity.this,InviteActivity.class);
							intent.putExtra("uuid", id);
							startActivity(intent);
							finish();
						}else{
							FileUtil.delFile();
							Toast.makeText(SelfhoodActivity.this, "保存失败", Toast.LENGTH_LONG).show();
						}
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
		photoButton = (Button) findViewById(R.id.selfhood_head_button);
		headImage = (CircleImageView) findViewById(R.id.selfhood_head_image);
		userNameEditText = (EditText) findViewById(R.id.selfhood_username);
		sexRadioGroup = (RadioGroup) findViewById(R.id.main_radio);
		baocunButton = (Button) findViewById(R.id.selfhood_baocun);
		linear = (LinearLayout) findViewById(R.id.linear);
		id = getIntent().getStringExtra("uuid");
	}
	private void setListeners(){
		/*
		 *获得头像 
		 */
		photoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(SelfhoodActivity.this,
						SelectPicPopupWindow.class), 1);
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
					sex = "1";
					break;

				case R.id.mainTabs_radio_nv:
					sex = "2";
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
		finish();
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.analyze_result_back:
			finish();
			break;

		default:
			break;
		}
	}
	/**
	 * 返回头像
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
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
						image = comp(image);
						Log.i("ceshipath", image+"1");
						if (image != null) {
							//showProgressDialog("提示","正在上传");

							String phoneName = android.os.Build.BRAND; 
							if(phoneName.equals("samsung")){								
								image = rotaingImageView(90,image);							
							}
							headImage.setImageBitmap(image);
							
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
							image = rotaingImageView(0,image);
							image = comp(image);
							Log.i("ceshipath", image+"2");
							headImage.setImageBitmap(image);

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
			saveMyBitmap("golf");
			
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
	
	/**
	 * 压缩图片
	 * @param image
	 * @return
	 */
	private Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();        
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;//这里设置高度为800f
		float ww = 480f;//这里设置宽度为480f
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inPurgeable = true;
		newOpts.inSampleSize = be;//设置缩放比例
		newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
	}	
	private Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024>100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩        
			baos.reset();//重置baos即清空baos
			options -= 10;//每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	/*
	 * 旋转图片 
	 * @param angle 
	 * @param bitmap 
	 * @return Bitmap 
	 */ 
	public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {  
		//旋转图片 动作   
		Matrix matrix = new Matrix();;  
		matrix.postRotate(angle);  
		System.out.println("angle2=" + angle);  
		// 创建新的图片   
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
		return resizedBitmap;  
	}
	/**
	 * 把bitmap存入手机文件目录
	 * @param bitName
	 */
	@SuppressLint("SdCardPath")
	public void saveMyBitmap(String bitName)  {
		File f = new File("/mnt/sdcard/testfile"); 
		if(f.exists()){
			f.delete();
		}else{
			f.mkdir();
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream("/mnt/sdcard/testfile/golf.jpg");
			image.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
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
	
}
