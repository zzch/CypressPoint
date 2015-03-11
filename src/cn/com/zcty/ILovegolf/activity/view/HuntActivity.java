package cn.com.zcty.ILovegolf.activity.view;

import java.io.Serializable;
import java.util.ArrayList;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.R.layout;
import cn.com.zcty.ILovegolf.activity.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HuntActivity extends Activity   {
	private ListView cityList;
	private ArrayList<String> arrayCityList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hunt);
		initView();
		//initData();
	}
	
	private void initData() {
		
		
	}

	private void initView() {
	cityList = (ListView) findViewById(R.id.listView1);
	Intent intent = this.getIntent();
	arrayCityList = intent.getStringArrayListExtra("city");
	cityList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayCityList));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hunt, menu);
		return true;
	}

}
