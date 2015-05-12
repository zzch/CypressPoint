package cn.com.zcty.ILovegolf.tools;

import java.security.PublicKey;

import cn.com.zcty.ILovegolf.activity.R;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


/**
 * 自定义密码输入框，仿微信支付密码输入框
 * @author hezenan
 *
 */
public class SecurityPasswordEditText extends LinearLayout {
	private RelativeLayout re;
	private EditText one_pwd;
	private EditText two_pwd;
	private EditText three_pwd;
	//private EditText five_pwd;
	private EditText four_pwd;
	//private EditText six_pwd;
	private TextWatcher tw_pwd;
	private AsteriskPasswordTransformationMethod asteriskPassword;
	private String inputnumber;
	private onKeyListeners onkeylistener;
	private onFocusListeners onfocuslistener;

	private OnEditTextListener onEditTextListener;





	public String getInputnumber() {
		return inputnumber;
	}

	public void setInputnumber(String inputnumber) {
		this.inputnumber = inputnumber;
		if(inputnumber == null){
			one_pwd.requestFocus();
			one_pwd.setText(null);
			two_pwd.setText(null);
			three_pwd.setText(null);
			four_pwd.setText(null);
			}
	}

	public OnEditTextListener getOnEditTextListener() {
		return onEditTextListener;
	}

	public void setOnEditTextListener(OnEditTextListener onEditTextListener) {
		this.onEditTextListener = onEditTextListener;
	}

	public interface OnEditTextListener{
		public void  inputComplete(int state,String password);
	}

	public SecurityPasswordEditText(Context context) {
		super(context);
		init(context);
	}



	public SecurityPasswordEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}



	private void init(Context context) {
		LayoutInflater.from(getContext())
		.inflate(R.layout.edittext_password, this);
		one_pwd= (EditText)findViewById(R.id.pwd_one);	
		/*one_pwd.requestFocus();
		InputMethodManager m = (InputMethodManager) one_pwd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
		m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);*/
		two_pwd= (EditText)findViewById(R.id.pwd_two);
		three_pwd= (EditText)findViewById(R.id.pwd_three);
		four_pwd= (EditText)findViewById(R.id.pwd_four);
		//five_pwd= (EditText)findViewById(R.id.pwd_five);
		//six_pwd= (EditText)findViewById(R.id.pwd_six);
		re = (RelativeLayout) findViewById(R.id.aa);
		re.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(inputnumber == null){
					one_pwd.requestFocus();
					
				}else{
					switch (inputnumber.length()) {
					case 3:
						four_pwd.requestFocus();
						break;
					case 1:
						two_pwd.requestFocus();
						break;
					case 2:
						three_pwd.requestFocus();
						break;
					case 4:
						four_pwd.requestFocus();
						break;
					

					}
				}
				InputMethodManager m = (InputMethodManager) one_pwd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
				m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		});
		
		asteriskPassword =  new AsteriskPasswordTransformationMethod();
		onkeylistener  =  new onKeyListeners();
		editPwdWatcher(context);
		//设置更改默认密码样式
		one_pwd.setTransformationMethod(asteriskPassword);
		two_pwd.setTransformationMethod(asteriskPassword);
		three_pwd.setTransformationMethod(asteriskPassword);
		four_pwd.setTransformationMethod(asteriskPassword);
		//five_pwd.setTransformationMethod(asteriskPassword);
		//six_pwd.setTransformationMethod(asteriskPassword);
		//设置字符改变监听
		one_pwd.addTextChangedListener(tw_pwd);
		two_pwd.addTextChangedListener(tw_pwd);
		three_pwd.addTextChangedListener(tw_pwd);
		four_pwd.addTextChangedListener(tw_pwd);
		//five_pwd.addTextChangedListener(tw_pwd);
		//six_pwd.addTextChangedListener(tw_pwd);
		//焦点监听
		one_pwd.setOnFocusChangeListener(onfocuslistener);
		two_pwd.setOnFocusChangeListener(onfocuslistener);
		three_pwd.setOnFocusChangeListener(onfocuslistener);
		four_pwd.setOnFocusChangeListener(onfocuslistener);
		//five_pwd.setOnFocusChangeListener(onfocuslistener);
		//six_pwd.setOnFocusChangeListener(onfocuslistener);
		//删除按钮监听
		one_pwd.setOnKeyListener(onkeylistener);
		two_pwd.setOnKeyListener(onkeylistener);
		three_pwd.setOnKeyListener(onkeylistener);
		four_pwd.setOnKeyListener(onkeylistener);
		//five_pwd.setOnKeyListener(onkeylistener);
		//six_pwd.setOnKeyListener(onkeylistener);


		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.hideSoftInputFromWindow(one_pwd.getWindowToken(), 0); 
		

	}
	/**
	 * 字符改变监听
	 * @param context
	 */
	private void editPwdWatcher(final Context context){
		tw_pwd = new TextWatcher() {


			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				
				if (s.toString().length() == 1) {
					if (one_pwd.isFocused()) { 
						two_pwd.setFocusable(true);
						two_pwd.requestFocus();
						inputnumber = getEditNumber();
						if (onEditTextListener != null) {
							onEditTextListener.inputComplete(4, inputnumber);
						}
					}else if (two_pwd.isFocused()) { 
						three_pwd.setFocusable(true);  
						three_pwd.requestFocus(); 
						inputnumber = getEditNumber();
						if (onEditTextListener != null) {
							onEditTextListener.inputComplete(4, inputnumber);
						}
					}else if(three_pwd.isFocused()){
						four_pwd.setFocusable(true); 
						four_pwd.requestFocus();
						inputnumber = getEditNumber();
						if (onEditTextListener != null) {
							onEditTextListener.inputComplete(4, inputnumber);
						}
					}else if(four_pwd.isFocused()){
						/*five_pwd.setFocusable(true);
						five_pwd.requestFocus();*/
						inputnumber = getEditNumber();
						if (onEditTextListener != null) {
							onEditTextListener.inputComplete(4, inputnumber);
						}
					}/*else if(five_pwd.isFocused()){
						six_pwd.setFocusable(true);
						six_pwd.requestFocus();
						inputnumber = getEditNumber();
						if (onEditTextListener != null) {
							onEditTextListener.inputComplete(6, inputnumber);
						}
					}*//*else if(six_pwd.isFocused()){
						inputnumber = getEditNumber();
						if (onEditTextListener != null) {
							onEditTextListener.inputComplete(6, inputnumber);
						}
						Log.d("tag", inputnumber);

					}*/
				}
			}
		};
	}
	//焦点监听
	class onFocusListeners implements OnFocusChangeListener{
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					
					switch (v.getId()) {
					case R.id.pwd_one:
							one_pwd.requestFocus();
							
						break;
					case R.id.pwd_two:
							two_pwd.clearFocus();
							one_pwd.requestFocus();
							
						break;
					case R.id.pwd_three:
							three_pwd.clearFocus();
							one_pwd.requestFocus();
							
						break;
					case R.id.pwd_four:
							four_pwd.clearFocus();
							one_pwd.requestFocus();
							
						break;
					/*case R.id.pwd_five:
							five_pwd.clearFocus();
							one_pwd.requestFocus();
						break;*/
					}
				}
		
			}
	
	
	/**
	 * 更改密码默认替代字符,系统默认的字符太小了
	 * @author hezenan
	 *
	 */
	class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {

		@Override
		public CharSequence getTransformation(CharSequence source, View view) {
			return new PasswordCharSequence(source);
		} 
		private class PasswordCharSequence implements CharSequence {
			private CharSequence mSource;  
			public PasswordCharSequence(CharSequence source) {  
				mSource = source;  
			}  
			@Override
			public int length() {
				return mSource.length();
			}

			@Override
			public char charAt(int index) {
				return '●';
			}

			@Override
			public CharSequence subSequence(int start, int end) {
				return mSource.subSequence(1, end);
			}

		}
	}

	private int count = 0;
	/**
	 * 删除按钮监听
	 * @author hezenan
	 *
	 */
	class onKeyListeners implements OnKeyListener{

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_DEL) {
				//不知道不知道什么原因，点击一次删除按钮会调两次这个方法，所有处理一下，两次当一次
				count++;
				if (count < 2) {
					return false;
				}
				count=0;
				inputnumber = "";
				
				/*if(six_pwd.isFocused()){
					six_pwd.clearFocus();
					five_pwd.requestFocus();
				}else if (five_pwd.isFocused()) {
					five_pwd.clearFocus();
					four_pwd.requestFocus();
				}else*/ if (four_pwd.isFocused()) {
					four_pwd.clearFocus();
					three_pwd.requestFocus();
				}else if(three_pwd.isFocused()){
					three_pwd.clearFocus();
					two_pwd.requestFocus();
				}else if(two_pwd.isFocused()){
					two_pwd.clearFocus();
					one_pwd.requestFocus();
				}

				Log.d("tag", "点击了");
			}
			return false;
		}

	}

	public String getEditNumber(){
		String number = one_pwd.getText().toString();
		number+=two_pwd.getText().toString();
		number+=three_pwd.getText().toString();
		number+=four_pwd.getText().toString();
		//number+=five_pwd.getText().toString();
		//number+=six_pwd.getText().toString();
		
		return number;
	}

}
