package cn.com.zcty.ILovegolf.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cn.com.zcty.ILovegolf.model.Course;
import cn.com.zcty.ILovegolf.model.Groups;
import cn.com.zcty.ILovegolf.model.PlaySet;
import cn.com.zcty.ILovegolf.model.QiuChangList;
import cn.com.zcty.ILovegolf.model.QuickContent;
import cn.com.zcty.ILovegolf.model.Scorecards;
import cn.com.zcty.ILovegolf.model.SortModel;
import cn.com.zcty.ILovegolf.model.TypeScorecard;

public class JsonUtil {
	 /**
	  * json解析
	  * @param path
	  * @param longitude
	  * @param latitude
	  * @param token
	  * @return
	  * @throws Exception
	  */
	 public static List<QiuChangList> getChoosePitch_json(String path)throws Exception{
		 
		 List<QiuChangList> qiuchanglists=new ArrayList<QiuChangList>();
		 String jsonData = HttpUtils.HttpClientGet(path);
		 Log.i("jsonData--->", ""+jsonData);
		 JSONArray jsonArray=new JSONArray(jsonData);
		 for(int i=0;i<jsonArray.length();i++)
			{
			    QiuChangList qiuchanglist=new QiuChangList();
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				qiuchanglist.setUuid(jsonObject.getString("uuid"));
				Log.i("------uuid--->>>", ""+jsonObject.getString("uuid"));
				qiuchanglist.setName(jsonObject.getString("name"));
				Log.i("------name--->>>", ""+jsonObject.getString("name"));
				qiuchanglist.setAddress(jsonObject.getString("address"));
				Log.i("------address--->>>", ""+jsonObject.getString("address"));
				qiuchanglist.setDistance(jsonObject.getString("distance"));
				Log.i("------dstance--->>>", ""+jsonObject.getString("distance"));
				qiuchanglist.setHoles_count(jsonObject.getString("holes_count"));
				qiuchanglists.add(qiuchanglist);
				
			}
		return qiuchanglists;
	 }
	 public static List<SortModel> getListChoosePitch_json(String path)throws Exception{
		 List<SortModel> sortModels=new ArrayList<SortModel>();
		 String JsonData=HttpUtils.HttpClientGet(path);
		 Log.i("JsonData----->>", ""+JsonData);
		 JSONArray jsonarray=new JSONArray(JsonData);
		 Log.i("jsonarray----->>", ""+jsonarray);
		 for(int i=0;i<jsonarray.length();i++){
			 SortModel sortModel=new SortModel();
			 JSONObject JSONobj=jsonarray.getJSONObject(i);
			 sortModel.setTitleName(JSONobj.getString("name"));
			 Log.i("----->>>城市名称", ""+sortModel.getName()+"");
			 
			 JSONArray subArray=JSONobj.getJSONArray("courses");
			 Log.i("courses------>>subArray", ""+subArray);
			 //List<Courses> coursess=new ArrayList<Courses>();
			 for(int j=0;j<subArray.length();j++){
				 JSONObject jsonObj =subArray.getJSONObject(j);
				
				 sortModel.setUuid(jsonObj.getString("uuid"));
				 Log.i("---->>球场信息", ""+sortModel.getUuid());
				 sortModel.setName(jsonObj.getString("name"));
				 Log.i("---->>球场信息", ""+sortModel.getName());
				 sortModel.setAddress(jsonObj.getString("address"));
				 Log.i("---->>球场信息", ""+sortModel.getAddress());
				 sortModels.add(sortModel);
			 }
			
			 }
		 Log.i("---->>集合的大小：", ""+sortModels.size());
		return sortModels;
		 }
	 
	 public static List<PlaySet> getPlaySetExpland_json(String path)throws Exception{
			List<PlaySet> playSets =new ArrayList<PlaySet>();
			String jsonData=HttpUtils.HttpClientGet(path);
			JSONObject jsonObj=new JSONObject(jsonData);
			Log.i("jsonData---->>>", ""+jsonObj);
				PlaySet playSet=new PlaySet();
				playSet.setName(jsonObj.getString("name"));
				Log.i("---->>", ""+playSet.getName());
				JSONArray subArray=jsonObj.getJSONArray("groups");
				List<Groups> groupss=new ArrayList<Groups>();
				for(int j=0;j<subArray.length();j++){
					
					Groups groups=new Groups();
					JSONObject jsonobj=subArray.getJSONObject(j);
					groups.setUuid(jsonobj.getString("uuid"));
					groups.setName(jsonobj.getString("name"));
					groups.setHoles_count(jsonobj.getString("holes_count"));
					groups.setTee_boxes(jsonobj.getString("tee_boxes"));
					groupss.add(groups);
				}
				playSet.setGroups(groupss);
				playSets.add(playSet);
			return playSets;
		}

public static List<Scorecards> getScorecards_json(String path)throws Exception{
	List<TypeScorecard> typeScorecards = new ArrayList<TypeScorecard>();
	String jsonDate=HttpUtils.HttpClientGet(path);
	JSONObject jsonObj= new JSONObject(jsonDate);
	Log.i("TypeScorecard========", ""+jsonDate);
		TypeScorecard typeScorecard= new TypeScorecard();
		typeScorecard.setType(jsonObj.getString("type"));
		Log.i("type=====", ""+typeScorecard.getType());
		JSONArray jsonArray= jsonObj.getJSONArray("scorecards");
		List<Scorecards> scorecardss= new ArrayList<Scorecards>();
		for(int i=0;i<jsonArray.length();i++){
			Scorecards scorecards= new Scorecards();
			JSONObject obj=jsonArray.getJSONObject(i);
			scorecards.setUuid(obj.getString("uuid"));
			Log.i("uuid=======", "uuid----"+scorecards.getUuid());
			scorecards.setNumber(obj.getString("number"));
			Log.i("number====", "number---"+scorecards.getNumber());
			scorecards.setPar(obj.getString("par"));
			Log.i("par====", "par---"+scorecards.getPar());
			scorecards.setTee_box_color(obj.getString("tee_box_color"));
			Log.i("tee_box_color==", "tee_box_color---"+scorecards.getTee_box_color());
			scorecards.setDistance_from_hole_to_tee_box(obj.getString("distance_from_hole_to_tee_box"));
			scorecards.setScore(obj.getString("score"));
			Log.i("score=====", "score--"+scorecards.getScore());
			scorecards.setPutts(obj.getString("putts"));
			Log.i("putts===", "putts---"+scorecards.getPutts());
			scorecards.setPenalties(obj.getString("penalties"));
			Log.i("penalties===", "penalties---"+scorecards.getPenalties());
			scorecards.setDriving_distance(obj.getString("driving_distance"));
			Log.i("driving_distance===", "driving_distance---"+scorecards.getDriving_distance());
			scorecards.setDirection(obj.getString("direction"));
			Log.i("direction===", "direction---"+scorecards.getDirection());
			scorecardss.add(scorecards);
		}
		typeScorecard.setScorecards(scorecardss);
		typeScorecards.add(typeScorecard);
	return scorecardss;
}
}
