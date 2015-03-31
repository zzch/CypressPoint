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
package cn.com.zcty.ILovegolf.exercise.adapter;

import android.content.Context;
import android.util.Log;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ArrayNumberWheelAdapter extends AbstractWheelTextAdapter {
    
    // items
    private String items[];

    /**
     * Constructor
     * @param context the current context
     * @param items the items
     */
    public ArrayNumberWheelAdapter(Context context) {
        super(context);
        
        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
       
    }
    
	@Override
    public CharSequence getItemText(int index) {
		 int a = 0; 
	    	String items[] = new String[200];
	        for(int i=0;i<82;i++){
	        	items[i] = a+"";
	        	a = a+5;
	        	
	        }
        if (index >= 0 && index < items.length) {
        	String item = items[index];
            if (item instanceof CharSequence) {
                return (CharSequence) item+"码";
            }
            return item.toString()+"码";
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return 81;
    }
}
