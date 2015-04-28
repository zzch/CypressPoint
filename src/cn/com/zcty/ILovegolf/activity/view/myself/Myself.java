package cn.com.zcty.ILovegolf.activity.view.myself;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.tools.CircleImageView;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.HttpUtils;
import cn.com.zcty.ILovegolf.utils.TimeUtil;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Myself extends Activity {
	private RelativeLayout headLayout;
	private String gender;
	private String description;
	private String birthday;
	private TextView signTextView;
	private String  url;
	private CircleImageView imageHead;
	private Bitmap bitmap ;
	private TextView nameTextView;
	private String year;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				signTextView.setText(description);
				getListeners();
			}
			if(msg.what==2 ){
				new Imageloder().start();
				
			}
		};
	};
	Handler handler1 = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				imageHead.setImageBitmap(bitmap);
				saveMyBitmap(bitmap);
				blur(bitmap);
			}
		
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myself);
		initView();		
		new Ziliao().start();
		
			if(fileIsExists()){
				imageHead.setImageBitmap(converToBitmap(100,100));
				blur(converToBitmap(100,100));
				
			}else{
			new Touxiang().start();
			}
	
	}
	private void getListeners() {
		headLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Myself.this,InformationChangesActivity.class);
				intent.putExtra("birthday", birthday);
				intent.putExtra("year", year);
				startActivity(intent);
				
			}
		});
	}
	private void initView() {
		nameTextView = (TextView) findViewById(R.id.myself_name);		
		headLayout = (RelativeLayout) findViewById(R.id.myself_head_self);
		signTextView = (TextView) findViewById(R.id.myself_sign);
		imageHead = (CircleImageView) findViewById(R.id.myself_head);
		SharedPreferences sp = getSharedPreferences("register", MODE_PRIVATE);
		String name = sp.getString("nickname", "nickname");
		nameTextView.setText(name);
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
	}
	class Imageloder extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			if(url!=null){
				bitmap = HttpUtils.imageloder(url);
				Message msg = handler1.obtainMessage();
				msg.what = 1;
				handler1.sendMessage(msg);
			}
		
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


	@SuppressWarnings("unused")
	private void blur(Bitmap bkg) {   
	     long startMs = System.currentTimeMillis();   
	     float radius = 20;   
	   
	     bkg = small(bkg);  
	     Bitmap bitmap = bkg.copy(bkg.getConfig(), true);  
	   
	     final RenderScript rs = RenderScript.create(Myself.this.getBaseContext());  
	     final Allocation input = Allocation.createFromBitmap(rs, bkg, Allocation.MipmapControl.MIPMAP_NONE,  
	             Allocation.USAGE_SCRIPT);  
	     final Allocation output = Allocation.createTyped(rs, input.getType());  
	     final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)); 
	     script.setRadius(radius);  
	     script.setInput(input);  
	     script.forEach(output);  
	     output.copyTo(bitmap);  
	   
	     bitmap = big(bitmap);  
	     headLayout.setBackground(new BitmapDrawable(getResources(), bitmap));   
	     rs.destroy();   
	     Log.d("zhangle","blur take away:" + (System.currentTimeMillis() - startMs )+ "ms");  
	 }   

		 
		private static Bitmap big(Bitmap bitmap) {  
		     Matrix matrix = new Matrix();   
		       matrix.postScale(4f,4f); //长和宽放大缩小的比例  
		      Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);  
		      return resizeBmp;  
		 }  
		  
		  private static Bitmap small(Bitmap bitmap) {  
		       Matrix matrix = new Matrix();   
		       matrix.postScale(0.25f,0.25f); //长和宽放大缩小的比例  
		       Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);  
		      return resizeBmp;  
		 }  



}
