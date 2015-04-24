package cn.com.zcty.ILovegolf.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
/**
 * @author jingche.yhq
 使用说明：
	1.先增加权限：
    	<uses-permission android:name="android.permission.INTERNET" />
    	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    2.图片URL和ImageView直接输入函数
    EX：
    	ImageService imageService = new ImageService(this);
    	ImageView imageView;
    	imageService.setBitmapByURL2("http://i1.itc.cn/20120801/a75_b0e447db_3573_ab5c_2058_5845d13545b9_1.jpg", imageView,defaultBitmap);
 */
public class ImageService {
	//*********************************************线程池获取图片资源******************************************************
	//本地数据缓存
	public Map<String, SoftReference<Bitmap>> imageCacheMap = new HashMap<String, SoftReference<Bitmap>>();
	//固定五个线程来执行任务
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    //回调函数使用

 final static ImageService imageService = new ImageService();
    
	public static ImageService getInstance(){
		return imageService;
	}
	/**
	 * 将图片放进缓存,图片名称是由URL去掉一些符号得到
	 * 
	 * @param url
	 *            地址
	 * @param bitmap
	 *            图片
	 */
	public void putImageCache(String url, Bitmap bitmap) {
		try {
			if(!ImageCache.getInstance().isBitmapExit(url));
				ImageCache.getInstance().put(url, bitmap, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param url图片URL 
	 * 
	 * @param imageView imageView
	 * 
	 * @param defaultBitmap 获取图片失败这显示默认图片
	 */
	public void setBitmapByURL(String url,final ImageView imageView,final Bitmap defaultBitmap,boolean isCacheToLocal) {
		//采用Handler+Thread+封装外部接口
	    //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Bitmap cacheImage = loadBitmap(url,imageView,isCacheToLocal,new ImageCallback() {
	    //请参见实现：如果第一次加载URL时下面方法会执行
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap) {
			//如果返回的Bitmap为空这选择默认图片
	    	   if (bitmap!=null) {
		            imageView.setImageBitmap(bitmap);
	    	   }else {
	    		   imageView.setImageBitmap(defaultBitmap);
	    	   }
		}
	     });
	     if(cacheImage!=null){
	    	 imageView.setImageBitmap(cacheImage);
	     }else {
	    	// imageView.setImageBitmap(defaultBitmap);
		}
	}
	 /**
    *
    * @param imageUrl     图像URL地址
    * @param imageCallback     回调接口
    * @return     返回内存中缓存的图像，第一次加载返回null
    */
   public Bitmap loadBitmap(final String imageUrl,final ImageView imageView,final boolean isCacheToLocal,final ImageCallback imageCallback) {
       //如果缓存过就从缓存中取出数据
	   final Handler handler = new Handler()  
       {  
           @Override  
           public void handleMessage(Message msg)  
           {  
        	   imageCallback.imageLoad(imageView, (Bitmap)msg.obj);  
           }  
       };  
       if (imageCacheMap.containsKey(imageUrl)) {
    	   Log.e("提示", "缓存获取图片");
           SoftReference<Bitmap> softReference = imageCacheMap.get(imageUrl);
           if (softReference.get() != null) {
               return softReference.get();
           }
       }else     
       if(ImageCache.getInstance().isBitmapExit(imageUrl)){
    	   Log.e("提示", "本地获取图片");
    	   Bitmap bitmap=null;
    	   try {
				bitmap = ImageCache.getInstance().get(imageUrl);
				
			} catch (Exception e) {
				bitmap = null;
			}
			return bitmap;
       }
       else {
		
       //缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
        executorService.submit(new Runnable() {
           public void run() {
               try {
            	   //获取图片
                   final Bitmap bitmap = getNetBitmapByURL(imageUrl);
                   //将图片放置到内存中
                   Log.e("提示", "网络获取图片"+Thread.currentThread().getName());
                   if(bitmap!=null){
                	   Log.e("提示", "存储图");
                	   imageCacheMap.put(imageUrl, new SoftReference<Bitmap>(bitmap));
                	   ImageCache.getInstance().put(imageUrl, bitmap, isCacheToLocal);
                   }
                   Message msg = handler.obtainMessage(0, bitmap);  
                   handler.sendMessage(msg);
               } catch (Exception e) {
                   throw new RuntimeException(e);
               }
           }
       });
       }
       return null;
   }
   //网络获取图片
   protected Bitmap getNetBitmapByURL(String urlString) {
		URL url = null;
		InputStream inputStream = null;
		HttpURLConnection urlConnection = null;
		Bitmap bmp = null;
		try {
			url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setConnectTimeout(30000);
			inputStream = urlConnection.getInputStream();
			byte[] bt = getBytesFromStream(inputStream);
			bmp = BitmapFactory.decodeByteArray(bt, 0, bt.length);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != urlConnection) {
				urlConnection.disconnect();
				urlConnection = null;
			}
		}
		return bmp;
	}

	// 数据流
	private byte[] getBytesFromStream(InputStream inputStream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while (len != -1) {
			try {
				len = inputStream.read(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (len != -1) {
				baos.write(b, 0, len); 
			}
		}
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}
   //对外界开放的回调接口
   private interface ImageCallback {
       //注意 此方法是用来设置目标对象的图像资源
	   public void imageLoad(ImageView imageView, Bitmap bitmap);
   }	
   
}
