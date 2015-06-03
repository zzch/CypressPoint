package cn.com.zcty.ILovegolf.activity.update;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

import cn.com.zcty.ILovegolf.utils.HttpUtils;

/**
 *@author coolszy
 *@date 2012-4-26
 *@blog http://blog.92coding.com
 */
public class ParseXmlService
{
	public HashMap<String, String> parseXml(InputStream inStream) throws Exception
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		
		// ʵ����һ���ĵ�����������
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// ͨ���ĵ�������������ȡһ���ĵ�������
		DocumentBuilder builder = factory.newDocumentBuilder();
		// ͨ���ĵ�ͨ���ĵ�����������һ���ĵ�ʵ��
		Document document = builder.parse(inStream);
		//��ȡXML�ļ����ڵ�
		Element root = document.getDocumentElement();
		//��������ӽڵ�
		NodeList childNodes = root.getChildNodes();
		for (int j = 0; j < childNodes.getLength(); j++)
		{
			//�����ӽڵ�
			Node childNode = (Node) childNodes.item(j);
			if (childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element childElement = (Element) childNode;
				//�汾��
				if ("version".equals(childElement.getNodeName()))
				{
					hashMap.put("version",childElement.getFirstChild().getNodeValue());
				}
				//�������
				else if (("name".equals(childElement.getNodeName())))
				{
					hashMap.put("name",childElement.getFirstChild().getNodeValue());
				}
				//���ص�ַ
				else if (("url".equals(childElement.getNodeName())))
				{
					hashMap.put("url",childElement.getFirstChild().getNodeValue());
				}
			}
		}
		return hashMap;
	}
	public HashMap<String, String> parseJson() throws Exception
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String path = "http://123.57.210.52/api/v1/versions/newest";
		Log.i("versoncode", path);
		String jsonData = HttpUtils.HttpClientGet(path);
		Log.i("versoncode", jsonData);
		JSONObject jsonObject = new JSONObject(jsonData);
		
		hashMap.put("version",jsonObject.getString("code"));
		hashMap.put("name",jsonObject.getString("name"));
		
		String file = jsonObject.getString("file");
		JSONObject fileJsonObject = new JSONObject(file);
		hashMap.put("url",fileJsonObject.getString("url"));
		return hashMap;
	}
	class Json extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
		}
		public void getData(){
			
		}
	}
}
