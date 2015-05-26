package cn.com.zcty.ILovegolf.activity.view.myself;

import cn.com.zcty.ILovegolf.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class FeedBackActivity extends Activity{
	private EditText opinionEditText;
	private Button submitButton;
	private String opinionContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
	}
}
