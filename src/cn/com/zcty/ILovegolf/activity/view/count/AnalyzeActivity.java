package cn.com.zcty.ILovegolf.activity.view.count;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.fragment.AnlyzeContestFragment;
import cn.com.zcty.ILovegolf.activity.view.fragment.AnlyzeDiamondFragment;
import cn.com.zcty.ILovegolf.activity.view.fragment.AnlyzeTimeFragment;

public class AnalyzeActivity extends FragmentActivity{
	private ViewPager viewPager;
	private ArrayList<Fragment> arrayFragment = new ArrayList<Fragment>();
	private RadioGroup analyzeRadioGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_analyze);
		initView();
		getData();
		setListeners();
	}
	private void setListeners() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		analyzeRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
			}
		});
		
	}
	private void getData() {
		arrayFragment.add(new AnlyzeContestFragment());
		arrayFragment.add(new AnlyzeTimeFragment());
		arrayFragment.add(new AnlyzeDiamondFragment());
		viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
	}
	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.analyze_viewpager);
		analyzeRadioGroup = (RadioGroup) findViewById(R.id.analyze_radio);
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
