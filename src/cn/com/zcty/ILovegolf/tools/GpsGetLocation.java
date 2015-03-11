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
		// ��ȡ��ȷ��λ��
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW); //�������ĵ�  
		criteria.setAltitudeRequired(false); //����Ҫ����  
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		
		// �õ�һ������ʵ�λ���ṩ��
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Log.i("---------LocationManager", ""+lm);
		String provider = lm.getBestProvider(criteria, true);
		// ע��һ��λ�ü�����.
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
		        	//����
		        	list.add(location.getLatitude()+"");
		        	//γ��
		        	list.add(location.getLongitude()+"");
		        }
			
		    }; 
		    
		    lm.requestLocationUpdates(provider, 0, 0, locationListener);
		return list;
	}
}
