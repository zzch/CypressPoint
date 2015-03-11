package cn.com.zcty.ILovegolf.tools;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class GpsGetLocation {


	public static List<String> getGpsLocationInfo(Context context)
	{
		
		 final List<String> list = new ArrayList<String>();
		 Criteria criteria = new Criteria();
		// 获取精确的位置
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW); //电量消耗低  
		criteria.setAltitudeRequired(false); //不需要海拔  
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		
		// 得到一个最合适的位置提供者
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Log.i("---------LocationManager", ""+lm);
		String provider = lm.getBestProvider(criteria, true);
		// 注册一个位置监听器.
		LocationListener locationListener =new LocationListener() {        
		        @Override 
		        public void onStatusChanged(String provider, int status, Bundle extras) { 
		            // TODO Auto-generated method stub 
		            
		        }        
		        @Override 
		        public void onProviderEnabled(String provider) { 
		          
		             
		        }        
		        @Override 
		        public void onProviderDisabled(String provider) { 
		                     
		        }        
		        @Override 
		        public void onLocationChanged(Location location) { 
		        	//经度
		        	list.add(location.getLatitude()+"");
		        	//纬度
		        	list.add(location.getLongitude()+"");
		        }
			
		    }; 
		    
		    lm.requestLocationUpdates(provider, 0, 0, locationListener);
		return list;
	}
}
