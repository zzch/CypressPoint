package cn.com.zcty.ILovegolf.activity.view.myself;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;
import cn.com.zcty.ILovegolf.utils.APIService;
import cn.com.zcty.ILovegolf.utils.FileUtil;
import cn.com.zcty.ILovegolf.utils.HttpUtils;

public class FeedBackActivity extends Activity{
	
	private EditText opinionEditText;
	private TextView numberTextView;
	private Button submitButton;
	private String opinionContent;
	private String result;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(msg.obj.equals("404")||msg.obj.equals("500")){
					Toast.makeText(FeedBackActivity.this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();

				}else if(msg.obj.equals("401")){
					Toast.makeText(FeedBackActivity.this, "帐号异地登录，请重新登录", Toast.LENGTH_LONG).show();
					FileUtil.delFile();
					Intent intent = new Intent(FeedBackActivity.this,ShouYeActivity.class);
					startActivity(intent);
					overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
					finish();
				}else{
					if(result.equals("success")){
						Toast.makeText(FeedBackActivity.this, "反馈成功", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(FeedBackActivity.this,Myself.class);
						startActivity(intent);
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						finish();
					}else{
						Toast.makeText(FeedBackActivity.this, "反馈失败", Toast.LENGTH_LONG).show();

					}
					}
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feedback);
		initView();
		setListeners();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(FeedBackActivity.this,Myself.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
		finish();
	}
	public void onClick(View v){
		Intent intent = new Intent(FeedBackActivity.this,Myself.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
		finish();
	}
	private void setListeners() {
		
		
		
		opinionEditText.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				temp = s;
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				 int num = 140;
				   int number = num - s.length();
				   numberTextView.setText("当前可以输入"+number+"字");
	               selectionStart = opinionEditText.getSelectionStart();
	               selectionEnd = opinionEditText.getSelectionEnd();
	               if (temp.length() > num) {
	                  String f =  s.toString().substring(0, 140);
	                   int tempSelection = selectionStart;
	                   opinionEditText.setText(f);
	                  // opinionEditText.setSelection(tempSelection);//设置光标在最后
	                }
			}
		});
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				opinionContent = opinionEditText.getText().toString().trim();
				new FeedBack().start();
			}
		});
	}
	private void initView() {
		opinionEditText = (EditText) findViewById(R.id.feedback);
		submitButton = (Button) findViewById(R.id.submit);
		numberTextView = (TextView) findViewById(R.id.number);
	}
	class FeedBack extends Thread{ 
		@Override
		public void run() {
			super.run();
			getData();
		}
		public void getData(){
			SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
			String token=sp.getString("token", "token");
			String path = APIService.FEEDBACK+"token="+token;
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("content", opinionContent);			
			String jsonData = HttpUtils.httpliuyanpost(path, map);
			Log.i("FeedBack", path);
			Log.i("FeedBack", jsonData);
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				result = jsonObject.getString("result");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.obj = jsonData;
			handler.sendMessage(msg);
		}
	}
}
