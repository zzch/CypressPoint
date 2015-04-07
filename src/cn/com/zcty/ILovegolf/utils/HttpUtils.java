package cn.com.zcty.ILovegolf.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.model.QuickContent;

import android.app.AlertDialog;
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
	{
		try {
			String str = "";
			//创建HttpClient对象
			HttpClient client=new DefaultHttpClient();
			//创建请求路径的HttpGet对象
			HttpPost httpPost=new HttpPost(url);   
			//client将response与httpPost连接
			HttpResponse response=client.execute(httpPost);			
			//找到服务返回的状态码 200表示成功
			int code=response.getStatusLine().getStatusCode();
			Log.i("code---->", ""+code);
			if(code==201)
			{
				
				//InputStream is=response.getEntity().getContent();
				str = EntityUtils.toString(response.getEntity(), "utf-8");
				System.out.println("is---->"+str);
				Log.i("is---->", ""+str);
				return str;
			}else{
				return "0";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
	/**
	 * 采用HttpClient的POST请求发送数据
	 * @param url
	 * @param map
	 * @return
	 */
	public static String HttpClientPost(String url,Map<String,String> map){
		 HttpPost post = new HttpPost(url);
	     String str = "";
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
	          if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
	               str = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
	          }
	     } catch (Exception e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	     }
	     return str;
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
		Log.i("---path", "path==="+path);
		try {
			//让HttpClient往服务器发送数据
			HttpResponse response=client.execute(httpGet);
			//找到服务返回的状态码 200表示成功
			int code=response.getStatusLine().getStatusCode();
			Log.i("code----->>>", ""+code);
			if(code==HttpStatus.SC_OK)
			{
				//InputStream is=response.getEntity().getContent();
				str = EntityUtils.toString(response.getEntity(), "utf-8");
				Log.i("is---->>", ""+str);
			}
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return str;
	}
	public static String HttpClientPut(String path)
	{
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
			int code=response.getStatusLine().getStatusCode();
			Log.i("code----->>>", ""+code);
			if(code==HttpStatus.SC_OK)
			{
				//InputStream is=response.getEntity().getContent();
				str = EntityUtils.toString(response.getEntity(), "utf-8");
				Log.i("is---->>", ""+str);
			}
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return str;
	}
	
	
	/**
	 * 采用HttpClient的DELETE请求发送数据
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String HttpClientDelete(String path){
		
	   String str = "";
		//创建一个http客户端  
		HttpClient client=new DefaultHttpClient();  
		//创建一个DELETE请求  
		HttpDelete httpDelete=new HttpDelete(path);  
		//向服务器发送DELETE请求并获取服务器返回的结果，可能是删除成功，或者失败等信息  
		HttpResponse response;
		try {
			response = client.execute(httpDelete);
			int code=response.getStatusLine().getStatusCode();
			Log.i("---->>", "delecode---"+code+"aaaaa");
			if(code==200){
				str = EntityUtils.toString(response.getEntity(),"utf-8");
				Log.i("is---->>", ""+str);}
		} catch (Exception e) {
			e.printStackTrace();
			str = "5";
		} 
		
		
		return str;
	}
}
