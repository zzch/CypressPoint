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
import android.widget.Toast;

public class GpsGetLocation {

	private static LocationManager lm;
    private static Criteria criteria;
    
	public static List<String> getGpsLocationInfo(Context context)
	{
		
		 final List<String> list = new ArrayList<String>();
		// 生成一个Criteria对象
		 criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//设置准确而非粗糙的精度
		criteria.setCostAllowed(false);     // 不能产生费用
		criteria.setPowerRequirement(Criteria.POWER_LOW); //设置相对省电而非耗电，一般高耗电量会换来更精确的位置信息
		criteria.setAltitudeRequired(false); //是否查询方位角 : 否 
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		
		
		lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Log.i("---------LocationManager", ""+lm);
		String provider = lm.getBestProvider(criteria, true);
		// 位置变化的监听
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
		        //手机位置发生变化
		        @Override 
		        public void onLocationChanged(Location location) { 
		        	//纬度
		        	list.add(location.getLatitude()+"");
		        	Log.i("vv", "vv"+location.getLatitude()+"");
		        	//经度
		        	list.add(location.getLongitude()+"");
		        	Log.i("ude", "ude"+location.getLatitude()+"");
		        }
			
		    }; 
		    
		    lm.requestLocationUpdates(provider, 2000, 10, locationListener);
		return list;
	}
	
}


