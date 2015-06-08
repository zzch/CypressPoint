package cn.com.zcty.ILovegolf.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.model.QuickContent;

import android.app.AlertDialog;
import android.app.DownloadManager.Request;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

//Http请求的工具类
public class HttpUtils
{
	/**
	 * 采用HttpClient的POST请求发送数据
	 * @param username
	 * @param password
	 * @return
	 */
	public static String HttpClientPost(String url)
	{		int code = 0;
	try {
		String str = "";
		//创建HttpClient对象
		HttpClient client=new DefaultHttpClient();
		//创建请求路径的HttpGet对象
		HttpPost httpPost=new HttpPost(url);   
		//client将response与httpPost连接
		HttpResponse response=client.execute(httpPost);			
		//找到服务返回的状态码 200表示成功
		code=response.getStatusLine().getStatusCode();
		Log.i("code---->", ""+code);
		if(code==201||code==200)
		{

			//InputStream is=response.getEntity().getContent();
			str = EntityUtils.toString(response.getEntity(), "utf-8");
			System.out.println("is---->"+str);
			Log.i("is---->", ""+str);
			return str;
		}

	} catch (Exception e) {
		e.printStackTrace();

	}
	return code+"";
	}








	public static String httpliuyanpost(String url,Map<String,String> map){
		HttpPost post = new HttpPost(url);
		String str = "";
		int code = 0;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,String> map1 = it.next();
			nvps.add(new BasicNameValuePair(map1.getKey(), map1.getValue()));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(post);
			code = httpResponse.getStatusLine().getStatusCode();
			if(code==201||code==200){
				str = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
				return str;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code+"";

	}










	/**
	 * 采用HttpClient的POST请求发送数据
	 * @param url
	 * @param map
	 * @return
	 */
	public static String HttpClientPost(String url,Map<String,String[]> map){
		HttpPut post = new HttpPut(url);

		String str = "";
		int code = 0;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, String[]>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,String[]> map1 = it.next();
			for(int i=0;i<map1.getValue().length;i++){	        	  
				nvps.add(new BasicNameValuePair(map1.getKey(),map1.getValue()[i]));
			}
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(post);
			code = httpResponse.getStatusLine().getStatusCode();
			if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				str = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
				return str;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ""+code+"";
	}
	public static String Httpput(String url,Map<String,String> map){
		HttpPut post = new HttpPut(url);
		String str = "";
		int code = 0;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,String> map1 = it.next();
			nvps.add(new BasicNameValuePair(map1.getKey(), map1.getValue()));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(post);
			code= httpResponse.getStatusLine().getStatusCode();
			if(code==200||code==201){
				str = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
				return str;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code+"";

	}


	/**
	 * 采用HttpClient发送Get请求
	 * @param username
	 * @param password
	 * @return
	 */
	public static String HttpClientGet(String path)
	{
		String str = "";
		//创建HttpClient对象
		HttpClient client=new DefaultHttpClient();
		//创建请求路径的HttpGet对象
		HttpGet httpGet=new HttpGet(path);
		int code = 0;
		try {
			//让HttpClient往服务器发送数据
			HttpResponse response=client.execute(httpGet);
			//找到服务返回的状态码 200表示成功
			code=response.getStatusLine().getStatusCode();
			Log.i("code----->>>", ""+code);
			if(code==HttpStatus.SC_OK)
			{
				//InputStream is=response.getEntity().getContent();
				str = EntityUtils.toString(response.getEntity(), "utf-8");
				Log.i("is---->>", ""+str);
				return str;
			}

		} catch (Exception e) {
			e.printStackTrace();		
		}
		return code+"";
	}
	/**
	 * httpPut请求
	 * @param path
	 * @return
	 */
	public static String HttpClientPut(String path)
	{	int code = 0;
	String str = "";
	//创建HttpClient对象
	HttpClient client=new DefaultHttpClient();
	//创建请求路径的HttpGet对象
	HttpPut httpPut=new HttpPut(path);
	Log.i("---path", "path==="+path);
	try {
		//让HttpClient往服务器发送数据
		HttpResponse response=client.execute(httpPut);
		//找到服务返回的状态码 200表示成功
		code=response.getStatusLine().getStatusCode();
		Log.i("code----->>>", ""+code);
		if(code==HttpStatus.SC_OK)
		{
			//InputStream is=response.getEntity().getContent();
			str = EntityUtils.toString(response.getEntity(), "utf-8");
			Log.i("is---->>", ""+str);
			return str;
		}

	} catch (Exception e) {
		e.printStackTrace();		
	}
	return ""+code+"";
	}


	/**
	 * 采用HttpClient的DELETE请求发送数据
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String HttpClientDelete(String path){

		String str = "";
		int code = 0;
		//创建一个http客户端  
		HttpClient client=new DefaultHttpClient();  
		//创建一个DELETE请求  
		HttpDelete httpDelete=new HttpDelete(path);  
		//向服务器发送DELETE请求并获取服务器返回的结果，可能是删除成功，或者失败等信息  
		HttpResponse response;
		try {
			response = client.execute(httpDelete);
			code=response.getStatusLine().getStatusCode();
			Log.i("---->>", "delecode---"+code+"aaaaa");
			if(code==200){
				str = EntityUtils.toString(response.getEntity(),"utf-8");
				Log.i("is---->>", ""+str);
				return str;	
			}
		} catch (Exception e) {
			e.printStackTrace();
			str = "5";
		} 


		return code+"";
	}
	/**
	 * 上传图片
	 * 
	 * @param url
	 *            上传地址
	 * @param filepath
	 *            图片路径
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String uploadImage(String url, String filepath) {
		String str = "";
		int statusCode = 0;;
		File file = new File(filepath);

		if (!file.exists()) {
			Log.i("leslie", "file not exists");
			return null;
		}

		HttpClient client = new DefaultHttpClient();
		HttpPut put = new HttpPut(url);

		MultipartEntity entity = new MultipartEntity();
		ContentBody fileBody = new FileBody(file);
		// image 是服务端读取文件的 key
		entity.addPart("portrait", fileBody);
		put.setEntity(entity);
		HttpResponse response;
		try {
			response = client.execute(put);
			statusCode = response.getStatusLine().getStatusCode();
			String result = EntityUtils.toString(response.getEntity(), "utf-8");
			Log.i("ceshishuju", result+"aa");
			if (statusCode == 201) {
				// upload success
				// do something
				//str = EntityUtils.toString(response.getEntity(),"utf-8")
			}

			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return statusCode+"";
	} 

	/**
	 * 获取头像
	 * @param url
	 * @return
	 */
	public static Bitmap imageloder(String url){
		HttpGet httpRequest = new HttpGet(url); 
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
				//取得相关信息 取得HttpEntiy   
				HttpEntity httpEntity = httpResponse.getEntity();  
				//获得一个输入流   
				InputStream is = httpEntity.getContent();  
				Bitmap bitmap = BitmapFactory.decodeStream(is);  
				is.close();  

				return bitmap;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
}

