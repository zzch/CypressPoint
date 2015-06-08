package cn.com.zcty.ILovegolf.activity.view.count;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.fragment.AnlyzeContestFragment;
import cn.com.zcty.ILovegolf.activity.view.fragment.AnlyzeDiamondFragment;
import cn.com.zcty.ILovegolf.activity.view.fragment.AnlyzeTimeFragment;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;

public class AnalyzeActivity extends FragmentActivity{
	private ViewPager viewPager;
	private ArrayList<Fragment> arrayFragment = new ArrayList<Fragment>();
	private RadioGroup analyzeRadioGroup;
	private RadioButton bisaiRadioButton;
	private RadioButton timeRadioButton;
	private RadioButton qiuchangRadioButton;
	private Button fanhuiButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_analyze);
		ShouYeActivity.getInstance().addActivity(this);
		initView();
		getData();
		setListeners();
	}
	private void setListeners() {
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent  = new Intent(AnalyzeActivity.this,CountActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
				finish();
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					bisaiRadioButton.setChecked(true);
					timeRadioButton.setChecked(false);
					qiuchangRadioButton.setChecked(false);
					break;
				case 1:
					bisaiRadioButton.setChecked(false);
					timeRadioButton.setChecked(true);
					qiuchangRadioButton.setChecked(false);
					break;
				case 2:
					bisaiRadioButton.setChecked(false);
					timeRadioButton.setChecked(false);
					qiuchangRadioButton.setChecked(true);
					break;

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int position) {
				
			}
		});
		analyzeRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (group.getCheckedRadioButtonId()) {
				case R.id.analyze_bisai:
					viewPager.setCurrentItem(0);
					analyzeRadioGroup.setBackgroundResource(R.drawable.radio_1);
					break;
				case R.id.analyze_time:
					viewPager.setCurrentItem(1);
					analyzeRadioGroup.setBackgroundResource(R.drawable.radio_3);
					break;
				case R.id.analyze_qiuchang:
					viewPager.setCurrentItem(2);
					analyzeRadioGroup.setBackgroundResource(R.drawable.radio_2);
					break;
				}
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
		bisaiRadioButton = (RadioButton) findViewById(R.id.analyze_bisai);
		timeRadioButton = (RadioButton) findViewById(R.id.analyze_time);
		qiuchangRadioButton = (RadioButton) findViewById(R.id.analyze_qiuchang);
		fanhuiButton = (Button) findViewById(R.id.analyze_back);
		bisaiRadioButton.setChecked(true);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent  = new Intent(AnalyzeActivity.this,CountActivity.class);
		startActivity(intent);
		finish();
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
