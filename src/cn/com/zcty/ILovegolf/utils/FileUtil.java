package cn.com.zcty.ILovegolf.utils;

import java.io.File;

import android.util.Log;

public class FileUtil {
			//删除文件
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
}
