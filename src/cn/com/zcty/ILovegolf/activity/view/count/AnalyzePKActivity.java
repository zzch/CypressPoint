package cn.com.zcty.ILovegolf.activity.view.count;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.com.zcty.ILovegolf.activity.R;

public class AnalyzePKActivity extends Activity{
	private Button backButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_analyze_pk);
		initView();
		setListeners();
	}

	private void setListeners() {
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initView() {
		backButton = (Button) findViewById(R.id.analyze_result_back);
	}
}
