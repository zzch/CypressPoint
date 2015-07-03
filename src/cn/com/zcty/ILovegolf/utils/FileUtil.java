package cn.com.zcty.ILovegolf.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.widget.RelativeLayout;
/**
 * 周鹤
 * 2015/5/18
 * @author Administrator
 *
 */
public class FileUtil {
	
	/**
	 * 删除文件
	 */
	public  static void delFile(){
		File file = new File("/mnt/sdcard/testfile");
		deleteFile(file);
	}
	public static void deleteFile(File file) {

		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
			Log.i("tishis","文件不存在！"+"\n");
		}
	}
	/**
	 * 从文件中读取图片
	 */
	public static Bitmap converToBitmap( int w, int h){
		Bitmap bitmap;
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
	 * 把bitmap存入手机文件目录
	 * @param bitName
	 */
	@SuppressLint("SdCardPath")
	public static void saveMyBitmap(Bitmap bitName)  {
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
	public static boolean fileIsExists(){

        File f=new File("/mnt/sdcard/testfile");

          if(!f.exists()){

                     return false;
             }

             return true;

      }
	/**
	 * 图片模糊化，成为背景
	 * @param bkg
	 * @param context
	 * @param headLayout
	 */
	@SuppressLint("NewApi")
	
	public  static void blur(Bitmap bkg ,Activity context,RelativeLayout headLayout) {   
	     long startMs = System.currentTimeMillis();   
	     float radius = 20;   
	   
	     bkg = small(bkg);  
	     Bitmap bitmap = bkg.copy(bkg.getConfig(), true);  
	   
	     final RenderScript rs = RenderScript.create(context.getBaseContext());  
	     final Allocation input = Allocation.createFromBitmap(rs, bkg, Allocation.MipmapControl.MIPMAP_NONE,  
	             Allocation.USAGE_SCRIPT);  
	     final Allocation output = Allocation.createTyped(rs, input.getType());  
	     final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)); 
	     script.setRadius(radius);  
	     script.setInput(input);  
	     script.forEach(output);  
	     output.copyTo(bitmap);  
	   
	     bitmap = big(bitmap);  
	     headLayout.setBackground(new BitmapDrawable(context.getResources(), bitmap));   
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
	       matrix.postScale(4f,4f); //长和宽放大缩小的比例  
	       Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);  
	      return resizeBmp;  
	 }  
	  
	  ///////////////////////////////////////////////////////////////
	  
	  /**
	   * 压缩图片
	   * @param image
	   * @return
	   */
	  public static Bitmap comp(Bitmap image) {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();        
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
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
	  private static Bitmap compressImage(Bitmap image) {
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
	  
	  //////////////////////////////////
	  /**
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
