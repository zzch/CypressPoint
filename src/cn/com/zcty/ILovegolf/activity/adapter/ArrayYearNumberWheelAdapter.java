/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.com.zcty.ILovegolf.activity.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.Log;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ArrayYearNumberWheelAdapter extends AbstractWheelTextAdapter {
    
    // items
    private String items[];

    /**
     * Constructor
     * @param context the current context
     * @param items the items
     */
    public ArrayYearNumberWheelAdapter(Context context) {
        super(context);
        
        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
       
    }
    
	@Override
    public CharSequence getItemText(int index) {
			int a = 1930; 
		 	SimpleDateFormat date = new SimpleDateFormat("yyyy");
	    	String d = date.format(new Date());
	    	int y = Integer.parseInt(d);
	    	String items[] = new String[y-1930+1];	    	
	        for(int i=0;i<items.length;i++){
	        	if(a<=y){
	        		items[i] = a+"";
		        	 a = a+1;
	        	}
	        	
	        	
	        }
        if (index >= 0 && index < items.length) {
        	String item = items[index];
            if (item instanceof CharSequence) {
                return (CharSequence) item+"年";
            }
            return item.toString()+"年";
        }
        return null;
    }

    @Override
    public int getItemsCount() {
    	SimpleDateFormat date = new SimpleDateFormat("yyyy");
    	String d = date.format(new Date());
    	int y = Integer.parseInt(d);
        return y-1930+1;
    }
}
