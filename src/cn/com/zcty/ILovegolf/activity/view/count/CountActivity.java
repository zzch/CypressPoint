package cn.com.zcty.ILovegolf.activity.view.count;



import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class CountActivity extends Activity implements OnClickListener{
	private Button analyButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_count);
		initView();
		setListeners();
	}
	private void setListeners() {
		analyButton.setOnClickListener(this);
	}
	private void initView() {
		analyButton = (Button) findViewById(R.id.count_start);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.count_start:
			Intent intent = new Intent(CountActivity.this,AnalyzeActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
