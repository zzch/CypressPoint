package cn.com.zcty.ILovegolf.activity.view;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.R.drawable;
import cn.com.zcty.ILovegolf.activity.R.id;
import cn.com.zcty.ILovegolf.activity.R.layout;
import cn.com.zcty.ILovegolf.activity.view.count.CountActivity;
import cn.com.zcty.ILovegolf.activity.view.myself.Myself;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabHostActivity extends TabActivity {
	

		@Override
		protected void onCreate(Bundle savedInstanceState) {
		
			super.onCreate(savedInstanceState);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.tabactivity_main);
			TabHost myTabHost=getTabHost();
			
			TabSpec tabSpec1=myTabHost.newTabSpec("��ϰ");
			tabSpec1.setIndicator(getIndicatorView("��ϰ",R.drawable.u1632_normal));
			tabSpec1.setContent(new Intent(this,ExerciseActivity.class));
			
			TabSpec tabSpec2=myTabHost.newTabSpec("ͳ��");
			tabSpec2.setIndicator(getIndicatorView("ͳ��",R.drawable.u1641_normal));
			tabSpec2.setContent(new Intent(this,CountActivity.class));
		
			TabSpec tabSpec3=myTabHost.newTabSpec("����");
			tabSpec3.setIndicator(getIndicatorView("����",R.drawable.u1644_normal));
			tabSpec3.setContent(new Intent(this,Myself.class));
			
			myTabHost.addTab(tabSpec1);
			myTabHost.addTab(tabSpec2);
			myTabHost.addTab(tabSpec3);
			myTabHost.setCurrentTab(0);
			
		}
		
		public View getIndicatorView(String name,int icon)
		{
			View view=View.inflate(getApplicationContext(), R.layout.tabactivity_main_item, null);
		    ImageView iv=(ImageView) view.findViewById(R.id.lianxi_item);
		    iv.setImageResource(icon);
		    TextView tv=(TextView) view.findViewById(R.id.lianxi_name);
		    tv.setText(name);
		    return view;
		}
	


}
