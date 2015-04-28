package cn.com.zcty.ILovegolf.activity.view.myself;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.adapter.ArrayDayNumberWheelAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.ArrayMonthNumberWheelAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.ArrayWheelAdapter;
import cn.com.zcty.ILovegolf.activity.adapter.ArrayYearNumberWheelAdapter;
import cn.com.zcty.ILovegolf.activity.view.BaseActivity;
import cn.com.zcty.ILovegolf.activity.view.TabHostActivity;
import cn.com.zcty.ILovegolf.tools.CircleImageView;
import cn.com.zcty.ILovegolf.tools.OnWheelChangedListener;
import cn.com.zcty.ILovegolf.tools.WheelView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import cn.com.zcty.ILovegolf.utils.TimeUtil;

public class InformationChangesActivity extends BaseActivity implements OnClickListener{
	private RelativeLayout headImage;
	private CircleImageView headMyImage;
	private TextView sexTextView;
	private LinearLayout brithdayLinearLayout;
	private TextView brithdayTextView;
	private RelativeLayout brithdayRelativeLayout;
	private WheelView sexWheel;
	private ArrayWheelAdapter<String> adapter;
	private WheelView yearWheelView;
	private WheelView monthWheelView;
	private WheelView dayWheelView;
	private String[] sexs = {"男","女"};
	private StringBuffer buffer;
	private ArrayYearNumberWheelAdapter yearadapter;
	private ArrayMonthNumberWheelAdapter monthdapter;
	private ArrayDayNumberWheelAdapter daydapter;
	private TextView nianlingTextView;
	private String year = "1550";
	private String moth = "01";
	private String day = "01";
	private int years;
	private WheelView quWheelView;
	private WheelView shiWheelView;
	private WheelView xianWheelView;
	private LinearLayout  areaLinearLayout;
	private TextView diquTextView;
	private StringBuffer citys;
	private Bitmap image;
	private long date;
	private File file;
	private EditText sginEditText;
	private String sgin;
	private EditText upnameEditText;
	private Button fanhuiButton;
	private String upname;
	private String imageurl;
	private ProgressDialog progressDialog;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView5;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				hideProgressDialog();
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_information);
		initView();
		setListeners();
		setUpData();
	}
	private void setUpData() {
		adapter = new ArrayWheelAdapter<String>(this, sexs);
		sexWheel.setViewAdapter(adapter);
		yearadapter = new ArrayYearNumberWheelAdapter(this);
		yearWheelView.setViewAdapter(yearadapter);
		monthdapter = new ArrayMonthNumberWheelAdapter(this);
		monthWheelView.setViewAdapter(monthdapter);
		daydapter = new ArrayDayNumberWheelAdapter(this);
		dayWheelView.setViewAdapter(daydapter);

		
		initProvinceDatas();
		quWheelView.setViewAdapter(new ArrayWheelAdapter<String>(InformationChangesActivity.this, mProvinceDatas));
		// 设置可见条目数量
		quWheelView.setVisibleItems(7);
		shiWheelView.setVisibleItems(7);
		xianWheelView.setVisibleItems(7);
		updateCities();
		updateAreas();
	}
	private void setListeners() {
		fanhuiButton.setOnClickListener(InformationChangesActivity.this);
		diquTextView.setOnClickListener(this);
		sexTextView.setOnClickListener(this);
		brithdayTextView.setOnClickListener(this);
		
		upnameEditText.addTextChangedListener(new TextWatcher() {
			
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
				upname = upnameEditText.getText().toString();
			}
		});
		sginEditText.addTextChangedListener(new TextWatcher() {
			
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
				sgin = removeAllSpace(sginEditText.getText().toString());
			}
		});
		
		headImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(InformationChangesActivity.this,
						SelectPicPopupWindow.class), 1);
			}
		});
		sexWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				sexTextView.setText(" "+sexs[newValue]);
			}
		});
		yearWheelView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				year =  (String) yearadapter.getItemText(newValue).subSequence(0, yearadapter.getItemText(newValue).length()-1);
				brithdayTextView.setText("  "+year+"-"+moth+"-"+day);
				years = Integer.parseInt(year);
				SimpleDateFormat time = new SimpleDateFormat("yyyy");
				years = Integer.parseInt(time.format(new Date()))-years;
				nianlingTextView.setText("  "+years+"");
			}
		});
		monthWheelView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				moth =  (String) monthdapter.getItemText(newValue).subSequence(0, monthdapter.getItemText(newValue).length()-1);
				if(Integer.parseInt(moth)<10){
					moth = "0"+moth;
				}
				brithdayTextView.setText("  "+year+"-"+moth+"-"+day);
			}
		});
		dayWheelView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				day =  (String) daydapter.getItemText(newValue).subSequence(0, daydapter.getItemText(newValue).length()-1);
				if(Integer.parseInt(day)<10){
					day = "0"+day;
				}
				brithdayTextView.setText("  "+year+"-"+moth+"-"+day);
			}
		});
		quWheelView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateCities();
			}
		});
		shiWheelView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateAreas();
			}
		});
		xianWheelView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
				mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
				diquTextView.setText("  "+mCurrentProviceName+"-"+mCurrentCityName+"-"+mCurrentDistrictName);
			}
		});
	}
	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = xianWheelView.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		xianWheelView.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		xianWheelView.setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = quWheelView.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		shiWheelView.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		shiWheelView.setCurrentItem(0);
		updateAreas();
	}

	private void initView() {
		headImage = (RelativeLayout) findViewById(R.id.information_head_image);
		headMyImage = (CircleImageView) findViewById(R.id.information_head);
		sexTextView = (TextView) findViewById(R.id.information_sex);
		sexWheel = (WheelView) findViewById(R.id.information_sex_wheel);
		brithdayTextView = (TextView) findViewById(R.id.information_brithday);
		brithdayLinearLayout = (LinearLayout) findViewById(R.id.information_brithday_wheel);
		yearWheelView = (WheelView) findViewById(R.id.information_brithday_wheel_year);
		monthWheelView = (WheelView) findViewById(R.id.information_brithday_wheel_month);
		dayWheelView = (WheelView) findViewById(R.id.information_brithday_wheel_day);
		nianlingTextView = (TextView) findViewById(R.id.information_year);
		diquTextView = (TextView) findViewById(R.id.information_area);
		areaLinearLayout = (LinearLayout) findViewById(R.id.information_area_wheel);
		quWheelView = (WheelView) findViewById(R.id.information_brithday_area_qu);
		shiWheelView = (WheelView) findViewById(R.id.information_brithday_area_shi);
		xianWheelView = (WheelView) findViewById(R.id.information_brithday_area_xian);
		sginEditText = (EditText) findViewById(R.id.information_qianming);
		upnameEditText = (EditText) findViewById(R.id.information_name);
		fanhuiButton = (Button) findViewById(R.id.information_back);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView3 = (ImageView) findViewById(R.id.imageView3);
		imageView5 = (ImageView) findViewById(R.id.imageView5);
		headMyImage.setImageBitmap(converToBitmap(100,100));
		SharedPreferences sp = getSharedPreferences("register", MODE_PRIVATE);
		String name = sp.getString("nickname", "nickname");
		upnameEditText.setText(" "+name);
		Intent intent = getIntent();
		brithdayTextView.setText("  "+intent.getStringExtra("birthday"));
		Log.i("brithdays", intent.getStringExtra("year"));
	
			int year =Integer.parseInt(intent.getStringExtra("year"));
			SimpleDateFormat time = new SimpleDateFormat("yyyy");
			year = Integer.parseInt(time.format(new Date()))-year;
			nianlingTextView.setText("  "+year+"");
		
		
	}
	/**
	 * 压缩读取图片
	 * @param w 压缩的大小
	 * @param h 压缩的大小
	 * @return
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

		return  Bitmap.createScaledBitmap(weak.get(),w, h, true);

		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case 1:
			if (data != null) {
				//取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
				Uri mImageCaptureUri = data.getData();
				Log.i("ceshipath", mImageCaptureUri+"");
				//返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
				if (mImageCaptureUri != null) {
					
					try {
						
						//这个方法是根据Uri获取Bitmap图片的静态方法
						image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
						/*
						 * 把图片旋转90度
						 */
						image = rotaingImageView(90,image);
						Log.i("ceshipath", image+"1");
						if (image != null) {
							headMyImage.setImageBitmap(image);	
							new GenxinHead().start();
							showProgressDialog("提示","正在上传");
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
							Log.i("ceshipath", image+"2");
							headMyImage.setImageBitmap(image);
							new GenxinHead().start();
							showProgressDialog("提示","正在上传");

						}
					}
				}

			}
			break;
		default:
			break;

		}
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.information_sex:
			if(sexWheel.getVisibility()==View.GONE){				
				sexWheel.setVisibility(View.VISIBLE);
				imageView2.setImageResource(R.drawable.image_icon_up);
			}else{
				sexWheel.setVisibility(View.GONE);	
				imageView2.setImageResource(R.drawable.image_icon);
				new GenxinSex().start();
			}
			break;
		case R.id.information_brithday:
			if(brithdayLinearLayout.getVisibility()==View.GONE){				
				brithdayLinearLayout.setVisibility(View.VISIBLE);
				imageView3.setImageResource(R.drawable.image_icon_up);
			}else{
				brithdayLinearLayout.setVisibility(View.GONE);	
				imageView3.setImageResource(R.drawable.image_icon);
				new GenxinBrithday().start();
			}
			break;
		case R.id.information_area:
			if(areaLinearLayout.getVisibility()==View.GONE){				
				areaLinearLayout.setVisibility(View.VISIBLE);
				imageView5.setImageResource(R.drawable.image_icon_up);
			}else{
				areaLinearLayout.setVisibility(View.GONE);	
				imageView5.setImageResource(R.drawable.image_icon);
				
			}
			break;
		case R.id.information_back:
			new GenxinSgin().start();
			new GenxinName().start();
			

			Intent intent = new Intent(InformationChangesActivity.this,TabHostActivity.class);
			intent.putExtra("1", "1");
			startActivity(intent);
			finish();
			break;
		}		
	}
	/*private void showSelectedResult() {
		Toast.makeText(MainActivity.this, "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","
				+mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
	}*/
	/**
	 * 更新头像
	 * @author Administrator
	 *
	 */
	@SuppressLint("SdCardPath")
	class GenxinHead extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			saveMyBitmap("golf");
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.HEAD+"token="+token;		
			String jsonData = HttpUtils.uploadImage(path, "/mnt/sdcard/testfile/golf.jpg");
			Log.i("imageurl", jsonData);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(jsonData);
				JSONObject jsObjectuser = new JSONObject(jsonObject.getString("user"));
				JSONObject jsObjectportrait = new JSONObject(jsObjectuser.getString("portrait"));
				imageurl = jsObjectportrait.getString("url");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Message msg = handler.obtainMessage();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	}

	class GenxinBrithday extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			String d = brithdayTextView.getText().toString();
			date = TimeUtil.localTime2utcTime(d, "yyyy-MM-dd");
			date = date/1000;
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.BRITHDAY+"token="+token+"&birthday="+date;
			String jsonData = HttpUtils.HttpClientPut(path);
			Message msg = handler.obtainMessage();
			msg.what = 2;
			handler.sendMessage(msg);
		}
	}
	class GenxinSgin extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
		
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.SIGNATURE+"token="+token+"&description="+sgin;
			String jsonData = HttpUtils.HttpClientPut(path);
			
		}
	}
	class GenxinSex extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			String s = "1";
			String sex = sexTextView.getText().toString();			
			Log.i("sexceshi", sex);

			if(sex.equals("男")){
				s = "1";
			}else{
				s = "2";
			}
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.SEX+"token="+token+"&gender="+s;
			String jsonData = HttpUtils.HttpClientPut(path);
			
		}
	}
	
	class GenxinName extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
		
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.UPNAME+"token="+token+"&nickname="+upname;
			String jsonData = HttpUtils.HttpClientPut(path);
			
		}
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
	public String removeAllSpace(String str)  
	   {  
	       String tmpstr=str.replace(" ","");  
	       return tmpstr;  
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
		
}
