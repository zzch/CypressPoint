package cn.com.zcty.ILovegolf.activity.view.competition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;

public class SginText extends Activity{
	private EditText editText;
	private String sgin;
	private Button fanhuiButton;
	private TextView numberTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sgintext);
		initView();
		setListeners();
	}
	private void setListeners() {
		editText.addTextChangedListener(new TextWatcher() {
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
			   int num = 100;
			   int number = num - s.length();
			   numberTextView.setText("当前可以输入"+number+"字");
               selectionStart = editText.getSelectionStart();
               selectionEnd = editText.getSelectionEnd();
               if (temp.length() > num) {
                   s.delete(selectionStart - 1, selectionEnd);
                   int tempSelection = selectionStart;
                    editText.setText(s);
                    editText.setSelection(tempSelection);//设置光标在最后
                }

			}
		});
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sgin = editText.getText().toString();
				if(sgin.equals("")){
					sgin = "点击设置比赛备注";
				}
				Intent intent = new Intent();
				intent.putExtra("sgin", sgin);
				setResult(1, intent);
				finish();
			
			}
		});
	}
	private void initView() {
		editText = (EditText) findViewById(R.id.add_content);
		fanhuiButton = (Button) findViewById(R.id.button1);
		numberTextView = (TextView) findViewById(R.id.number);
	}
	
}
