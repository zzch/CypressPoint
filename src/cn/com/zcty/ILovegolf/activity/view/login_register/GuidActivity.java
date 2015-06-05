package cn.com.zcty.ILovegolf.activity.view.login_register;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;
import android.widget.ImageView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.fragment.GuidFragmentOne;
import cn.com.zcty.ILovegolf.activity.view.fragment.GuidFragmentThree;
import cn.com.zcty.ILovegolf.activity.view.fragment.GuidFragmentTwo;

public class GuidActivity extends FragmentActivity{
	private ViewPager viewPager;
	private ImageView imageView_1;
	private ImageView imageView_2;
	private ImageView imageView_3;
	private ArrayList<Fragment> arrayFragment = new ArrayList<Fragment>();;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		ShouYeActivity.getInstance().addActivity(this);
		initView();
		getData();
		setListeners();
	}
	private void setListeners() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					imageView_1.setImageResource(R.drawable.sdian);
					imageView_2.setImageResource(R.drawable.dian);
					imageView_3.setImageResource(R.drawable.dian);
					break;
				case 1:
					imageView_1.setImageResource(R.drawable.dian);
					imageView_2.setImageResource(R.drawable.sdian);
					imageView_3.setImageResource(R.drawable.dian);
					break;
				case 2:
					imageView_1.setImageBitmap(null);
					imageView_2.setImageBitmap(null);
					imageView_3.setImageBitmap(null);
					break;

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	private void getData() {
		arrayFragment.add(new GuidFragmentOne());
		arrayFragment.add(new GuidFragmentTwo());
		arrayFragment.add(new GuidFragmentThree());
		viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
	}
	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		imageView_1 = (ImageView) findViewById(R.id.select_dian_1);
		imageView_2 = (ImageView) findViewById(R.id.select_dian_2);
		imageView_3 = (ImageView) findViewById(R.id.select_dian_3);
		imageView_1.setImageResource(R.drawable.sdian);
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
