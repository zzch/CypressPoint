package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentOne;
import cn.com.zcty.ILovegolf.activity.view.fragment.StaticsFragmentTwo;

public class StatisticsAvtivity extends FragmentActivity{
	private Button backButton;
	private ViewPager tablePager;
	private Button agoButton;
	private Button laterButton;
	private ArrayList<Fragment> arrayFragment = new ArrayList<Fragment>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		initView();
		setListener();
		getData();
	}

	private void getData() {
		arrayFragment.add(new StaticsFragmentOne());
		arrayFragment.add(new StaticsFragmentTwo());
		tablePager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
	}

	private void setListener() {
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
		
	}

	private void initView() {
		backButton = (Button) findViewById(R.id.back);
		tablePager = (ViewPager) findViewById(R.id.viewpager);
	}
	class MyFragmentPagerAdapter  extends FragmentPagerAdapter{

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return arrayFragment.get(position);
		}

		@Override
		public int getCount() {
			return arrayFragment.size();
		}
		
	}
}
