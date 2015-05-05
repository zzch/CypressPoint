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

import android.content.Context;
import android.util.Log;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class MajorArrayNumberWheelAdapter extends AbstractWheelTextAdapter {

	// items
	private String items[];

	/**
	 * Constructor
	 * @param context the current context
	 * @param items the items
	 */
	public MajorArrayNumberWheelAdapter(Context context) {
		super(context);

		//setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);

	}

	@Override
	public CharSequence getItemText(int index) {
		int a = 0; 
		String items[] = new String[200];
		for(int i=0;i<162;i++){
			if(a==0){
				items[i] = "进球";
			}else{
				items[i] = a+"";
			}
			
			if(a<50){
				a = a+1;
			}else{
				a = a+5;
			}

		}
		if (index >= 0 && index < items.length) {
			String item = items[index];
			if (item instanceof CharSequence) {
				return (CharSequence) item;
			}
			return item.toString();
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return 161;
	}
}
