package cn.com.zcty.ILovegolf.activity.view.login_register;

import cn.com.zcty.ILovegolf.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
/**
 * ����������
 * @author deii
 *
 */
public class ForGetPasswordActivity extends Activity {

	 private EditText et_mobile;
	 private LinearLayout forget_layout_one;
	 private LinearLayout forget_layout_two;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forgetpassword);
		et_mobile=(EditText) findViewById(R.id.et_mobile);
		forget_layout_one=(LinearLayout) findViewById(R.id.forget_layout_one);
		forget_layout_two=(LinearLayout) findViewById(R.id.forget_layout_two);
	}
	/**
	 * ��������--���ذ�ť
	 * @param v
	 */
	public void forgetp_back(View v){
		Intent intent=new Intent(this,LoginActivity.class);
		startActivity(intent);
		finish();
	}
	/**
	 * ��������--�ύ��ť
	 * @param v
	 */
	public void on_submit(View v){
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
		}.execute();
		
		String mobile=et_mobile.getText().toString().trim();
		if(mobile.equals(null)){
			forget_layout_one.setVisibility(View.GONE);
		}else{
			forget_layout_two.setVisibility(View.VISIBLE);
		}
		
		
	}

}
