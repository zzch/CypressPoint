package cn.com.zcty.ILovegolf.activity.view.myself;

import cn.com.zcty.ILovegolf.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ChangesPhone extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changephone);
	}
}
